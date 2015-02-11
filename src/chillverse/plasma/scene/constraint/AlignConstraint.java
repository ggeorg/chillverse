package chillverse.plasma.scene.constraint;

import chillverse.jna.ClutterLibrary;
import chillverse.plasma.scene.Actor;

import com.sun.jna.Pointer;

/**
 * {@code AlignConstraint} is a {@link Constraint} that aligns the position of
 * the {@link Actor} to which it is applied to the size of another {@link Actor}
 * . using an alignment factor.
 */
public class AlignConstraint extends Constraint {

  /** Maintain the alignment on the X axis. */
  public static final int CLUTTER_ALIGN_X_AXIS = 0;

  /** Maintain the alignment on the Y axis. */
  public static final int CLUTTER_ALIGN_Y_AXIS = 1;

  /** Maintain the alignment on both the X and Y axis. */
  public static final int CLUTTER_ALIGN_BOTH = 2;

  public AlignConstraint(Actor source, int axis, float factor) {
    this(ClutterLibrary.INSTANCE.clutter_align_constraint_new(source, axis, factor));
  }

  protected AlignConstraint(Pointer ptr) {
    super(ptr);
  }

}
