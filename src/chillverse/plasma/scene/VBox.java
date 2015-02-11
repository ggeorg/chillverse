package chillverse.plasma.scene;

import chillverse.plasma.scene.layout.BoxLayout;

import com.sun.jna.Pointer;

public class VBox extends Box {

  public VBox() {
    super();
  }

  protected VBox(Pointer ptr) {
    super(ptr);
  }

  @Override
  protected int getOrientation() {
    return BoxLayout.ORIENTATION_VERTICAL;
  }
  
}
