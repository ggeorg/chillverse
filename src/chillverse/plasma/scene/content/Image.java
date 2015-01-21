package chillverse.plasma.scene.content;


import chillverse.jna.gobject.GObject;

import com.sun.jna.Pointer;

public class Image extends GObject implements HasContent {

  protected Image(Pointer ptr) {
    super(ptr);
  }

}
