package chillverse.plasma.scene;


import org.apache.pivot.beans.DefaultProperty;

import chillverse.jna.ClutterLibrary;
import chillverse.plasma.scene.text.LineAlignment;
import chillverse.plasma.scene.text.EllipsizeMode;
import chillverse.plasma.scene.text.WrapMode;

import com.sun.jna.Pointer;

/**
 * An actor for displaying and editing text using Pango as the text rendering
 * engine..
 */
@DefaultProperty("text")
public class Text extends Actor implements EllipsizeMode, LineAlignment, WrapMode {

  public Text() {
    this(ClutterLibrary.INSTANCE.clutter_text_new());
  }

  protected Text(Pointer ptr) {
    super(ptr);

    setLineWrap(true);
    setLineWrapMode(WRAP_WORD);
    this.setLineAlignment(ALIGN_RIGHT);
  }
  
  public void setText(String text) {
    //ClutterLibrary.INSTANCE.clutter_text_set_text(this, text);
    setMarkup(text);
  }
  
  public void setMarkup(String text) {
    ClutterLibrary.INSTANCE.clutter_text_set_markup(this, text);
  }
  
  public String getText() {
    return ClutterLibrary.INSTANCE.clutter_text_get_text(this);
  }
  
  public void setActivatable(boolean activatable) {
    ClutterLibrary.INSTANCE.clutter_text_set_activatable(this, activatable);
  }
  
  public boolean isActivatable() {
    return ClutterLibrary.INSTANCE.clutter_text_get_activatable(this);
  }
  
  // TODO PangoAttrList
  
  public void setColor(String c) {
    setColor(Color.decodeColor(c));
  }
  
  public void setColor(Color c) {
    ClutterLibrary.INSTANCE.clutter_text_set_color(this, c);
  }
  
  public Color getColor() {
    final Color c = new Color();
    ClutterLibrary.INSTANCE.clutter_text_get_color(this, c);
    return c;
  }
  
  public void setEllipsizeMode(int mode) {
    ClutterLibrary.INSTANCE.clutter_text_set_ellipsize(this, mode);
  }
  
  public int getEllipsizeMode() {
    return ClutterLibrary.INSTANCE.clutter_text_get_ellipsize(this);
  }
  
  public void setFontName(String font_name) {
    ClutterLibrary.INSTANCE.clutter_text_set_font_name(this, font_name);
  }
  
  public String getFontName() {
    return ClutterLibrary.INSTANCE.clutter_text_get_font_name(this);
  }
  
  // TODO PangoFontDescription
  
  public void setPasswordChar(char wc) {
    ClutterLibrary.INSTANCE.clutter_text_set_password_char(this, wc);
  }
  
  public char getPasswordChar() {
    return ClutterLibrary.INSTANCE.clutter_text_get_password_char(this);
  }
  
  public void setJustify(boolean justify) {
    ClutterLibrary.INSTANCE.clutter_text_set_justify(this, justify);
  }
  
  public boolean isJustify() {
    return ClutterLibrary.INSTANCE.clutter_text_get_justify(this);
  }
  
  // TODO PangoLayout
  
  public void setLineAlignment(int alignment) {
    ClutterLibrary.INSTANCE.clutter_text_set_line_alignment(this, alignment);
  }
  
  public int getLineAlignment(int alignment) {
    return ClutterLibrary.INSTANCE.clutter_text_get_line_alignment(this);
  }
  
  public void setLineWrap(boolean lineWrap) {
    ClutterLibrary.INSTANCE.clutter_text_set_line_wrap(this, lineWrap);
  }
  
  public boolean isLineWrap() {
    return ClutterLibrary.INSTANCE.clutter_text_get_line_wrap(this);
  }
  
  public void setLineWrapMode(int wrapMode) {
    ClutterLibrary.INSTANCE.clutter_text_set_line_wrap_mode(this, wrapMode);
  }
  
  public int getLineWrapMode() {
    return ClutterLibrary.INSTANCE.clutter_text_get_line_wrap_mode(this);
  }
  
  public void setMaxLength(int max) {
    ClutterLibrary.INSTANCE.clutter_text_set_max_length(this, max);
  }
  
  public int getMaxLength() {
    return ClutterLibrary.INSTANCE.clutter_text_get_max_length(this);
  }
  
  public void setSelectable(boolean selectable) {
    ClutterLibrary.INSTANCE.clutter_text_set_selectable(this, selectable);
  }
  
  public boolean isSelectable() {
    return ClutterLibrary.INSTANCE.clutter_text_get_selectable(this);
  }
  
  public void setSelection(int startPos, int endPos) {
    ClutterLibrary.INSTANCE.clutter_text_set_selection(this, startPos, endPos);
  }
  
  public String getSelection() {
    return ClutterLibrary.INSTANCE.clutter_text_get_selection(this);
  }
  
  public void setSelectionBound(int selectionBound) {
    ClutterLibrary.INSTANCE.clutter_text_set_selection_bound(this, selectionBound);
  }
  
  public int getSelectionBound() {
    return ClutterLibrary.INSTANCE.clutter_text_get_selection_bound(this);
  }
  
  public void setSignleLineMode(boolean singleLine) {
    ClutterLibrary.INSTANCE.clutter_text_set_single_line_mode(this, singleLine);
  }
  
  public boolean isSignleLineMode() {
    return ClutterLibrary.INSTANCE.clutter_text_get_single_line_mode(this);
  }
  
  public void setUseMarkup(boolean setting) {
    ClutterLibrary.INSTANCE.clutter_text_set_use_markup(this, setting);
  }
  
  public boolean isUseMarkup() {
    return ClutterLibrary.INSTANCE.clutter_text_get_use_markup(this);
  }
  
  public void setEditable(boolean editable) {
    ClutterLibrary.INSTANCE.clutter_text_set_editable(this, editable);
  }
  
  public boolean isEditable() {
    return ClutterLibrary.INSTANCE.clutter_text_get_editable(this);
  }
  
  public void insertText(String text, int position) {
    ClutterLibrary.INSTANCE.clutter_text_insert_text(this, text, position);
  }
  
  public void insertChar(char wc) {
    ClutterLibrary.INSTANCE.clutter_text_insert_unichar(this, wc);
  }
  
  public void deleteChars(int nChars) {
    ClutterLibrary.INSTANCE.clutter_text_delete_chars(this, nChars);
  }
  
  public void deleteText(int startPos, int endPos) {
    ClutterLibrary.INSTANCE.clutter_text_delete_text(this, startPos, endPos);
  }
  
  public void deleteSelection() {
    ClutterLibrary.INSTANCE.clutter_text_delete_selection(this);
  }
  
  public String getChars(int startPos, int endPos) {
    return ClutterLibrary.INSTANCE.clutter_text_get_chars(this, startPos, endPos);
  }
  
  public void setCursorColor(Color c) {
    ClutterLibrary.INSTANCE.clutter_text_set_cursor_color(this, c);
  }
  
  public Color getCursorColor() {
    final Color c = new Color();
    ClutterLibrary.INSTANCE.clutter_text_get_cursor_color(this, c);
    return c;
  }
  
  public void setSelectionColor(Color c) {
    ClutterLibrary.INSTANCE.clutter_text_set_selection_color(this, c);
  }
  
  public Color getSelectionColor() {
    final Color c = new Color();
    ClutterLibrary.INSTANCE.clutter_text_get_selection_color(this, c);
    return c;
  }
  
  public void setSelectedTextColor(Color c) {
    ClutterLibrary.INSTANCE.clutter_text_set_selected_text_color(this, c);
  }
  
  public Color getSelectedTextColor() {
    final Color c = new Color();
    ClutterLibrary.INSTANCE.clutter_text_get_selected_text_color(this, c);
    return c;
  }
  
  public void setCursorPosition(int position) {
    ClutterLibrary.INSTANCE.clutter_text_set_cursor_position(this, position);
  }
  
  public int getCursorPosition() {
    return ClutterLibrary.INSTANCE.clutter_text_get_cursor_position(this);
  }
  
  public void setCursorVisible(int cursorVisible) {
    ClutterLibrary.INSTANCE.clutter_text_set_cursor_visible(this, cursorVisible);
  }
  
  public boolean isCursorVisible() {
    return ClutterLibrary.INSTANCE.clutter_text_get_cursor_visible(this);
  }
  
  public void setCursorSize(int size) {
    ClutterLibrary.INSTANCE.clutter_text_set_cursor_size(this, size);
  }
  
  public int getCursorSize() {
    return ClutterLibrary.INSTANCE.clutter_text_get_cursor_size(this);
  }
  
  // TODO cursor_rect
  
  // TODO activate
  
  public int coordsToPosition(float x, float y) {
    return ClutterLibrary.INSTANCE.clutter_text_coords_to_position(this, x, y);
  }
  
//  public TODO positionToCoords(int position) {
//    
//  }

}
