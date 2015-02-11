package chillverse.plasma.scene.layout;

import chillverse.jna.ClutterLibrary;
import chillverse.plasma.scene.Actor;

import com.sun.jna.Pointer;

public class BinLayout extends LayoutManager {

  public BinLayout() {
    this(ClutterLibrary.INSTANCE.clutter_bin_layout_new(Actor.ALIGN_CENTER, Actor.ALIGN_CENTER));
  }
  
  protected BinLayout(Pointer ptr) {
    super(ptr);
  }

}
