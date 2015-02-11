package chillverse.plasma.application;

import java.util.HashMap;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import chillverse.jna.ClutterGstLibrary;
import chillverse.jna.ClutterLibrary;
import chillverse.plasma.scene.Actor;
import chillverse.plasma.scene.Actor.DestroySignalHandler;
import chillverse.plasma.scene.Stage;
import chillverse.util.Log;

import com.sun.jna.Pointer;

/**
 * Application context used to execute applications in a native frame window.
 */
public class DesktopApplicationContext extends ApplicationContext {
  private static final Logger LOGGER = Logger.getLogger(DesktopApplicationContext.class.getName());

  static {
    
    // Initialize the Clutter library.
    ClutterLibrary.INSTANCE.clutter_init(0, Pointer.NULL);

    // Initialize the Clutter-Gst library.
    ClutterGstLibrary.INSTANCE.clutter_gst_init(0, Pointer.NULL);

  }

  private static String applicationClassName = null;
  private static HashMap<String, String> properties = null;

  private static Application application = null;

  private static Stage primaryStage = null;

  public static final String DEFAULT_HOST_WINDOW_TITLE = "Chilverse";

  private static final String INVALID_PROPERTY_FORMAT_MESSAGE = "\"%s\" is not a valid startup "
      + "property (expected format is \"--name=value\").";
  private static final String INVALID_PROPERTY_VALUE_MESSAGE = "\"%s\" is not a valid value for "
      + "startup property \"%s\".";

  /**
   * Primary application entry point.
   * 
   * @param args
   */
  public static void main(String[] args) {
    // Get the application class name
    if (args.length == 0) {
      System.err.println("Application class name is required.");
      return;
    }

    applicationClassName = args[0];

    // Get the startup properties
    properties = new HashMap<String, String>();

    try {
      Preferences preferences = Preferences.userNodeForPackage(DesktopApplicationContext.class);
      preferences = preferences.node(applicationClassName);

      // TODO

    } catch (SecurityException e) {
      System.err.println("Unable to retrieve startup preferences: " + e);
    }

    for (int i = 1, n = args.length; i < n; i++) {
      String arg = args[i];

      if (arg.startsWith("--")) {
        arg = arg.substring(2);
        String[] property = arg.split("=");

        if (property.length == 2) {
          String key = property[0];
          String value = property[1];

          try {
            // TODO default args
            properties.put(key, value);
          } catch (Exception exception) {
            System.err.println(String.format(INVALID_PROPERTY_VALUE_MESSAGE, value, key));
          }

        } else {
          System.err.println(String.format(INVALID_PROPERTY_FORMAT_MESSAGE, arg));
        }
      } else {
        System.err.println(String.format(INVALID_PROPERTY_FORMAT_MESSAGE, arg));
      }
    }

    // Start the timer
    createTimer();

    try {
      // Create primary stage.
      primaryStage = new Stage();

      // Connect to destroy signal
      primaryStage.connect(new DestroySignalHandler() {
        @Override
        protected void onSignal(Actor actor) {
          try {
            exit(true);
          } finally {
            ClutterLibrary.INSTANCE.clutter_main_quit();
          }
        }
      });

      // Add KeyboardListener
      // TODO

      // Load the application
      try {
        Class<?> applicationClass = Class.forName(applicationClassName);
        application = (Application) applicationClass.newInstance();
      } catch (ClassNotFoundException exception) {
        exception.printStackTrace();
      } catch (InstantiationException exception) {
        exception.printStackTrace();
      } catch (IllegalAccessException exception) {
        exception.printStackTrace();
      }

      if (application != null) {
        // Add the application to the application list
        applications.add(application);

        // Initialize OS-specific extensions
        initializeOSExtensions();

        //
        // Call the application init method (on launcher thread).
        //

        final Thread initThread = new Thread() {
          @Override
          public void run() {
            try {
              application.init(properties);
            } catch (Throwable t) {
              Log.e(LOGGER, "Exception in Application init method");
            }
          }
        };

        initThread.start();

        try {
          initThread.join();
        } catch (InterruptedException ex) {
          throw new RuntimeException("Unexpected exception: ", ex);
        }

        //
        // Call the application start method on UI thread.
        //

        try {
          application.startup(primaryStage);
        } catch (Throwable t) {
          Log.e(LOGGER, "Exception in Application start method");
          throw new RuntimeException(t);
        }

        //
        // Add shutdown hook & start Clutter main loop.
        //

        ClutterLibrary.INSTANCE.clutter_main();
      }
      
    } finally {
      // Stop the timer.
      destroyTimer();
    }
  }

  private static void initializeOSExtensions() {
    @SuppressWarnings("unused")
    String osName = System.getProperty("os.name");

    // TODO
  }

  public static boolean exit(boolean optional) {
    boolean cancelShutdown = false;

    if (application != null) {
      try {
        cancelShutdown = application.shutdown(optional);
      } catch (Exception e) {
        handleUncaughtException(e);
      }

      if (!cancelShutdown) {
        // Remove the application from the application list
        applications.remove(application);
      }
    }

    if (!cancelShutdown) {

      try {
        Preferences preferences = Preferences.userNodeForPackage(DesktopApplicationContext.class);
        preferences = preferences.node(applicationClassName);

        // TODO

        preferences.flush();
      } catch (SecurityException exception) {
        // No-op
      } catch (BackingStoreException e) {
        // No-op
      }

      primaryStage = null;
    }

    return cancelShutdown;
  }

  /**
   * Utility method to make it easier to define <tt>main()</tt> entry-points
   * into applications. For example:
   * 
   * <code>
   * public class MyApp implements Application {
   *   public static void main(String[] args) throws Exception {
   *     DesktopApplicationContext.main(MyApp.class, args);
   *   }
   * }
   * </code>
   * 
   * @param applicationClass
   * @param applicationArgs
   */
  public static final void main(Class<? extends Application> applicationClass, String[] applicationArgs) {
    String[] args = new String[applicationArgs.length + 1];
    System.arraycopy(applicationArgs, 0, args, 1, applicationArgs.length);
    args[0] = applicationClass.getName();
    main(args);
  }
}
