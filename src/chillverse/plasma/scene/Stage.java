package chillverse.plasma.scene;

import chillverse.jna.ClutterLibrary;
import chillverse.jna.cairo.CairoRectangle;
import chillverse.jna.clutter.ClutterPerspective;
import chillverse.plasma.geometry.Dimension;

import com.sun.jna.Pointer;

/** Top level visual element to which actors are placed and manipulated. */
public class Stage extends Container {

  public static final int PICK_NONE = 0;
  public static final int PICK_REACTIVE = 1;
  public static final int PICK_ALL = 2;

  @Deprecated
  public static void init() {
    ClutterLibrary.INSTANCE.clutter_init(0, Pointer.NULL);
  }

  @Deprecated
  public static void mainStart() {
    ClutterLibrary.INSTANCE.clutter_main();
  }

  @Deprecated
  public static void mainQuit() {
    ClutterLibrary.INSTANCE.clutter_main_quit();
  }

  // ---

  public Stage() {
    this(ClutterLibrary.INSTANCE.clutter_stage_new());
  }

  protected Stage(Pointer ptr) {
    super(ptr);
  }

  public void setKeyFocus(Actor actor) {
    ClutterLibrary.INSTANCE.clutter_stage_set_key_focus(this, actor);
  }

  public Actor getKeyFocus() {
    return ClutterLibrary.INSTANCE.clutter_stage_get_key_focus(this);
  }

  // TODO clutter_stage_read_pixels ()

  public void setUseAlpha(boolean useAlpha) {
    ClutterLibrary.INSTANCE.clutter_stage_set_use_alpha(this, useAlpha);
  }

  public boolean isUseAlpha() {
    return ClutterLibrary.INSTANCE.clutter_stage_get_use_alpha(this);
  }

  public void setMinimumSize(int width, int height) {
    ClutterLibrary.INSTANCE.clutter_stage_set_minimum_size(this, width, height);
  }

  public Dimension getMinimumSize() {
    final Dimension d = new Dimension();
    ClutterLibrary.INSTANCE.clutter_stage_get_minimum_size(this, d);
    return d;
  }

  public void setNoClearHint(boolean noClear) {
    ClutterLibrary.INSTANCE.clutter_stage_set_no_clear_hint(this, noClear);
  }

  public boolean isNoClearHint() {
    return ClutterLibrary.INSTANCE.clutter_stage_get_no_clear_hint(this);
  }

  public CairoRectangle getRedrawClipBounds() {
    final CairoRectangle clip = new CairoRectangle();
    ClutterLibrary.INSTANCE.clutter_stage_get_redraw_clip_bounds(this, clip);
    return clip;
  }

  public void setMotionEventsEnabled(boolean enabled) {
    ClutterLibrary.INSTANCE.clutter_stage_set_motion_events_enabled(this, enabled);
  }

  public boolean isMotionEventsEnabled() {
    return ClutterLibrary.INSTANCE.clutter_stage_get_motion_events_enabled(this);
  }

  public void setPerspective(ClutterPerspective perspective) {
    ClutterLibrary.INSTANCE.clutter_stage_set_perspective(this, perspective);
  }

  public ClutterPerspective getPerspective() {
    final ClutterPerspective perspective = new ClutterPerspective();
    ClutterLibrary.INSTANCE.clutter_stage_get_perspective(this, perspective);
    return perspective;
  }

  public void setTitle(String title) {
    ClutterLibrary.INSTANCE.clutter_stage_set_title(this, title);
  }

  public String getTitle() {
    return ClutterLibrary.INSTANCE.clutter_stage_get_title(this);
  }

  public void setUserResizable(boolean resizable) {
    ClutterLibrary.INSTANCE.clutter_stage_set_user_resizable(this, resizable);
  }

  public boolean isUserResizable() {
    return ClutterLibrary.INSTANCE.clutter_stage_get_user_resizable(this);
  }

  public void setFullscreen(boolean fullscreen) {
    ClutterLibrary.INSTANCE.clutter_stage_set_fullscreen(this, fullscreen);
  }

  public boolean isFullscreen() {
    return ClutterLibrary.INSTANCE.clutter_stage_get_fullscreen(this);
  }

  public void showCursor() {
    ClutterLibrary.INSTANCE.clutter_stage_show_cursor(this);
  }

  public void hideCursor() {
    ClutterLibrary.INSTANCE.clutter_stage_hide_cursor(this);
  }

  public void setAcceptFocus(boolean acceptFocus) {
    ClutterLibrary.INSTANCE.clutter_stage_set_accept_focus(this, acceptFocus);
  }

  public boolean isAcceptFocus() {
    return ClutterLibrary.INSTANCE.clutter_stage_get_accept_focus(this);
  }

  public void setSyncDelay(int syncDelay) {
    ClutterLibrary.INSTANCE.clutter_stage_set_sync_delay(this, syncDelay);
  }

  public void skipSyncDelay() {
    ClutterLibrary.INSTANCE.clutter_stage_skip_sync_delay(this);
  }

}
