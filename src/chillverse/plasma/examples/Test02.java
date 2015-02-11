package chillverse.plasma.examples;

import chillverse.jna.avahi.Address;
import chillverse.jna.avahi.AvahiClient;
import chillverse.jna.avahi.AvahiConstants;
import chillverse.jna.avahi.EntryGroup;
import chillverse.jna.avahi.AvahiServiceBrowser;
import chillverse.jna.avahi.AvahiServiceResolver;
import chillverse.jna.avahi.AvahiServiceBrowser.NewServiceSignalHandler;
import chillverse.jna.avahi.AvahiServiceResolver.FoundSignalHandler;
import chillverse.jna.gobject.GErrorException;
import chillverse.net.nsd.DnsSdTxtRecord;
import chillverse.net.nsd.NsdManager;
import chillverse.net.nsd.NsdServiceInfo;
import chillverse.plasma.scene.Actor;
import chillverse.plasma.scene.Actor.DestroySignalHandler;
import chillverse.plasma.scene.Color;
import chillverse.plasma.scene.Stage;
import chillverse.plasma.scene.Text;
import chillverse.plasma.signal.SignalConnection;

import com.sun.jna.Pointer;

public class Test02 {
  public static void main(String[] args) {
    Stage.init();

    Text text = new Text();
    text.setColor(Color.WHITE);

    Stage stage = new Stage();
    stage.setSize(720, 512);
    stage.setBackgroundColor(Color.BLUE);
    stage.addChild(text);
    stage.show();

    SignalConnection h = stage.connect(new DestroySignalHandler() {
      @Override
      protected void onSignal(Actor actor) {
        Stage.mainQuit();
      }
    });

    startAvahi();

    Stage.mainStart();

    // System.out.println("=============");
    //
    // stage = null;
    //
    // Stage.mainStart();

  }

  private static AvahiClient client = null;

  private static void startAvahi2() {
    client = new AvahiClient();
    client.connect(new AvahiClient.StateChangedSignalHandler() {
      @Override
      protected void onSignal(AvahiClient client, int state) {
        if (state == AvahiConstants.STATE_S_RUNNING) {
          System.out.println("STATE_S_RUNNING");
          // registerService();
        } else if (state == AvahiConstants.STATE_FAILURE) {
          System.out.println("STATE_FAILURE");
        }
      }
    });

    AvahiServiceBrowser browser = new AvahiServiceBrowser("_http._tcp");
    browser.connect(new NewServiceSignalHandler() {
      @Override
      protected void onSignal(AvahiServiceBrowser browser, int iface, int protocol, String name, String type, String domain, int lookupResultFlags) {
        System.out.println("> " + name);

        final AvahiServiceResolver resolver = new AvahiServiceResolver(iface, protocol, name, type, domain, protocol);
        resolver.connect(new FoundSignalHandler() {
          @Override
          protected void onSignal(AvahiServiceResolver _resolver, int iface, int protocol, String name, String type, String domain, String hostname, Address address, short port, Pointer txt, int flags) {
            System.out.println("> " + hostname);
          }
        });

        try {
          resolver.attach(client);
        } catch (GErrorException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    });

    try {
      client.start();
      browser.attach(client);
    } catch (GErrorException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  protected static void registerService() {
    EntryGroup group = new EntryGroup();
    group.connect(new EntryGroup.StateChangedSignalHandler() {
      @Override
      protected void onSignal(EntryGroup group, int state) {
        System.out.println("===================>" + state);
      }
    });

    try {
      group.attach(client);

      System.out.println(">>>>>>>>>>>>>>>>>>>> ");
      group.addService("lalala", "_http._tcpx", 80, "dtalk=1", "dtype=Yes/1");
      group.commit();

    } catch (GErrorException e) {
      System.out.println(e.getCode());
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private static void startAvahi() {
    final NsdManager nsdManager = new NsdManager();

    final DnsSdTxtRecord txtRecord = new DnsSdTxtRecord();
    txtRecord.set("dtalk", "1.0");
    txtRecord.set("dtype", "MediaStation/1");
    
    final NsdServiceInfo localService = new NsdServiceInfo("lalala", "_http._tcp", 333, txtRecord);
    
    nsdManager.registerService(localService, NsdManager.PROTOCOL_DNS_SD,
        new NsdManager.RegistrationListener() {

          @Override
          public void onServiceRegistered(NsdServiceInfo serviceInfo) {
            System.out.println(">>> onServiceRegistered: " + serviceInfo);
          }
          
          @Override
          public void onRegistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
            System.out.println(">>> onRegistrationFailed: " + errorCode);
          }

          @Override
          public void onServiceUnregistered(NsdServiceInfo serviceInfo) {
            System.out.println(">>> onServiceUnregistered: " + serviceInfo);
          }

          @Override
          public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
            System.out.println(">>> onUnregistrationFailed: " + errorCode);
          }
        });

    nsdManager.discoverServices("_http._tcp", NsdManager.PROTOCOL_DNS_SD,
        new NsdManager.DiscoveryListener() {
          @Override
          public void onDiscoveryStarted(String serviceType) {
            System.out.println("onDiscoveryStarted: " + serviceType);
          }

          @Override
          public void onDiscoveryStopped(String serviceType) {
            System.out.println("onDiscoveryStopped: " + serviceType);
          }

          @Override
          public void onServiceFound(NsdServiceInfo serviceInfo) {
            System.out.println(">>> onServiceFound: " + serviceInfo);

            nsdManager.resolveService(serviceInfo, new NsdManager.ResolveListener() {
              public void onServiceResolved(NsdServiceInfo serviceInfo) {
                System.out.println(">>> onServiceResolved: " + serviceInfo);
              }

              public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
                System.out.println(">>> onResolveFailed: " + serviceInfo);
              }
            });
          }

          @Override
          public void onServiceLost(NsdServiceInfo serviceInfo) {
            System.out.println("onServiceLost: " + serviceInfo);
          }

          @Override
          public void onStartDiscoveryFailed(String serviceType, int errorCode) {
            System.out.println("onStartDiscoveryFailed: " + errorCode);
          }

          @Override
          public void onStopDiscoveryFailed(String serviceType, int errorCode) {
            System.out.println("onStopDiscoveryFailed: " + errorCode);
          }
        });

  }
}
