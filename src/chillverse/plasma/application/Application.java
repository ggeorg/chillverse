package chillverse.plasma.application;

import java.util.Map;

import chillverse.plasma.scene.Stage;

/**
 * Represents the entry point of an application.
 * <p>
 * These methods are called by the application context. In general, they should
 * not be invoked directly by the application.
 */
public interface Application {

  /**
   * Application adapter.
   */
  public static class Adapter implements Application {

    public void init(Map<String, String> properties) {
      // TODO Auto-generated method stub
      
    }

    public void startup(Stage primaryStage) throws Exception {
      // TODO Auto-generated method stub
      
    }

    public boolean shutdown(boolean optional) throws Exception {
      // TODO Auto-generated method stub
      return false;
    }

    public void suspended() throws Exception {
      // TODO Auto-generated method stub
      
    }

    public void resume() throws Exception {
      // TODO Auto-generated method stub
      
    }

  }

  /**
   * Optional interface that allows an application to handle uncaught exceptions
   * thrown during a user input event.
   */
  public interface UncaughtExceptionHandler {
    public void uncaughtExceptionThrown(Exception exception);
  }

  /**
   * 
   * @param properties
   *          Initialization properties passed to the application.
   */
  void init(Map<String, String> properties);

  /**
   * Called when the application is starting up.
   * 
   * @param primaryStage
   *          The {@link Stage} on which this application was started.
   */
  void startup(Stage primaryStage) throws Exception;

  /**
   * Called when the application is being shut down.
   * 
   * @param optional
   *          Id {@code true}, the shutdown may be cancelled by returning a
   *          value of {@code true}.
   * @return {@code true} to cancel shutdown, {@code false} to continue.
   */
  boolean shutdown(boolean optional) throws Exception;

  /**
   * Called to notify the application that it is being suspended.
   */
  void suspended() throws Exception;

  /**
   * Called when a suspended application has been resumed.
   */
  void resume() throws Exception;

}
