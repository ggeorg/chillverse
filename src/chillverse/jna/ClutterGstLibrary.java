package chillverse.jna;

import java.util.HashMap;

import chillverse.jna.mapper.GTypeMapper;
import chillverse.plasma.scene.Video;

import com.sun.jna.Library;
import com.sun.jna.Pointer;

public interface ClutterGstLibrary extends Library {

  String NATIVE_LIBRARY_NAME = "clutter-gst-2.0";

  @SuppressWarnings("serial")
  static ClutterGstLibrary INSTANCE = ChillversePlatform.loadLibrary(NATIVE_LIBRARY_NAME, ClutterGstLibrary.class, new HashMap<String, Object>() {
    {
      put(Library.OPTION_TYPE_MAPPER, new GTypeMapper());
    }
  });
  
//@formatter:off
  void clutter_gst_init(int argc, Pointer argv);
  Pointer clutter_gst_video_texture_new();

  void clutter_media_set_filename(Video self, String filename);

  void clutter_media_set_playing(Video self, int playing);
//@formatter:on
}
