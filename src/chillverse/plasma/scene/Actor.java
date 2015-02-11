package chillverse.plasma.scene;

import java.util.Iterator;

import org.apache.pivot.beans.IDProperty;

import chillverse.jna.ClutterLibrary;
import chillverse.jna.NativeObject;
import chillverse.jna.glib.GList;
import chillverse.jna.gobject.GObject;
import chillverse.plasma.geometry.Dimension;
import chillverse.plasma.geometry.Point2D;
import chillverse.plasma.scene.constraint.Constraint;
import chillverse.plasma.scene.layout.LayoutManager;
import chillverse.plasma.signal.SignalConnection;
import chillverse.plasma.signal.SignalHandler;

import com.sun.jna.Pointer;

@IDProperty("name")
public class Actor extends GObject {

  /** Stretch to cover the whole allocated space. */
  public static final int ALIGN_FILL = 0;

  /**
   * Snap to left or top side, leaving space to the right or bottom. For
   * horizontal layouts, in right-to-left locales this should be reversed.
   */
  public static final int ALIGN_START = 0;

  /** Center the actor inside the allocation. */
  public static final int ALIGN_CENTER = 0;

  /**
   * Snap to right or bottom side, leaving space to the left or top. For
   * horizontal layouts, in right-to-left locales this should be reversed.
   */
  public static final int ALIGN_END = 0;

  /**
   * The {@code destroy} signal notifies that all references held on the actor
   * emitted it should be released.
   * <p>
   * The {@code destroy} signal should be used by all holders of a reference on
   * actor.
   * <p>
   * This signal might result in the finalization of the {@code Actor} if all
   * references are released.
   */
  public static final String SIGNAL_DESTROY = "destroy";

  public Actor() {
    this(ClutterLibrary.INSTANCE.clutter_actor_new());
  }

  protected Actor(Pointer ptr) {
    super(ptr);
  }

  public void setName(String name) {
    ClutterLibrary.INSTANCE.clutter_actor_set_name(this, name);
  }

  public String getName() {
    return ClutterLibrary.INSTANCE.clutter_actor_get_name(this);
  }

  public void show() {
    ClutterLibrary.INSTANCE.clutter_actor_show(this);
  }

  public void hide() {
    ClutterLibrary.INSTANCE.clutter_actor_hide(this);
  }

  public void queueRedraw() {
    ClutterLibrary.INSTANCE.clutter_actor_queue_redraw(this);
  }

  // TODO queueRedrawWithClip()

  // TODO destroy

  // TODO event

  public void setHorizontalAlign(String align) {
    if ("fill".equals(align)) {
      setHorizontalAlign(ALIGN_FILL);
    } else if ("start".equals(align)) {
      setHorizontalAlign(ALIGN_START);
    } else if ("center".equals(align)) {
      setHorizontalAlign(ALIGN_CENTER);
    } else if ("end".equals(align)) {
      setHorizontalAlign(ALIGN_END);
    }
  }

  public void setHorizontalAlign(int align) {
    ClutterLibrary.INSTANCE.clutter_actor_set_x_align(this, align);
  }

  public int getHorizontalAlign() {
    return ClutterLibrary.INSTANCE.clutter_actor_get_x_align(this);
  }

  public void setVerticalAlign(String align) {
    if ("fill".equals(align)) {
      setVerticalAlign(ALIGN_FILL);
    } else if ("start".equals(align)) {
      setVerticalAlign(ALIGN_START);
    } else if ("center".equals(align)) {
      setVerticalAlign(ALIGN_CENTER);
    } else if ("end".equals(align)) {
      setVerticalAlign(ALIGN_END);
    }
  }

  public void setVerticalAlign(int align) {
    ClutterLibrary.INSTANCE.clutter_actor_set_y_align(this, align);
  }

  public int getVerticalAlign() {
    return ClutterLibrary.INSTANCE.clutter_actor_get_y_align(this);
  }

  public void setHorizontalExpand(boolean expand) {
    ClutterLibrary.INSTANCE.clutter_actor_set_x_expand(this, expand);
  }

  public boolean isHorizontalExpand() {
    return ClutterLibrary.INSTANCE.clutter_actor_get_x_expand(this);
  }

  public void setVerticalExpand(boolean expand) {
    ClutterLibrary.INSTANCE.clutter_actor_set_y_expand(this, expand);
  }

  public boolean isVerticalExpand() {
    return ClutterLibrary.INSTANCE.clutter_actor_get_y_expand(this);
  }

  protected void setLayoutManager(LayoutManager manager) {
    ClutterLibrary.INSTANCE.clutter_actor_set_layout_manager(this, manager);
  }

  protected LayoutManager getLayoutManager() {
    return ClutterLibrary.INSTANCE.clutter_actor_get_layout_manager(this);
  }

  public void setBackgroundColor(String color) {
    setBackgroundColor(Color.decodeColor(color));
  }

  public void setBackgroundColor(Color c) {
    ClutterLibrary.INSTANCE.clutter_actor_set_background_color(this, c);
  }

  public Color getBackgroundColor() {
    Color c = new Color();
    ClutterLibrary.INSTANCE.clutter_actor_get_background_color(this, c);
    return c;
  }

  public void setSize(float width, float height) {
    ClutterLibrary.INSTANCE.clutter_actor_set_size(this, width, height);
  }

  public Dimension getSize() {
    final Dimension d = new Dimension();
    ClutterLibrary.INSTANCE.clutter_actor_get_size(this, d.width, d.height);
    return d;
  }

  public void setPosition(float x, float y) {
    ClutterLibrary.INSTANCE.clutter_actor_set_position(this, x, y);
  }

  public Point2D getPosition() {
    final Point2D p = new Point2D();
    ClutterLibrary.INSTANCE.clutter_actor_get_position(this, p.x, p.y);
    return p;
  }

  public void setWidth(float width) {
    ClutterLibrary.INSTANCE.clutter_actor_set_width(this, width);
  }

  public float getWidth() {
    return ClutterLibrary.INSTANCE.clutter_actor_get_width(this);
  }

  public void setHeight(float height) {
    ClutterLibrary.INSTANCE.clutter_actor_set_height(this, height);
  }

  public float getHeight() {
    return ClutterLibrary.INSTANCE.clutter_actor_get_height(this);
  }

  public void setX(float x) {
    ClutterLibrary.INSTANCE.clutter_actor_set_x(this, x);
  }

  public float getX() {
    return ClutterLibrary.INSTANCE.clutter_actor_get_x(this);
  }

  public void setY(float y) {
    ClutterLibrary.INSTANCE.clutter_actor_set_y(this, y);
  }

  public float getY() {
    return ClutterLibrary.INSTANCE.clutter_actor_get_y(this);
  }

  public void moveBy(double dx, double dy) {
    ClutterLibrary.INSTANCE.clutter_actor_move_by(this, dx, dy);
  }

  public void setZ(float z) {
    ClutterLibrary.INSTANCE.clutter_actor_set_z_position(this, z);
  }

  public float getZ() {
    return ClutterLibrary.INSTANCE.clutter_actor_get_z_position(this);
  }

  public void setPivotPoint(float x, float y) {
    ClutterLibrary.INSTANCE.clutter_actor_set_pivot_point(this, x, y);
  }

  public Point2D getPivotPoint() {
    final Point2D p = new Point2D();
    ClutterLibrary.INSTANCE.clutter_actor_get_pivot_point(this, p.x, p.y);
    return p;
  }

  public void setPivotPointZ(float z) {
    ClutterLibrary.INSTANCE.clutter_actor_set_pivot_point_z(this, z);
  }

  public float getPivotPointZ() {
    return ClutterLibrary.INSTANCE.clutter_actor_get_pivot_point_z(this);
  }

  public void addChild(Actor child) {
    ClutterLibrary.INSTANCE.clutter_actor_add_child(this, child);
  }

  public void insertChildAbove(Actor child, Actor sibling) {
    ClutterLibrary.INSTANCE.clutter_actor_insert_child_above(this, child.getPtr(), sibling.getPtr());
  }

  public void insertChild(Actor child, int index) {
    ClutterLibrary.INSTANCE.clutter_actor_insert_child_at_index(this, child.getPtr(), index);
  }

  public void insertChildBelow(Actor child, Actor sibling) {
    ClutterLibrary.INSTANCE.clutter_actor_insert_child_below(this, child.getPtr(), sibling.getPtr());
  }

  public void replaceChild(Actor oldChild, Actor newChild) {
    ClutterLibrary.INSTANCE.clutter_actor_replace_child(this, oldChild.getPtr(), newChild.getPtr());
  }

  public void removeChild(Actor child) {
    ClutterLibrary.INSTANCE.clutter_actor_remove_child(this, child);
  }

  public void removeAllChildren() {
    ClutterLibrary.INSTANCE.clutter_actor_remove_all_children(this);
  }

  public Actor getFirstChild() {
    final Pointer handle = ClutterLibrary.INSTANCE.clutter_actor_get_first_child(this);
    // TODO
    return null;
  }

  public Actor getNextSibling() {
    final Pointer handle = ClutterLibrary.INSTANCE.clutter_actor_get_next_sibling(this);
    // TODO
    return null;
  }

  public Actor getPreviousSibling() {
    final Pointer handle = ClutterLibrary.INSTANCE.clutter_actor_get_previous_sibling(this);
    // TODO
    return null;
  }

  public Actor getLastChild() {
    final Pointer handle = ClutterLibrary.INSTANCE.clutter_actor_get_last_child(this);
    // TODO
    return null;
  }

  public Actor getChildAtIndex(int index) {
    return ClutterLibrary.INSTANCE.clutter_actor_get_child_at_index(this, index);
  }

  public int indexOf(Actor child) {
    final GList<Actor> gList = new GList<Actor>(ClutterLibrary.INSTANCE.clutter_actor_get_children(this));
    return gList.indexOf(child);
  }

  // TODO clutter_actor_get_children()
  public Iterator<Actor> children() {
    throw new UnsupportedOperationException();
  }

  public int getChildrenNum() {
    return ClutterLibrary.INSTANCE.clutter_actor_get_n_children(this);
  }

  public Actor getParent() {
    final Pointer handle = ClutterLibrary.INSTANCE.clutter_actor_get_parent(this);
    // TODO
    return null;
  }

  public void saveEastingState() {
    ClutterLibrary.INSTANCE.clutter_actor_save_easing_state(this);
  }

  public void restoreEasingState() {
    ClutterLibrary.INSTANCE.clutter_actor_restore_easing_state(this);
  }

  public void setEasingDuration(int msecs) {
    ClutterLibrary.INSTANCE.clutter_actor_set_easing_duration(this, msecs);
  }

  public int getEasingDuration() {
    return ClutterLibrary.INSTANCE.clutter_actor_get_easing_duration(this);
  }

  public void setEasingMode(int mode) {
    ClutterLibrary.INSTANCE.clutter_actor_set_easing_mode(this, mode);
  }

  public int getEasingMode() {
    return ClutterLibrary.INSTANCE.clutter_actor_get_easing_mode(this);
  }

  public void setEasingDelay(int msecs) {
    ClutterLibrary.INSTANCE.clutter_actor_set_easing_delay(this, msecs);
  }

  public int getEasingDelay() {
    return ClutterLibrary.INSTANCE.clutter_actor_get_easing_delay(this);
  }

  /** Sets actor as reactive. Reactive actors will receive events. */
  public void setReactive(boolean reactive) {
    ClutterLibrary.INSTANCE.clutter_actor_set_reactive(this, reactive);
  }

  /** Checks whether actor is marked as reactive. */
  public boolean isReactive() {
    return ClutterLibrary.INSTANCE.clutter_actor_get_reactive(this);
  }

  /** Checks whether this actor is the actor that has key focus. */
  public boolean hasKeyFocus() {
    return ClutterLibrary.INSTANCE.clutter_actor_has_key_focus(this);
  }

  public void grabKeyFocus() {
    ClutterLibrary.INSTANCE.clutter_actor_grab_key_focus(this);
  }

  public boolean hasPointer() {
    return ClutterLibrary.INSTANCE.clutter_actor_has_pointer(this);
  }

  public void addConstraing(Constraint constraint) {
    ClutterLibrary.INSTANCE.clutter_actor_add_constraint(this, constraint);
  }

  // ---

  public SignalConnection connect(DestroySignalHandler handler) {
    return super.connect(Actor.SIGNAL_DESTROY, handler);
  }

  /**
   * {@code Actor} signal handler.
   * 
   * @see Actor#signalConnect(DestroySignalHandler, Object, int)
   */
  protected static abstract class ActorSignalHandler implements SignalHandler {
    public final void onSignal(Pointer actorPtr, Pointer userData) {
      onSignal((Actor) NativeObject.instanceFor(actorPtr));
    }

    /**
     * The signal handler callback.
     * 
     * @param actor
     *          the actor which received the signal.
     */
    protected abstract void onSignal(Actor actor);
  }

  /**
   * The {@code destroy} signal handler.
   * 
   * @see Actor#SIGNAL_DESTROY
   */
  public static abstract class DestroySignalHandler extends ActorSignalHandler {
    // empty body
  }

}
