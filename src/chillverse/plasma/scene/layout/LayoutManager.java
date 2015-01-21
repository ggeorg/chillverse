package chillverse.plasma.scene.layout;


import chillverse.jna.gobject.GObject;

import com.sun.jna.Pointer;

public abstract class LayoutManager extends GObject {

  protected LayoutManager(Pointer ptr) {
    super(ptr);
  }

}
