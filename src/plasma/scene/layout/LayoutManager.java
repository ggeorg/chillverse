package plasma.scene.layout;

import gobject4j.GObject;

import com.sun.jna.Pointer;

public abstract class LayoutManager extends GObject {

  protected LayoutManager(Pointer ptr) {
    super(ptr);
  }

}
