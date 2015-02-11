package chillverse.plasma.scene.text;

import chillverse.plasma.scene.Text;

/**
 * {@code Alignment} describes how to align the lines of {@link Text} within the
 * available space.
 */
public interface LineAlignment {
  /** Put all available space on the right. */
  public final int ALIGN_LEFT = 0;
  /** Center the line within the available space. */
  public final int ALIGN_CENTER = 1;
  /** Put all available space on the left. */
  public final int ALIGN_RIGHT = 2;
}
