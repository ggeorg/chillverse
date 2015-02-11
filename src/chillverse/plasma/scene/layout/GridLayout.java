package chillverse.plasma.scene.layout;

import chillverse.jna.ClutterLibrary;
import chillverse.plasma.scene.Actor;

import com.sun.jna.Pointer;

/** A layout manager for a grid of actors. */
public class GridLayout extends LayoutManager {

  public static final int GRID_POSITION_LEFT = 0;
  public static final int GRID_POSITION_RIGHT = 1;
  public static final int GRID_POSITION_TOP = 2;
  public static final int GRID_POSITION_BOTTOM = 3;

  public GridLayout() {
    this(ClutterLibrary.INSTANCE.clutter_grid_layout_new());
  }

  protected GridLayout(Pointer ptr) {
    super(ptr);
  }

  public void attach(Actor child, int left, int top, int width, int height) {
    ClutterLibrary.INSTANCE.clutter_grid_layout_attach(this, child, left, top, width, height);
  }

  public void attachNextTo(Actor child, Actor sibling, int side, int width, int height) {
    ClutterLibrary.INSTANCE.clutter_grid_layout_attach_next_to(this, child, sibling, side, width, height);
  }

  public Actor getChildAt(int left, int top) {
    return ClutterLibrary.INSTANCE.clutter_grid_layout_get_child_at(this, left, top);
  }

  public void insertColumn(int position) {
    ClutterLibrary.INSTANCE.clutter_grid_layout_insert_column(this, position);
  }

  public void insertRow(int position) {
    ClutterLibrary.INSTANCE.clutter_grid_layout_insert_row(this, position);
  }

  public void insertNextTo(Actor sibling, int side) {
    ClutterLibrary.INSTANCE.clutter_grid_layout_insert_next_to(this, sibling, side);
  }

  public void setOrientation(int orientation) {
    ClutterLibrary.INSTANCE.clutter_grid_layout_set_orientation(this, orientation);
  }

  public int getOrientation() {
    return ClutterLibrary.INSTANCE.clutter_grid_layout_get_orientation(this);
  }

  public void setColumnHomogeneous(boolean homogeneous) {
    ClutterLibrary.INSTANCE.clutter_grid_layout_set_column_homogeneous(this, homogeneous);
  }

  public boolean getColumnHomogeneous() {
    return ClutterLibrary.INSTANCE.clutter_grid_layout_get_column_homogeneous(this);
  }

  public void setRowHomogeneous(boolean homogeneous) {
    ClutterLibrary.INSTANCE.clutter_grid_layout_set_row_homogeneous(this, homogeneous);
  }

  public boolean getRowHomogeneous() {
    return ClutterLibrary.INSTANCE.clutter_grid_layout_get_row_homogeneous(this);
  }

  public void setColumnSpacing(int spacing) {
    ClutterLibrary.INSTANCE.clutter_grid_layout_set_column_spacing(this, spacing);
  }

  public int getColumnSpacing() {
    return ClutterLibrary.INSTANCE.clutter_grid_layout_get_column_spacing(this);
  }

  public void setRowSpacing(int spacing) {
    ClutterLibrary.INSTANCE.clutter_grid_layout_set_row_spacing(this, spacing);
  }

  public int getRowSpacing() {
    return ClutterLibrary.INSTANCE.clutter_grid_layout_get_row_spacing(this);
  }

}
