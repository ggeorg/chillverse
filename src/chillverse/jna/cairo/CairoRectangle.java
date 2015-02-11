package chillverse.jna.cairo;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;

/** A data structure for holding a rectangle with integer coordinates. */
public class CairoRectangle extends Structure {
  public int x, y;
  public int width, height;

  @SuppressWarnings("rawtypes")
  @Override
  protected List getFieldOrder() {
    return Arrays.asList(new String[] { "x", "y", "width", "height" });
  }
}
