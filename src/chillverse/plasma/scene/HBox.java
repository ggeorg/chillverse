package chillverse.plasma.scene;

import chillverse.plasma.scene.layout.BoxLayout;

import com.sun.jna.Pointer;

public class HBox extends Box {

  public HBox() {
    super();
  }

  protected HBox(Pointer ptr) {
    super(ptr);
  }

  @Override
  protected int getOrientation() {
    return BoxLayout.ORIENTATION_HORIZONTAL;
  }
  
}
