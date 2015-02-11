package chillverse.jna;

import java.util.HashMap;

import chillverse.jna.glib.GList;
import chillverse.jna.mapper.GTypeMapper;

import com.sun.jna.Library;
import com.sun.jna.Pointer;

public interface GlibLibrary extends Library {
  
  String NATIVE_LIBRARY_NAME = "glib-2.0";
  
  @SuppressWarnings("serial")
  static GlibLibrary INSTANCE = ChillversePlatform.loadLibrary(NATIVE_LIBRARY_NAME, GlibLibrary.class, new HashMap<String, Object>() {
    {
      put(Library.OPTION_TYPE_MAPPER, new GTypeMapper());
    }
  });

//@formatter:off
  int g_list_index (GList self, Pointer ptr);
//@formatter:on
}
