package plasma.geometry;

/**
 * A 2D geometric point that represents the x, y coordinates.
 */
public class Point2D {
  
  public final float[] x = new float[1];
  public final float[] y = new float[1];
  
  public Point2D() {
    this(0F, 0F);
  }

  public Point2D(float x, float y) {
    this.x[0] = x;
    this.x[0] = y;
  }

  public float getX() {
    return x[0];
  }

  public void setX(float x) {
    this.x[0] = x;
  }

  public float getY() {
    return y[0];
  }

  public void setY(float y) {
    this.y[0] = y;
  }

  @Override
  public String toString() {
    return "Position [x=" + x[0] + ", y=" + y[0] + "]";
  }

}
