package plasma.scene;

import gobject4j.jna.ClutterLibrary;

import com.sun.jna.Pointer;

public class Stage extends Group {
  
  public static void init() {
    ClutterLibrary.INSTANCE.clutter_init(0, Pointer.NULL);
  }
  
  public static void mainStart() {
    ClutterLibrary.INSTANCE.clutter_main();
  }
  
  public static void mainQuit() {
    ClutterLibrary.INSTANCE.clutter_main_quit();
  }
  
  // ---
  
  public Stage() {
    this(ClutterLibrary.INSTANCE.clutter_stage_new());
  }

  protected Stage(Pointer ptr) {
    super(ptr);
  }
  
  

}
