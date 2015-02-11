package chillverse.jna.gdkpixbuf;

import chillverse.jna.GdkPixbufLibrary;
import chillverse.jna.gobject.GObject;

import com.sun.jna.Pointer;

public class GdkPixbuf extends GObject {

  public GdkPixbuf() {
    this(GdkPixbufLibrary.INSTANCE.gdk_pixbuf_loader_new());
  }
  
  protected GdkPixbuf(Pointer ptr) {
    super(ptr);
  }
  
  public byte[] getPixels() {
    return GdkPixbufLibrary.INSTANCE.gdk_pixbuf_get_pixels(this);
  }
  
  public boolean hasAlpha() {
    return GdkPixbufLibrary.INSTANCE.gdk_pixbuf_has_alpha(this);
  }
  
  public int getWidth() {
    return GdkPixbufLibrary.INSTANCE.gdk_pixbuf_get_width(this);
  }
  
  public int getHeight() {
    return GdkPixbufLibrary.INSTANCE.gdk_pixbuf_get_height(this);
  }
  
  public int getRowStride() {
    return GdkPixbufLibrary.INSTANCE.gdk_pixbuf_get_rowstride(this);
  }
  
}
