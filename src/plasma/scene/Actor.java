package plasma.scene;

import gobject4j.GObject;
import gobject4j.NativeObject;
import gobject4j.jna.ClutterLibrary;

import java.util.Iterator;

import plasma.geometry.Dimension;
import plasma.geometry.Point2D;
import plasma.signal.SignalConnection;
import plasma.signal.SignalHandler;

import com.sun.jna.Pointer;

public class Actor extends GObject {

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

  public void setHorizontalAlign() {

  }

  public void setVerticalAlign() {

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
    ClutterLibrary.INSTANCE.clutter_actor_add_child(this, child.getPtr());
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
    ClutterLibrary.INSTANCE.clutter_actor_remove_child(this, child.getPtr());
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
    final Pointer handle = ClutterLibrary.INSTANCE.clutter_actor_get_child_at_index(this, index);
    // TODO
    return null;
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
      final Actor actor = NativeObject.instanceFor(actorPtr);
      onSignal(actor);
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
