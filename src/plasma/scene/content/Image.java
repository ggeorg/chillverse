package plasma.scene.content;

import gobject4j.GObject;

import com.sun.jna.Pointer;

public class Image extends GObject implements HasContent {

  protected Image(Pointer ptr) {
    super(ptr);
  }

}
