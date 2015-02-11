package chillverse.jna.gdkpixbuf;

import chillverse.jna.GdkPixbufLibrary;
import chillverse.jna.gobject.GErrorException;
import chillverse.jna.gobject.GObject;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

/**
 * {@code GdkPixbufLoader} provides a way for applications to drive the process
 * of loading an image, by letting them send the image data directly to the
 * loader instead of having the loader read the data from a file.
 * <p>
 * To use {@code GdkPixbufLoader} to load an image, just create a new one, and
 * call {@link #write} to send the data to it. When done, {@link #clone} should
 * be called to end the stream and finalize everything. The loader will emit
 * three important signals throughout the process. The first,
 * {@code size-prepared"}, will be emitted as soon as the image has enough
 * information to determine the size of the image to be used. If you want to
 * scale the image while loading it, you will call {@link #setSize} in response
 * to this signal. The second signal, {@code area-prepared"}, will be emitted as
 * soon as the pixbuf of the desired has been allocated. You can obtain it by
 * calling {@link #getPixbuf}. The last signal, {@code area-updated}, gets
 * emitted every time a region is updated. This way you can update a partially
 * completed image.
 */
public class GdkPixbufLoader extends GObject {

  /**
   * Creates a new pixbuf loader object.
   */
  public GdkPixbufLoader() {
    this(GdkPixbufLibrary.INSTANCE.gdk_pixbuf_loader_new());
  }

  protected GdkPixbufLoader(Pointer ptr) {
    super(ptr);
  }

  /**
   * This will cause a pixbuf loader to parse the next count bytes of an image.
   * 
   * @param buffer
   *          The image data.
   * @throws GErrorException
   */
  public void write(byte[] buffer, int nbytes) throws GErrorException {
    final PointerByReference error = new PointerByReference(null);
    if (!GdkPixbufLibrary.INSTANCE.gdk_pixbuf_loader_write(this, buffer, nbytes, error)) {
      throw new GErrorException(error);
    }
  }

  /**
   * Causes the image to be scaled while it is loaded.
   * 
   * @param width
   *          The desired width of the image being loaded.
   * @param height
   *          The desired height of the image being loaded.
   */
  public void setSize(int width, int height) {
    GdkPixbufLibrary.INSTANCE.gdk_pixbuf_loader_set_size(this, width, height);
  }

  /**
   * Queries the {@link GdkPixbuf} that a pixbuf loader is currently creating.
   * In general it only makes sense to call this function after the
   * "area-prepared" signal has been emitted by the loader; this means that
   * enough data has been read to know the size of the image that will be
   * allocated.
   * 
   * @return The {@link GdkPixbuf} that the loader is creating, or {@code null}
   *         if not enough data has been read to determine how to create the
   *         image buffer.
   */
  public GdkPixbuf getPixbuf() {
    return GdkPixbufLibrary.INSTANCE.gdk_pixbuf_loader_get_pixbuf(this);
  }

  /**
   * Informs the pixbuf loader that no further writes with {@link #write} will
   * occur, so that it can free its internal loading structures. Also, tries to
   * parse any data that hasn't yet been parsed; if the remaining data is
   * partial or corrupt, an error will be returned.
   * 
   * @throws GErrorException
   */
  public void close() throws GErrorException {
    final PointerByReference error = new PointerByReference(null);
    if (!GdkPixbufLibrary.INSTANCE.gdk_pixbuf_loader_close(this, error)) {
      throw new GErrorException(error);
    }
  }

}
