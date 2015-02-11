package chillverse.plasma.scene;

import chillverse.jna.ClutterGstLibrary;
import chillverse.plasma.application.ApplicationContext;

import com.sun.jna.Pointer;

/** Actor for playback of video files. */
public class Video extends Actor {

  public Video() {
    this(ClutterGstLibrary.INSTANCE.clutter_gst_video_texture_new());
  }

  protected Video(Pointer ptr) {
    super(ptr);
  }
  
  public void setFilename(String filename) {
    ClutterGstLibrary.INSTANCE.clutter_media_set_filename(this, filename);
    
    ApplicationContext.scheduleCallback(new Runnable(){
      @Override
      public void run() {
        play();
      }}, 3000L);
  }
  
  public void play() {
    ClutterGstLibrary.INSTANCE.clutter_media_set_playing(this, 1);
  }
  
}
