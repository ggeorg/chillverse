package chillverse.plasma.scene.content;

import chillverse.jna.ClutterLibrary;
import chillverse.jna.cogl.CoglPixelFormat;
import chillverse.jna.gdkpixbuf.GdkPixbuf;
import chillverse.jna.gobject.GErrorException;
import chillverse.jna.gobject.GObject;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

public class Image extends GObject implements Content {

  public Image() {
    this(ClutterLibrary.INSTANCE.clutter_image_new());
  }

  protected Image(Pointer ptr) {
    super(ptr);
  }

  public void setData(GdkPixbuf pixbuf) throws GErrorException {
    final PointerByReference error = new PointerByReference(null);
    if (ClutterLibrary.INSTANCE.clutter_image_set_data(this, pixbuf.getPixels(), pixbuf.hasAlpha() ?
        CoglPixelFormat.COGL_PIXEL_FORMAT_RGBA_8888 : CoglPixelFormat.COGL_PIXEL_FORMAT_RGB_888,
        pixbuf.getWidth(), pixbuf.getHeight(), pixbuf.getRowStride(), error)) {
      throw new GErrorException(error);
    }
  }

}
