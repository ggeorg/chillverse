package chillverse.plasma.scene.text;

public interface WrapMode {
  /**
   * Wrap lines at word boundaries.
   */
  public final int WRAP_WORD = 0;

  /**
   * Wrap lines at character boundaries.
   */
  public final int WRAP_CHAR = 1;

  /**
   * Wrap lines at word boundaries, but fall back to character boundaries if
   * there is not enough space for a full word.
   */
  public final int WRAP_WORD_CHAR = 2;
}
