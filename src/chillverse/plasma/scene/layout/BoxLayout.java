package chillverse.plasma.scene.layout;

import chillverse.jna.ClutterLibrary;

import com.sun.jna.Pointer;

public class BoxLayout extends LayoutManager {
  
  public static final int ORIENTATION_HORIZONTAL = 0;
  
  public static final int ORIENTATION_VERTICAL = 1;

  public BoxLayout() {
    this(ClutterLibrary.INSTANCE.clutter_box_layout_new());
  }
  
  protected BoxLayout(Pointer ptr) {
    super(ptr);
  }

  public void setPackStart(boolean packStart) {
    ClutterLibrary.INSTANCE.clutter_box_layout_set_pack_start(this, packStart);
  }
  
  public boolean isPackStart() {
    return ClutterLibrary.INSTANCE.clutter_box_layout_get_pack_start(this);
  }
  
  public void setSpacing(int spacing) {
    ClutterLibrary.INSTANCE.clutter_box_layout_set_spacing(this, spacing);
  }
  
  public int getSpacing() {
    return ClutterLibrary.INSTANCE.clutter_box_layout_get_spacing(this);
  }
  
  public void setHomogeneous(boolean homogeneous) {
    ClutterLibrary.INSTANCE.clutter_box_layout_set_homogeneous(this, homogeneous);
  }
  
  public boolean isHomogeneous() {
    return ClutterLibrary.INSTANCE.clutter_box_layout_get_homogeneous(this);
  }
  
  public void setOrientation(int orientation) {
    ClutterLibrary.INSTANCE.clutter_box_layout_set_orientation(this, orientation);
  }
  
  public int getOrientation() {
    return ClutterLibrary.INSTANCE.clutter_box_layout_get_orientation(this);
  }
}
