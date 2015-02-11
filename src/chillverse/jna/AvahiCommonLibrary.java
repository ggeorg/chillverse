package chillverse.jna;

import java.util.HashMap;

import chillverse.jna.mapper.GTypeMapper;

import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

public interface AvahiCommonLibrary extends Library {

  String NATIVE_LIBRARY_NAME = "avahi-common";

  @SuppressWarnings("serial")
  static AvahiCommonLibrary INSTANCE = ChillversePlatform.loadLibrary(NATIVE_LIBRARY_NAME, AvahiCommonLibrary.class, new HashMap<String, Object>() {
    {
      put(Library.OPTION_TYPE_MAPPER, new GTypeMapper());
    }
  });

//@formatter:off
  void avahi_free(Pointer p);
  Pointer avahi_string_list_to_string(Pointer list);
  int avahi_string_list_serialize(Pointer list, byte[] data, int size);
  int avahi_string_list_parse(byte[] data, int size, PointerByReference ret);
  void avahi_string_list_free(Pointer list);
//@formatter:on
}
