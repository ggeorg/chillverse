package chillverse.plasma.scene.content;


import chillverse.jna.gobject.GObject;

import com.sun.jna.Pointer;

/**
 * {@code Canvas} class is a {@link HasContent} implementation that allows,
 * drawing on a 2D surface.
 */
public class Canvas extends GObject implements HasContent {

  protected Canvas(Pointer ptr) {
    super(ptr);
  }

}
