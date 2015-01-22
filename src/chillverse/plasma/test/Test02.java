package chillverse.plasma.test;

import chillverse.jna.gobject.GErrorException;
import chillverse.jna.gobject.avahi.Address;
import chillverse.jna.gobject.avahi.AvahiClient;
import chillverse.jna.gobject.avahi.EntryGroup;
import chillverse.jna.gobject.avahi.ServiceBrowser;
import chillverse.jna.gobject.avahi.ServiceBrowser.NewServiceSignalHandler;
import chillverse.jna.gobject.avahi.ServiceResolver;
import chillverse.jna.gobject.avahi.ServiceResolver.FoundSignalHandler;
import chillverse.net.nsd.NsdManager;
import chillverse.net.nsd.NsdServiceInfo;
import chillverse.plasma.scene.Actor;
import chillverse.plasma.scene.Actor.DestroySignalHandler;
import chillverse.plasma.scene.Color;
import chillverse.plasma.scene.Stage;
import chillverse.plasma.scene.text.Text;

import com.sun.jna.Pointer;

public class Test02 {
  public static void main(String[] args) {
    Stage.init();

    Actor a1 = new Actor();
    a1.setSize(256, 256);
    a1.setBackgroundColor(Color.RED);
    a1.setPosition(10, 10);

    Text txt1 = new Text();
    txt1.setMarkup("<b>Hello</b><i>, World!</i>");
    txt1.setColor(new Color(255, 255, 255, 128));
    txt1.setPosition(50, 50);
    txt1.setFontName("Arial 24px");
    // txt1.setSelectable(true);
    // txt1.setEditable(true);

    Stage stage = new Stage();
    stage.setSize(720, 512);
    stage.setBackgroundColor(Color.BLUE);
    stage.addChild(a1);
    stage.addChild(txt1);
    stage.show();

    stage.connect(new DestroySignalHandler() {
      @Override
      protected void onSignal(Actor actor) {
        System.out.println("==========");
        Stage.mainQuit();
      }
    });

    startAvahi2();

    Stage.mainStart();
  }

  private static AvahiClient client = null;

  private static void startAvahi2() {
    client = new AvahiClient();
    client.connect(new AvahiClient.StateChangedSignalHandler() {
      @Override
      protected void onSignal(AvahiClient client, int state) {
        if (state == AvahiClient.STATE_S_RUNNING) {
          registerService();
        }
      }
    });

    ServiceBrowser browser = new ServiceBrowser("_http._tcp");
    browser.connect(new NewServiceSignalHandler() {
      @Override
      protected void onSignal(ServiceBrowser browser, int iface, int protocol, String name, String type, String domain, int lookupResultFlags) {
        final ServiceResolver resolver = new ServiceResolver(iface, protocol, name, type, domain, protocol);
        resolver.connect(new FoundSignalHandler() {
          @Override
          protected void onSignal(ServiceResolver _resolver, int iface, int protocol, String name, String type, String domain, String hostname, Address address, short port, Pointer txt, int flags) {
            System.out.println("> "+hostname);
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
    nsdManager.discoverServices("_http._tcp", NsdManager.PROTOCOL_DNS_SD, new NsdManager.DiscoveryListener() {
      public void onDiscoveryStarted(String serviceType) {
        System.out.println("onDiscoveryStarted: " + serviceType);
      }

      public void onDiscoveryStopped(String serviceType) {
        System.out.println("onDiscoveryStopped: " + serviceType);
      }

      public void onServiceFound(NsdServiceInfo serviceInfo) {
        System.out.println("onServiceFound: " + serviceInfo);

        nsdManager.resolveService(serviceInfo, new NsdManager.ResolveListener() {
          public void onServiceResolved(NsdServiceInfo serviceInfo) {
            System.out.println("onServiceResolved: " + serviceInfo);
          }

          public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
            System.out.println("onResolveFailed: " + serviceInfo);
          }
        });
      }

      public void onServiceLost(NsdServiceInfo serviceInfo) {
        System.out.println("onServiceLost: " + serviceInfo);
      }

      public void onStartDiscoveryFailed(String serviceType, int errorCode) {
        System.out.println("onStartDiscoveryFailed: " + errorCode);
      }

      public void onStopDiscoveryFailed(String serviceType, int errorCode) {
        System.out.println("onStopDiscoveryFailed: " + errorCode);
      }
    });

  }
}
