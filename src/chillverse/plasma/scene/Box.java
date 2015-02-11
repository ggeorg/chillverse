package chillverse.plasma.scene;

import chillverse.plasma.scene.layout.BoxLayout;

import com.sun.jna.Pointer;

public abstract class Box extends Container {

  public Box() {
    super();
    init();
  }

  protected Box(Pointer ptr) {
    super(ptr);
    init();
  }

  private void init() {
    final BoxLayout boxLayout = new BoxLayout();
    boxLayout.setOrientation(getOrientation());
    boxLayout.setSpacing(5);
    setLayoutManager(boxLayout);
  }

  protected abstract int getOrientation();

  public void setPackStart(boolean packStart) {
    setPackStart(packStart);
  }

  public boolean isPackStart() {
    return ((BoxLayout) getLayoutManager()).isPackStart();
  }

  public void setSpacing(int spacing) {
    ((BoxLayout) getLayoutManager()).setSpacing(spacing);
  }

  public int getSpacing() {
    return ((BoxLayout) getLayoutManager()).getSpacing();
  }

  public void setHomogeneous(boolean homogeneous) {
    ((BoxLayout) getLayoutManager()).setHomogeneous(homogeneous);
  }

  public boolean isHomogeneous() {
    return ((BoxLayout) getLayoutManager()).isHomogeneous();
  }

}
