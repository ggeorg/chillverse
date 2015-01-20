package plasma.geometry;

/**
 * A dimension object that contains a width and a height.
 */
public class Dimension {

  public final float[] width = new float[1];
  public final float[] height = new float[1];

  public Dimension() {
    this(0F, 0F);
  }

  public Dimension(float width, float height) {
    this.width[0] = width;
    this.height[0] = height;
  }
  
  public float getWidth() {
    return width[0];
  }
  
  public void setWidth(float width) {
    this.width[0] = width;
  }
  
  public float getHeight() {
    return height[0];
  }
  
  public void setHeight(float height) {
    this.height[0] = height;
  }

  @Override
  public String toString() {
    return "Dimension [width=" + width[0] + ", height=" + height[0] + "]";
  }
}
