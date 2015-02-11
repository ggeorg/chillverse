package chillverse.jna.clutter;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;

/** Stage perspective. */
public class ClutterPerspective extends Structure {

  /** The field of view angle, in degrees, in the y direction. */
  public float fovy;

  /** The aspect ratio that determines the field of view in the x direction. */
  public float aspect;

  /** The distance from the viewer to the near clipping plane. */
  public float z_near;

  /** The distance from the viewer to the far clipping plane. */
  public float z_far;

  @SuppressWarnings("rawtypes")
  @Override
  protected List getFieldOrder() {
    return Arrays.asList(new String[] { "fovy", "aspect", "z_near", "z_far" });
  }

}
