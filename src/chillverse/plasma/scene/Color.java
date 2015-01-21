package chillverse.plasma.scene;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;

public class Color extends Structure {

  public static final Color BLACK = new Color();
  public static final Color WHITE = new Color(255, 255, 255);
  public static final Color BLUE = new Color(0, 0, 255);
  public static final Color RED = new Color(255, 0, 0);
  public static final Color GREEN = new Color(0, 255, 0);
  public static final Color YELLOW = new Color(255, 255, 0);
  public static final Color CYAN = new Color(0, 255, 255);
  public static final Color MAGENTA = new Color(255, 0, 255);

  public byte red;
  public byte green;
  public byte blue;
  public byte alpha;

  public Color(byte red, byte green, byte blue, byte alpha) {
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.alpha = alpha;
  }
  
  public Color(int red, int green, int blue) {
    this((byte) red, (byte) green, (byte) blue, (byte) 255);
  }

  public Color(int red, int green, int blue, int alpha) {
    this((byte) red, (byte) green, (byte) blue, (byte) alpha);
  }

  public Color() {
    this(0, 0, 0, 255);
  }

  @SuppressWarnings("rawtypes")
  @Override
  protected List getFieldOrder() {
    return Arrays.asList(new String[] { "red", "green", "blue", "alpha" });
  }

  @Override
  public String toString() {
    return "Color [red=" + (0xff & red) + ", green=" + (0xff & green) + ", blue=" + (0xff & blue) + ", alpha=" + alpha + "]";
  }

}
