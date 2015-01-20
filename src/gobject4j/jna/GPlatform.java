package gobject4j.jna;

import java.util.Map;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

public class GPlatform {

  public static <T extends Library> T loadLibrary(String name, Class<T> interfaceClass, Map<String, Object> options) {
    if (Platform.isLinux()) {
      return loadNativeLibrary(name, interfaceClass, options);
    } else {
      throw new UnsupportedOperationException();
    }
  }

  @SuppressWarnings("unchecked")
  private static <T extends Library> T loadNativeLibrary(String name, Class<T> interfaceClass, Map<String, ?> options) {
    return (T) Native.loadLibrary(name, interfaceClass, options);
  }

}
