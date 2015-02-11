package chillverse.plasma.scene;

import org.apache.pivot.beans.DefaultProperty;

import chillverse.plasma.scene.constraint.AlignConstraint;

@DefaultProperty("content")
public class Window {

  private Stage stage;
  private float width, height;
  private Actor content;
  private String title;

  public void show(Stage stage) {
    this.stage = stage;
    this.stage.setAcceptFocus(true);
    this.stage.setUserResizable(true);
    this.stage.setTitle(title);
    this.stage.setSize(width, height);
    this.stage.addChild(content);
    this.content.addConstraing(new AlignConstraint(stage, AlignConstraint.CLUTTER_ALIGN_BOTH, 0.5F));
    this.stage.show();
  }

  public float getWidth() {
    return width;
  }

  public void setWidth(float width) {
    this.width = width;
  }

  public float getHeight() {
    return height;
  }

  public void setHeight(float height) {
    this.height = height;
  }

  public Actor getContent() {
    return content;
  }

  public void setContent(Actor content) {
    this.content = content;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

}
