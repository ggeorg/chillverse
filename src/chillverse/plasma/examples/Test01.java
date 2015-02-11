package chillverse.plasma.examples;

import java.util.Map;

import org.apache.pivot.beans.BXMLSerializer;

import chillverse.plasma.application.Application;
import chillverse.plasma.application.DesktopApplicationContext;
import chillverse.plasma.scene.Stage;
import chillverse.plasma.scene.Window;

public class Test01 implements Application {

  public void init(Map<String, String> properties) {
    System.out.println("init:" + Thread.currentThread());
  }

  public void startup(Stage primaryStage) throws Exception {
    System.out.println("startup:" + Thread.currentThread());
    
    BXMLSerializer bxmlSerializer = new BXMLSerializer();
    Window topLevel = (Window) bxmlSerializer.readObject(Test01.class, "/test01.xml");
    topLevel.show(primaryStage);
  }

  public boolean shutdown(boolean optional) throws Exception {
    // TODO Auto-generated method stub
    return false;
  }

  public void suspended() throws Exception {
    // TODO Auto-generated method stub

  }

  public void resume() throws Exception {
    // TODO Auto-generated method stub

  }

  public static void main(String[] args) {
    DesktopApplicationContext.main(Test01.class, args);
  }
}
