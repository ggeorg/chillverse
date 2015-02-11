package chillverse.net.nsd;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import chillverse.jna.AvahiCommonLibrary;
import chillverse.jna.avahi.Address;
import chillverse.jna.avahi.AvahiClient;
import chillverse.jna.avahi.AvahiConstants;
import chillverse.jna.avahi.AvahiServiceBrowser;
import chillverse.jna.avahi.AvahiServiceResolver;
import chillverse.jna.avahi.EntryGroup;
import chillverse.jna.gobject.GError;
import chillverse.jna.gobject.GErrorException;
import chillverse.text.TextUtils;

import com.sun.jna.Pointer;

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

  private final Map<DiscoveryListener, AvahiDiscoveryListener> mDiscoveryListenerMap =
      new ConcurrentHashMap<DiscoveryListener, AvahiDiscoveryListener>();

  private final Map<RegistrationListener, AvahiRegistrationListener> mRegistrationListenerMap =
      new ConcurrentHashMap<RegistrationListener, AvahiRegistrationListener>();

  private final Map<String, NsdServiceInfo> mServices = new ConcurrentHashMap<String, NsdServiceInfo>();

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
  public void discoverServices(final String serviceType, int protocolType, final DiscoveryListener listener) {
    if (listener == null) {
      throw new IllegalArgumentException("listener cannot be null");
    }
    if (TextUtils.isEmpty(serviceType)) {
      throw new IllegalArgumentException("Service type cannot be empty");
    }

    if (protocolType != PROTOCOL_DNS_SD) {
      throw new IllegalArgumentException("Unsupported protocol");
    }

    final AvahiServiceBrowser browser = new AvahiServiceBrowser(serviceType);
    final AvahiDiscoveryListener avahiListener = new AvahiDiscoveryListener(serviceType, listener);
    avahiListener.setServiceBrowser(browser);
    avahiListener.connect(new AvahiServiceBrowser.NewServiceSignalHandler() {
      @Override
      protected void onSignal(AvahiServiceBrowser srvBrowser, int iface, int protocol, String name, String type, String domain, int lookupResultFlags) {
        NsdServiceInfo info = getServiceInfo(name, type);
        if (info != null) {
          // LOG warning
        } else {
          putServiceInfo(info = new AvahiNsdServiceInfo(iface, protocol, name, type, domain, lookupResultFlags));
        }
        listener.onServiceFound(info);
      }
    });
    avahiListener.connect(new AvahiServiceBrowser.RemovedServiceSignalHandler() {
      @Override
      protected void onSignal(AvahiServiceBrowser srvBrowser, int iface, int protocol, String name, String type, String domain, int lookupResultFlags) {
        NsdServiceInfo info = getServiceInfo(name, type);
        if (info != null) {
          listener.onServiceLost(info);
        } else {
          // LOG warning
        }
      }
    });
    // TODO cache-exhausted

    mDiscoveryListenerMap.put(listener, avahiListener);

    try {
      browser.attach(client);
      avahiListener.onDiscoveryStarted(serviceType);
    } catch (GErrorException e) {
      // TODO log the error
      avahiListener.onStartDiscoveryFailed(serviceType, e.getCode());
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

    final AvahiDiscoveryListener avahiListener = mDiscoveryListenerMap.remove(listener);
    if (avahiListener != null) {
      avahiListener.setServiceBrowser(null);
      avahiListener.onDiscoveryStopped(avahiListener.getServiceType());
    } else {
      throw new IllegalArgumentException("listener not registered");
    }
  }

  /**
   * Resolve a discovered service. An Application can resolve a service right
   * before establishing a connection to fetch the IP and port details on which
   * to setup the connection.
   * 
   * @param serviceInfo
   *          The service to be resolved.
   * @param listener
   *          To receive callback upon success or failure. Cannot be
   *          {@code null}. Cannot be in use for an active service resolution.
   */
  public void resolveService(final NsdServiceInfo serviceInfo, final ResolveListener listener) {
    final AvahiNsdServiceInfo as = (AvahiNsdServiceInfo) serviceInfo;
    final AvahiServiceResolver resolver = new AvahiServiceResolver(as.iface, as.prototol,
        as.getServiceName(), as.getServiceType(), as.domain, as.prototol, AvahiServiceResolver.LOOKUP_USE_MULTICAST);
    final AvahiResolveListener avahiListener = new AvahiResolveListener(listener);
    avahiListener.setServiceResolver(resolver);
    avahiListener.connect(new AvahiServiceResolver.FoundSignalHandler() {
      @Override
      protected void onSignal(AvahiServiceResolver resolver, int iface, int protocol,
          String name, String type, String domain, String hostname, Address address, short port, Pointer txt, int flags) {
        final NsdServiceInfo info = getServiceInfo(name, type);
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

          byte[] data = new byte[4096];
          int size = AvahiCommonLibrary.INSTANCE.avahi_string_list_serialize(txt, data, data.length);
          info.setTxtRecord(new DnsSdTxtRecord(data, size));

          avahiListener.onServiceResolved(info);
        } else {
          avahiListener.onResolveFailed(serviceInfo, 0); // TODO specify error
                                                         // code
        }
      }
    });
    avahiListener.connect(new AvahiServiceResolver.FailureSignalHandler() {
      @Override
      protected void onSignal(AvahiServiceResolver resolver, GError error) {
        listener.onResolveFailed(serviceInfo, error.code);
      }
    });

    try {
      resolver.attach(client);
    } catch (GErrorException e) {
      e.printStackTrace();
      avahiListener.onResolveFailed(serviceInfo, 0); // TODO specify error code
    }
  }

  /**
   * Register a service to be discovered by others.
   * <p>
   * The method call immediately returns after sending a request to register
   * service to the framework. The application is notified of a success to
   * initiate discovery through the callback
   * {@link RegistrationListener#onServiceRegistered} or a failure through
   * {@link RegistrationListener#onRegistrationFailed}.
   * 
   * @param serviceInfo
   *          The service being registered.
   * @param protocolType
   *          The service discovery protocol.
   * @param listener
   *          The listener notifies of a successful registration and is used to
   *          unregister this service through a call on
   *          {@link #unregisterService}. Cannot be {@code null}.
   */
  public void registerService(final NsdServiceInfo s, int protocolType, final RegistrationListener listener) {
    if (TextUtils.isEmpty(s.getServiceName()) ||
        TextUtils.isEmpty(s.getServiceType())) {
      throw new IllegalArgumentException("Service name or type cannot be empty");
    }
    if (s.getPort() <= 0) {
      throw new IllegalArgumentException("Invalid port number");
    }
    if (listener == null) {
      throw new IllegalArgumentException("listener cannot be null");
    }
    if (protocolType != PROTOCOL_DNS_SD) {
      throw new IllegalArgumentException("Unsupported protocol");
    }

    final AvahiRegistrationListener avahiListener = new AvahiRegistrationListener(s, listener);
    final EntryGroup entryGroup = new EntryGroup();
    avahiListener.setEntryGroup(entryGroup);
    avahiListener.connect(new EntryGroup.StateChangedSignalHandler() {
      @Override
      protected void onSignal(EntryGroup group, int state) {
        switch (state) {
        case EntryGroup.STATE_UNCOMMITED:
          break;
        case EntryGroup.STATE_REGISTERING:
          break;
        case EntryGroup.STATE_ESTABLISHED:
          avahiListener.onServiceRegistered(s);
          break;
        case EntryGroup.STATE_COLLISTION:
          break;
        case EntryGroup.STATE_FAILURE:
          avahiListener.onRegistrationFailed(s, 0); // TODO error code
          break;
        }
      }
    });
    
    mRegistrationListenerMap.put(listener, avahiListener);

    try {
      entryGroup.attach(client);
      entryGroup.addService(s.getServiceName(), s.getServiceType(), s.getPort(), s.getTxtRecord());
      entryGroup.commit();
    } catch (GErrorException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      avahiListener.onRegistrationFailed(s, 0); // TODO error code
    }
  }

  /**
   * Unregister a service registered through {@link #registerService}. A
   * successful unregister is notified to the application with a call to
   * {@link RegistrationListener#onServiceUnregistered}.
   * 
   * @param listener
   *          This should be the listener object that was passed to
   *          {@link #registerService}. It identifies the service that should be
   *          unregistered and notifies of a successful unregistration.
   */
  public void unregisterService(RegistrationListener listener) {
    if (listener == null) {
      throw new IllegalArgumentException("listener cannot be null");
    }

    final AvahiRegistrationListener avahiListener = mRegistrationListenerMap.remove(listener);
    if (avahiListener != null) {
      avahiListener.setEntryGroup(null);
      avahiListener.onServiceUnregistered(avahiListener.getServiceInfo());
    } else {
      throw new IllegalArgumentException("listener not registered");
    }
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

}
