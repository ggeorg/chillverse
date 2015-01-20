package plasma.scene.text;

/**
 * {@code EllipsizeMode} describes what sort of (if any) ellipsization should be
 * applied to a line of text. In the ellipsization process characters are
 * removed from the text in order to make it fit to a given width and replaced
 * with an ellipsis.
 */
public interface EllipsizeMode {
  /** No ellipsization. */
  public final int PANGO_ELLIPSIZE_NONE = 0;
  /** Omit characters at the start of the text. */
  public final int PANGO_ELLIPSIZE_START = 1;
  /** Omit characters in the middle of the text. */
  public final int PANGO_ELLIPSIZE_MIDDLE = 2;
  /** Omit characters at the end of the text. */
  public final int PANGO_ELLIPSIZE_END = 3;
}
