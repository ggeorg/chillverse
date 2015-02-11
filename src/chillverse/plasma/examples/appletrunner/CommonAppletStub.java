package chillverse.plasma.examples.appletrunner;

import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.net.URL;

class CommonAppletStub implements AppletStub {
  private AppletContext context;
  private Applet applet;

  CommonAppletStub(AppletContext context, Applet applet) {
    this.context = context;
    this.applet = applet;
  }

  public boolean isActive() {
    return true;
  }

  public URL getDocumentBase() {
    throw new UnsupportedOperationException();
  }

  public URL getCodeBase() {
    throw new UnsupportedOperationException();
  }

  public String getParameter(String name) {
    throw new UnsupportedOperationException();
  }

  public AppletContext getAppletContext() {
    return context;
  }
  
  public void appletResize(int width, int height) {
    applet.setBounds(0, 0, width, height);
  }
}
