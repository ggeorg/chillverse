package plasma.animation;

public interface AnimationMode {

  public final int CUSTOM_MODE = 0;

  /* linear */
  public final int LINEAR = 1;

  /* quadratic */
  public final int EASE_IN_QUAD = 2;
  public final int EASE_OUT_QUAD = 3;
  public final int EASE_IN_OUT_QUAD = 4;

  /* cubic */
  public final int EASE_IN_CUBIC = 5;
  public final int EASE_OUT_CUBIC = 6;
  public final int EASE_IN_OUT_CUBIC = 7;

  /* quartic */
  public final int EASE_IN_QUART = 8;
  public final int EASE_OUT_QUART = 9;
  public final int EASE_IN_OUT_QUART = 10;

  /* quintic */
  public final int EASE_IN_QUINT = 11;
  public final int EASE_OUT_QUINT = 12;
  public final int EASE_IN_OUT_QUINT = 13;

  /* sinusoidal */
  public final int EASE_IN_SINE = 14;
  public final int EASE_OUT_SINE = 15;
  public final int EASE_IN_OUT_SINE = 16;

  /* exponential */
  public final int EASE_IN_EXPO = 17;
  public final int EASE_OUT_EXPO = 18;
  public final int EASE_IN_OUT_EXPO = 19;

  /* circular */
  public final int EASE_IN_CIRC = 20;
  public final int EASE_OUT_CIRC = 21;
  public final int EASE_IN_OUT_CIRC = 22;

  /* elastic */
  public final int EASE_IN_ELASTIC = 23;
  public final int EASE_OUT_ELASTIC = 24;
  public final int EASE_IN_OUT_ELASTIC = 25;

  /* overshooting cubic */
  public final int EASE_IN_BACK = 26;
  public final int EASE_OUT_BACK = 27;
  public final int EASE_IN_OUT_BACK = 28;

  /* exponentially decaying parabolic */
  public final int EASE_IN_BOUNCE = 29;
  public final int EASE_OUT_BOUNCE = 30;
  public final int EASE_IN_OUT_BOUNCE = 31;

  /* step functions (see css3-transitions) */
  public final int STEPS = 32;
  public final int STEP_START = 33; /* steps(1, start) */
  public final int STEP_END = 34; /* steps(1, end) */

  /* cubic bezier (see css3-transitions) */
  public final int CUBIC_BEZIER = 35;
  public final int EASE = 36;
  public final int EASE_IN = 37;
  public final int EASE_OUT = 38;
  public final int EASE_IN_OUT = 39;

  /* guard, before registered alpha functions */
  public final int ANIMATION_LAST = 40;

}
