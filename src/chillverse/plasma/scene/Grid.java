package chillverse.plasma.scene;

import chillverse.plasma.scene.layout.GridLayout;

import com.sun.jna.Pointer;

public class Grid extends Container {
  
  private final GridLayout layout = new GridLayout();

  public Grid() {
    super();
    init();
  }

  protected Grid(Pointer ptr) {
    super(ptr);
    init();
  }

  private void init() {
    setLayoutManager(layout);
  }

//  @Override
//  public int add(Actor child) {
//    layout.attach(child, child.getColumn(), child.getRow(), child.getColspan(), child.getRowspan());
//  }
}
