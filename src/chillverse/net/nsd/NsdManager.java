package chillverse.net.nsd;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import com.sun.jna.Pointer;

import chillverse.jna.gobject.GError;
import chillverse.jna.gobject.GErrorException;
import chillverse.jna.gobject.avahi.Address;
import chillverse.jna.gobject.avahi.AvahiClient;
import chillverse.jna.gobject.avahi.AvahiConstants;
import chillverse.jna.gobject.avahi.ServiceBrowser;
import chillverse.jna.gobject.avahi.ServiceResolver;
import chillverse.text.TextUtils;

public class NsdManager implements AvahiConstants {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(NsdManager.class.getName());

  /** DNS based service discovery protocol */
  public static final int PROTOCOL_DNS_SD = 0x0001;

  /** Interface for callback invocation for service discovery. */
  public interface DiscoveryListener {

    void onDiscoveryStarted(String serviceType);

    void onDiscoveryStopped(String serviceType);

    void onServiceFound(NsdServiceInfo serviceInfo);

    void onServiceLost(NsdServiceInfo serviceInfo);

    void onStartDiscoveryFailed(String serviceType, int errorCode);

    void onStopDiscoveryFailed(String serviceType, int errorCode);

  }

  /** Interface for callback invocation for service registration. */
  public interface RegistrationListener {

    void onRegistrationFailed(NsdServiceInfo serviceInfo, int errorCode);

    void onServiceRegistered(NsdServiceInfo serviceInfo);

    void onServiceUnregistered(NsdServiceInfo serviceInfo);

    void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode);

  }

  /** Interface for callback invocation for service resolution. */
  public interface ResolveListener {

    void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode);

    void onServiceResolved(NsdServiceInfo serviceInfo);

  }

  // ---

  private final Map<NsdServiceInfo, Object> mListenerMap = new HashMap<NsdServiceInfo, Object>();
  private final Map<Object, NsdServiceInfo> mServiceMap = new HashMap<Object, NsdServiceInfo>();
  private final Object mMapLock = new Object();

  private final Map<String, ServiceBrowser> mServiceBrowsers = new ConcurrentHashMap<String, ServiceBrowser>();
  
  private final Map<String, NsdServiceInfo> mServices = new ConcurrentHashMap<String, NsdServiceInfo>();
  private final Map<String, ServiceResolver> mServiceResolvers = new ConcurrentHashMap<String, ServiceResolver>();

  private final AvahiClient client;

  public NsdManager() {
    this(CLIENT_FLAG_NO_FLAGS);
  }

  public NsdManager(int clientFlags) {
    client = new AvahiClient(clientFlags);

    // TODO initialize worker thread...

    try {
      client.start();
    } catch (GErrorException e) {
      e.printStackTrace();
    }
  }

  /**
   * Initiate service discovery to browse for instances of a service type.
   * Service discovery consumes network bandwidth and will continue until the
   * application calls {@link #stopServiceDiscovery}.
   * <p>
   * The function call immediately returns after sending a request to start
   * service discovery to the network. The application is notified of a success
   * to initiate discovery through the callback
   * {@link DiscoveryListener#onDiscoveryStarted} or a failure through
   * {@link DiscoveryListener#onStartDiscoveryFailed}.
   * <p>
   * Upon successful start, application is notified when a service is found with
   * {@link DiscoveryListener#onServiceFound} or when a service is lost with
   * {@link DiscoveryListener#onServiceLost}.
   * <p>
   * Upon failure to start, service discovery is not active and application does
   * not need to invoke {@link #stopServiceDiscovery}.
   * 
   * @param serviceType
   *          The service type being discovered. Examples include
   *          {@code _http._tcp} for http services or {@code _ipp._tcp} for
   *          printers.
   * @param protocolType
   *          The service discovery protocol.
   * @param listener
   *          The listener notifies of a successful discovery and is used to
   *          stop discovery on this serviceType through a call on
   *          {@link #stopServiceDiscovery}. Cannot be {@code null}.
   */
  public void discoverServices(String serviceType, int protocolType, final DiscoveryListener listener) {
    if (listener == null) {
      throw new IllegalArgumentException("listener cannot be null");
    }
    if (TextUtils.isEmpty(serviceType)) {
      throw new IllegalArgumentException("Service type cannot be empty");
    }

    if (protocolType != PROTOCOL_DNS_SD) {
      throw new IllegalArgumentException("Unsupported protocol");
    }

    final ServiceBrowser browser = new ServiceBrowser(serviceType);
    final AvahiNsdServiceInfo s = new AvahiNsdServiceInfo(browser);
    s.setServiceType(serviceType);
    s.connect(new ServiceBrowser.NewServiceSignalHandler() {
      @Override
      protected void onSignal(ServiceBrowser srvBrowser, int iface, int protocol, String name, String type, String domain, int lookupResultFlags) {
        NsdServiceInfo info = getServiceInfo(name, type);
        if (info != null) {
          // LOG warning
        } else {
          putServiceInfo(info = new AvahiNsdServiceInfo(iface, protocol, name, type, domain, lookupResultFlags));
        }
        listener.onServiceFound(info);
      }
    });
    s.connect(new ServiceBrowser.RemovedServiceSignalHandler() {
      @Override
      protected void onSignal(ServiceBrowser srvBrowser, int iface, int protocol, String name, String type, String domain, int lookupResultFlags) {
        NsdServiceInfo info = getServiceInfo(name, type);
        if (info != null) {
          listener.onServiceLost(info);
        } else {
          // LOG warning
        }
      }
    });
    // TODO cache-exhausted

    try {
      browser.attach(client);
      putListener(listener, s);
      listener.onDiscoveryStarted(serviceType);
    } catch (GErrorException e) {
      // TODO log the error
      listener.onStartDiscoveryFailed(serviceType, e.getCode());
    }
  }

  /**
   * Stop service discovery initiated with {@link #discoverServices}. An active
   * service discovery is notified to the application with
   * {@link DiscoveryListener#onDiscoveryStarted} and it stays active until the
   * application invokes a stop service discovery. A successful stop is notified
   * to with a call to {@link DiscoveryListener#onDiscoveryStopped}.
   * <p>
   * Upon failure to stop service discovery, application is notified through
   * {@link DiscoveryListener#onStopDiscoveryFailed}.
   * 
   * @param listener
   *          This should be the listener object that was passed to
   *          {@link #discoverServices}. It identifies the discovery that should
   *          be stopped and notifies of a successful stop.
   */
  public void stopServiceDiscovery(DiscoveryListener listener) {
    if (listener == null) {
      throw new IllegalArgumentException("listener cannot be null");
    }

    NsdServiceInfo s = mServiceMap.get(listener);

    // TODO STOP_DISCOVERY
  }

  public void resolveService(final NsdServiceInfo s, final ResolveListener listener) {
    final AvahiNsdServiceInfo as = (AvahiNsdServiceInfo) s;
    final ServiceResolver resolver = new ServiceResolver(as.iface, as.prototol, 
        as.getServiceName(), as.getServiceType(), as.domain, as.prototol, ServiceResolver.LOOKUP_USE_MULTICAST);
    as.setServiceResolver(resolver);
    as.connect(new ServiceResolver.FoundSignalHandler() {
      @Override
      protected void onSignal(ServiceResolver resolver, int iface, int protocol, 
          String name, String type, String domain, String hostname, Address address, short port, Pointer txt, int flags) {
        NsdServiceInfo info = getServiceInfo(name, type);
        if (info != null) {
          if (protocol == PROTO_INET) {
            try {
              info.setHost(Inet4Address.getByName(hostname));
            } catch (UnknownHostException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          } else if (protocol == PROTO_INET6) {
            try {
              info.setHost(Inet6Address.getByName(hostname));
            } catch (UnknownHostException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          }
          info.setPort(port);
          // info.setTxtRecord(t)
          listener.onServiceResolved(info);
        } else {
          // TODO Warning
        }
      }
    });
    as.connect(new ServiceResolver.FailureSignalHandler() {
      @Override
      protected void onSignal(ServiceResolver resolver, GError error) {
        listener.onResolveFailed(s, error.code);
      }
    });

    try {
      resolver.attach(client);
    } catch (GErrorException e) {
      e.printStackTrace();
    }
  }

  public void registerService(NsdServiceInfo serviceInfo, int protocolType, RegistrationListener listener) {
    throw new UnsupportedOperationException();
  }

  public void unregisterService(RegistrationListener listener) {
    throw new UnsupportedOperationException();
  }

  private NsdServiceInfo getServiceInfo(String name, String type) {
    return mServices.get(serviceKey(name, type));
  }

  private void putServiceInfo(NsdServiceInfo s) {
    mServices.put(serviceKey(s.getServiceName(), s.getServiceType()), s);
  }

  private String serviceKey(String name, String type) {
    return new StringBuilder().append(name).append(':').append(type).toString();
  }

  private void putListener(Object listener, NsdServiceInfo s) {
    assert (listener != null) : "INVALID_LISTENER_KEY";
    synchronized (mMapLock) {
      mListenerMap.put(s, listener);
      mServiceMap.put(listener, s);
    }
  }
}
