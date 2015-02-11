package chillverse.plasma.examples.appletrunner;

import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AudioClip;
import java.awt.Image;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * {@code CommonAppletContext} represents the common context stuff for both
 * types, plugins and standalone.
 */
abstract class CommonAppletContext implements AppletContext {
  // FIXME: this needs to be static, and we need one AppletContext per
  // Applet.
  List<Applet> applets = new ArrayList<Applet>();
  HashMap<String, InputStream> streams = new HashMap<String, InputStream>();

  void addApplet(Applet applet) {
    applets.add(applet);
  }

  // -------------------------------------------------------------------
  // AppletContext methods
  // -------------------------------------------------------------------

  public AudioClip getAudioClip(URL url) {
    return Applet.newAudioClip(url);
  }

  @Deprecated
  public Image getImage(URL url) {
    throw new UnsupportedOperationException();
  }

  public Applet getApplet(String name) {
    Applet a;
    String appletName;
    Iterator<Applet> i = applets.iterator();

    while (i.hasNext()) {
      a = (Applet) i.next();

      appletName = a.getParameter("name");
      if (a != null && appletName != null && appletName.equals(name))
        return a;
    }
    return null;
  }

  public Enumeration<Applet> getApplets() {
    return Collections.enumeration(applets);
  }

  public void showDocument(URL url) {
    showDocument(url, "_self");
  }

  // FIXME: implement. public abstract void showDocument (URL url, String
  // target);
  
  // FIXME: implement. public abstract void showStatus (String status);
  
  public void setStream(String key, InputStream stream) {
    streams.put(key, stream);
  }
  
  public InputStream getStream(String key) {
    return streams.get(key);
  }
  
  public Iterator<String> getStreamKeys() {
    return streams.keySet().iterator();
  }
}
