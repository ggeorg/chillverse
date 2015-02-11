package chillverse.plasma.scene.constraint;

import com.sun.jna.Pointer;

import chillverse.jna.gobject.GObject;

public abstract class Constraint extends GObject {

  protected Constraint(Pointer ptr) {
    super(ptr);
  }

}
