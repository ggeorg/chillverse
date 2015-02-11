package chillverse.plasma.examples.appletrunner;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

public class AppletClassLoader extends URLClassLoader {

  /**
   * Constructs a new {@code AppletClassLoader} object.
   * 
   * @param codebase The code base of the applet.
   * @param archives The urls to add to the search path.
   */
  public AppletClassLoader(URL codebase, ArrayList<URL> archives) {
    super(new URL[0]);
    
    for (int i = 0, n = archives.size(); i < n; i++) {
      addURL(archives.get(i));
    }
    
    addURL(codebase);
  }

}
