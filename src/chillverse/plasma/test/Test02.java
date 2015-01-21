package chillverse.plasma.test;

import chillverse.jna.gobject.GErrorException;
import chillverse.jna.gobject.avahi.Avahi;
import chillverse.jna.gobject.avahi.ServiceBrowser;
import chillverse.jna.gobject.avahi.ServiceResolver;
import chillverse.jna.gobject.avahi.Avahi.StateChangedSignalHandler;
import chillverse.jna.gobject.avahi.ServiceBrowser.NewServiceSignalHandler;
import chillverse.plasma.scene.Actor;
import chillverse.plasma.scene.Color;
import chillverse.plasma.scene.Stage;
import chillverse.plasma.scene.Actor.DestroySignalHandler;
import chillverse.plasma.scene.text.Text;

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

    startAvahi();

    Stage.mainStart();
  }

  private static void startAvahi() {
    Avahi c = new Avahi();
    c.connect(new StateChangedSignalHandler() {
      @Override
      protected void onSignal(Avahi client, int state) {
        System.out.println("State changed: " + state);
      }
    });

    try {
      c.start();
    } catch (GErrorException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    ServiceBrowser browser = new ServiceBrowser("_keepalive._dns-sd._udp");
    browser.connect(new NewServiceSignalHandler() {
      @Override
      protected void onSignal(ServiceBrowser srvBrowser, int iface, int protocol, String name, String type, int lookupFlags) {
        System.out.println(String.format("New: %s, type: %s", name, type));
      }
    });
    
    try {
      browser.attach(c);
    } catch (GErrorException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    
  }
}
