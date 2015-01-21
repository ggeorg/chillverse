package chillverse.jna;


import java.util.HashMap;


import chillverse.jna.mapper.GTypeMapper;
import chillverse.plasma.scene.Actor;
import chillverse.plasma.scene.Color;
import chillverse.plasma.scene.text.Text;

import com.sun.jna.Library;
import com.sun.jna.Pointer;

public interface ClutterLibrary extends Library {

  String NATIVE_LIBRARY_NAME = "clutter-1.0";

  @SuppressWarnings("serial")
  static ClutterLibrary INSTANCE = ChillversePlatform.loadLibrary(NATIVE_LIBRARY_NAME, ClutterLibrary.class, new HashMap<String, Object>() {
    {
      put(Library.OPTION_TYPE_MAPPER, new GTypeMapper());
    }
  });

//@formatter:off
  void clutter_init(int argc, Pointer argv);
  void clutter_main();
  void clutter_main_quit();
//@formatter:on
  
//@formatter:off
  Pointer clutter_actor_new();
  void clutter_actor_set_name(Actor self, String name);
  String clutter_actor_get_name(Actor self);
  void clutter_actor_show(Actor self);
  void clutter_actor_hide(Actor self);
  void clutter_actor_queue_redraw(Actor self);
  // TODO void clutter_actor_queue_redraw_with_clip(Actor self, )
  void clutter_actor_set_background_color(Actor self, Color c);
  void clutter_actor_get_background_color(Actor self, Color c);
  void clutter_actor_set_size(Actor self, float width, float height);
  void clutter_actor_get_size(Actor self, float[] width, float[] height);
  void clutter_actor_set_position(Actor self, float x, float y);
  void clutter_actor_get_position(Actor self, float[] x, float[] y);
  void clutter_actor_set_width(Actor self, float width);
  float clutter_actor_get_width(Actor self);
  void clutter_actor_set_height(Actor self, float height);
  float clutter_actor_get_height(Actor self);
  void clutter_actor_set_x(Actor self, float x);
  float clutter_actor_get_x(Actor self);
  void clutter_actor_set_y(Actor self, float y);
  float clutter_actor_get_y(Actor self);
  void clutter_actor_move_by(Actor self, double dx, double dy);
  void clutter_actor_set_z_position(Actor self, float z);
  float clutter_actor_get_z_position(Actor self);
  void clutter_actor_set_pivot_point(Actor self, float x, float y);
  void clutter_actor_get_pivot_point(Actor self, float[] x, float[] y);
  void clutter_actor_set_pivot_point_z(Actor self, float z);
  float clutter_actor_get_pivot_point_z(Actor self);
  
  void clutter_actor_add_child(Actor self, Pointer child);
  void clutter_actor_insert_child_above(Actor self, Pointer child, Pointer sibling);
  void clutter_actor_insert_child_at_index(Actor self, Pointer child, int index);
  void clutter_actor_insert_child_below(Actor self, Pointer child, Pointer sibling);
  void clutter_actor_replace_child(Actor self, Pointer oldChild, Pointer newChild);
  void clutter_actor_remove_child(Actor self, Pointer child);
  void clutter_actor_remove_all_children(Actor self);
  Pointer clutter_actor_get_first_child(Actor self);
  Pointer clutter_actor_get_next_sibling(Actor self);
  Pointer clutter_actor_get_previous_sibling(Actor self);
  Pointer clutter_actor_get_last_child(Actor self);
  Pointer clutter_actor_get_child_at_index(Actor self, int index);
  Pointer clutter_actor_get_children(Actor self);
  int clutter_actor_get_n_children(Actor self);
  Pointer clutter_actor_get_parent(Actor self);
  void clutter_actor_set_child_above_sibling(Actor self, Pointer child, Pointer sibling);
  void clutter_actor_set_child_at_index(Actor self, Pointer child, int index);
  void clutter_actor_set_child_below_sibling(Actor self, Pointer child, Pointer sibling);
  boolean clutter_actor_contains(Actor self, Pointer descendant);
  Pointer clutter_actor_get_stage(Actor self);
  // TODO clutter_actor_iter_init()
  // TODO clutter_actor_iter_is_valid ()
  // TODO clutter_actor_iter_next ()
  // TODO clutter_actor_iter_prev ()
  // TODO clutter_actor_iter_remove ()
  // TODO clutter_actor_iter_destroy ()
  void clutter_actor_save_easing_state(Actor self);
  void clutter_actor_restore_easing_state(Actor self);
  void clutter_actor_set_easing_duration(Actor self, int msecs);
  int clutter_actor_get_easing_duration(Actor self);
  void clutter_actor_set_easing_mode(Actor self, int mode);
  int clutter_actor_get_easing_mode(Actor self);
  void clutter_actor_set_easing_delay(Actor self, int msecs);
  int clutter_actor_get_easing_delay(Actor self);
//@formatter:on

  /* Base actors */
  
  Pointer clutter_stage_new();
  
//@formatter:off
  Pointer clutter_text_new();
  void clutter_text_set_text(Text self, String text);
  void clutter_text_set_markup(Text self, String markup);
  String clutter_text_get_text(Text self);
  void clutter_text_set_activatable(Text self, boolean activatable);
  boolean clutter_text_get_activatable(Text self);
  void clutter_text_set_color(Text self, Color c);
  void clutter_text_get_color(Text self, Color c);
  void clutter_text_set_ellipsize(Text self, int mode);
  int clutter_text_get_ellipsize(Text self);
  void clutter_text_set_font_name(Text self, String font_name);
  String clutter_text_get_font_name(Text self);
  void clutter_text_set_password_char(Text self, char wc);
  char clutter_text_get_password_char(Text self);
  void clutter_text_set_justify(Text self, boolean justify);
  boolean clutter_text_get_justify(Text self);
  void clutter_text_set_line_alignment(Text self, int alignment);
  int clutter_text_get_line_alignment(Text self);
  void clutter_text_set_line_wrap(Text self, boolean lineWrap);
  boolean clutter_text_get_line_wrap(Text self);
  void clutter_text_set_line_wrap_mode(Text self, int wrapMode);
  int clutter_text_get_line_wrap_mode(Text self);
  void clutter_text_set_max_length(Text self, int max);
  int clutter_text_get_max_length(Text self);
  void clutter_text_set_selectable(Text self, boolean selectable);
  boolean clutter_text_get_selectable(Text self);
  void clutter_text_set_selection(Text self, int startPos, int endPos);
  String clutter_text_get_selection(Text self);
  void clutter_text_set_selection_bound(Text self, int selectionBound);
  int clutter_text_get_selection_bound(Text self);
  void clutter_text_set_single_line_mode(Text self, boolean singleLine);
  boolean clutter_text_get_single_line_mode(Text self);
  void clutter_text_set_use_markup(Text self, boolean setting);
  boolean clutter_text_get_use_markup(Text self);
  void clutter_text_set_editable(Text self, boolean editable);
  boolean clutter_text_get_editable(Text self);
  void clutter_text_insert_text(Text self, String text, int position);
  void clutter_text_insert_unichar(Text self, char wc);
  void clutter_text_delete_chars(Text self, int nChars);
  void clutter_text_delete_text(Text self, int startPos, int endPos);
  void clutter_text_delete_selection(Text self);
  String clutter_text_get_chars(Text self, int startPos, int endPos);
  void clutter_text_set_cursor_color(Text self, Color c);
  void clutter_text_get_cursor_color(Text self, Color c);
  void clutter_text_set_selection_color(Text self, Color c);
  void clutter_text_get_selection_color(Text self, Color c);
  void clutter_text_set_selected_text_color(Text self, Color c);
  void clutter_text_get_selected_text_color(Text self, Color c);
  void clutter_text_set_cursor_position(Text text, int position);
  int clutter_text_get_cursor_position(Text text);
  void clutter_text_set_cursor_visible(Text text, int cursorVisible);
  boolean clutter_text_get_cursor_visible(Text text);
  void clutter_text_set_cursor_size(Text text, int size);
  int clutter_text_get_cursor_size(Text text);
  int clutter_text_coords_to_position(Text text, float x, float y);
//@formatter:on
  
}
