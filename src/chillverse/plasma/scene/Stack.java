package chillverse.plasma.scene;

import chillverse.plasma.scene.layout.BinLayout;

import com.sun.jna.Pointer;

public class Stack extends Container {

  public Stack() {
    super();
    init();
  }

  protected Stack(Pointer ptr) {
    super(ptr);
    init();
  }

  private void init() {
    final BinLayout layout = new BinLayout();
    setLayoutManager(layout);
  }

}
