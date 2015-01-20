package plasma.test;

import plasma.scene.Actor;
import plasma.scene.Actor.DestroySignalHandler;
import plasma.scene.Color;
import plasma.scene.Stage;
import plasma.scene.text.Text;

public class Test02 {
  public static void main(String[] args) {
    Stage.init();

    Actor a1 = new Actor();
    a1.setSize(256, 256);
    a1.setBackgroundColor(Color.RED);
    a1.setPosition(10, 10);
    
    Text txt1 = new Text();
    txt1.setMarkup("<b>Hello</b><i>, World!</i>");
    txt1.setColor(new Color(255, 255, 255, 128));
    txt1.setPosition(50, 50);
    txt1.setFontName("Arial 24px");
//    txt1.setSelectable(true);
//    txt1.setEditable(true);
    
    Stage stage = new Stage();
    stage.setSize(720, 512);
    stage.setBackgroundColor(Color.BLUE);
    stage.addChild(a1);
    stage.addChild(txt1);
    stage.show();
    
    
    
    stage.connect(new DestroySignalHandler() {
      @Override
      protected void onSignal(Actor actor) {
        System.out.println("==========");
        Stage.mainQuit();
      }
    });

    Stage.mainStart();
  }
}
