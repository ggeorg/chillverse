package chillverse.jna;

import java.util.HashMap;

import chillverse.jna.gdkpixbuf.GdkPixbuf;
import chillverse.jna.gdkpixbuf.GdkPixbufLoader;
import chillverse.jna.mapper.GTypeMapper;

import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

/**
 * 
 */
public interface GdkPixbufLibrary extends Library {

  String NATIVE_LIBRARY_NAME = "gdk_pixbuf-2.0";

  @SuppressWarnings("serial")
  static GdkPixbufLibrary INSTANCE = ChillversePlatform.loadLibrary(NATIVE_LIBRARY_NAME, GdkPixbufLibrary.class, new HashMap<String, Object>() {
    {
      put(Library.OPTION_TYPE_MAPPER, new GTypeMapper());
    }
  });

//@formatter:off
  Pointer gdk_pixbuf_loader_new();
  //Pointer gdk_pixbuf_loader_new_with_type(String image_type, PointerByReference error);
  //Pointer gdk_pixbuf_loader_new_with_mime_type(String mime_type, PointerByReference error);
  // TODO gdk_pixbuf_loader_get_format
  boolean gdk_pixbuf_loader_write(GdkPixbufLoader self, byte[] buf, int count, PointerByReference error);
  void gdk_pixbuf_loader_set_size(GdkPixbufLoader self, int width, int height);
  GdkPixbuf gdk_pixbuf_loader_get_pixbuf(GdkPixbufLoader self);
  // TODO gdk_pixbuf_loader_get_animation
  boolean gdk_pixbuf_loader_close (GdkPixbufLoader self, PointerByReference error);
//@formatter:on

//@formatter:off
  byte[] gdk_pixbuf_get_pixels(GdkPixbuf self);
  boolean gdk_pixbuf_has_alpha(GdkPixbuf self);
  int gdk_pixbuf_get_width(GdkPixbuf self);
  int gdk_pixbuf_get_height(GdkPixbuf self);
  int gdk_pixbuf_get_rowstride(GdkPixbuf self);
//@formatter:on

}
