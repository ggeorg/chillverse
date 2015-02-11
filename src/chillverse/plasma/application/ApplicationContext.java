package chillverse.plasma.application;

import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

import chillverse.jna.ClutterLibrary;
import chillverse.util.Version;

/** Base class for application contexts. */
public abstract class ApplicationContext {

  /** Class representing a scheduled callback. */
  public static final class ScheduledCallback extends TimerTask {
    private Runnable callback;
    private QueuedCallback queuedCallback = null;

    private ScheduledCallback(Runnable callback) {
      this.callback = callback;
    }

    @Override
    public void run() {
      if (queuedCallback != null) {
        queuedCallback.cancel();
      }
      queuedCallback = queueCallback(callback);
    }

    @Override
    public boolean cancel() {
      if (queuedCallback != null) {
        queuedCallback.cancel();
      }
      return super.cancel();
    }
  }

  /** Class representing a queued callback. */
  public static class QueuedCallback implements Runnable {
    private Runnable callback;
    private volatile boolean executed = false;
    private volatile boolean cancelled = false;

    private QueuedCallback(Runnable callback) {
      this.callback = callback;
    }

    // @Override
    public void run() {
      if (!cancelled) {
        try {
          callback.run();
        } catch (Exception exception) {
          exception.printStackTrace();

          for (Application application : applications) {
            if (application instanceof Application.UncaughtExceptionHandler) {
              Application.UncaughtExceptionHandler uncaughtExceptionHandler =
                  (Application.UncaughtExceptionHandler) application;
              uncaughtExceptionHandler.uncaughtExceptionThrown(exception);
            }
          }
        }

        // for (Display display : displays) {
        // display.validate();
        // }

        executed = true;
      }
    }

    public boolean cancel() {
      cancelled = true;
      return (!executed);
    }
  }

  protected static URL origin = null;

  protected static final ArrayList<Application> applications = new ArrayList<Application>();

  private static Timer timer = null;

  private static Version sJvmVersion = null;
  private static Version sVersion = null;

  /** The main thread instance (singleton) */
  private static final Thread sMainThread = Thread.currentThread();

  static {
    // Get the JVM version
    sJvmVersion = Version.decode(System.getProperty("java.vm.version"));

    // Get the Pivot version
    String version = ApplicationContext.class.getPackage().getImplementationVersion();
    if (version == null) {
      sVersion = new Version(0, 0, 0, 0);
    } else {
      sVersion = Version.decode(version);
    }
  }

  /**
   * Returns this application's origin (the URL of it's originating server).
   * 
   * @return The application's origin, or {@code null} if the origin cannot be
   *         determined.
   */
  public static URL getOrigin() {
    return origin;
  }

  /**
   * Returns the current JVM version.
   * 
   * @return The current JVM version, or <tt>null</tt> if the version can't be
   *         determined.
   */
  public static Version getJVMVersion() {
    return sJvmVersion;
  }

  /**
   * Returns the current SDK version.
   * 
   * @return The current SDK version (determined at build time), or {@code null}
   *         if the version can't be determined.
   */
  public static Version getSDKVersion() {
    return sVersion;
  }

  /**
   * Schedules a task for one-time execution. The task will be executed on the
   * UI thread.
   * 
   * @param callback
   *          The task to execute.
   * 
   * @param delay
   *          The length of time to wait before executing the task (in
   *          milliseconds).
   */
  public static ScheduledCallback scheduleCallback(Runnable callback, long delay) {
    ScheduledCallback scheduledCallback = new ScheduledCallback(callback);

    // TODO This is a workaround for a potential OS X bug; revisit
    try {
      try {
        timer.schedule(scheduledCallback, delay);
      } catch (IllegalStateException exception) {
        createTimer();
        timer.schedule(scheduledCallback, delay);
      }
    } catch (Throwable throwable) {
      System.err.println("Unable to schedule callback: " + throwable);
    }

    return scheduledCallback;
  }

  /**
   * Schedules a task for repeated execution. The task will be executed on the
   * UI thread and will begin executing immediately.
   * 
   * @param callback
   *          The task to execute.
   * 
   * @param period
   *          The interval at which the task will be repeated (in milliseconds).
   */
  public static ScheduledCallback scheduleRecurringCallback(Runnable callback, long period) {
    return scheduleRecurringCallback(callback, 0, period);
  }

  /**
   * Schedules a task for repeated execution. The task will be executed on the
   * UI thread.
   * 
   * @param callback
   *          The task to execute.
   * 
   * @param delay
   *          The length of time to wait before the first execution of the task
   *          (milliseconds).
   * 
   * @param period
   *          The interval at which the task will be repeated (also in
   *          milliseconds).
   */
  public static ScheduledCallback scheduleRecurringCallback(Runnable callback, long delay, long period) {
    ScheduledCallback scheduledCallback = new ScheduledCallback(callback);

    try {
      try {
        timer.schedule(scheduledCallback, delay, period);
      } catch (IllegalStateException exception) {
        createTimer();
        timer.schedule(scheduledCallback, delay, period);
      }
    } catch (Throwable throwable) {
      System.err.println("Unable to schedule callback: " + throwable);
    }

    return scheduledCallback;
  }

  /**
   * Returns {@code true} if the calling thread is the application main thread.
   * 
   * @return {@code true} if running on the application main thread.
   * 
   * @see #queueCallback(Runnable)
   */
  public static boolean isMainThread() {
    return Thread.currentThread() == sMainThread;
  }

  /**
   * Queues a task to execute after all pending events have been processed and
   * returns without waiting for the task to complete.
   * 
   * @param callback
   *          The task to execute.
   * 
   * @see #isMainThread()
   */
  public static QueuedCallback queueCallback(final Runnable callback) {
    QueuedCallback queuedCallback = new QueuedCallback(callback);

    try {
      ClutterLibrary.INSTANCE.clutter_threads_add_idle(new Callback() {
        @SuppressWarnings("unused")
        public int run(Pointer data) {
          callback.run();
          return 0; // G_SOURCE_REMOVE
        }
      }, Pointer.NULL);
    } catch (Throwable throwable) {
      System.err.println("Unable to queue callback: " + throwable);
    }

    return queuedCallback;
  }

  protected static void createTimer() {
    timer = new Timer();
  }

  protected static void destroyTimer() {
    timer.cancel();
    timer = null;
  }

  public static void handleUncaughtException(Exception exception) {
    int n = 0;
    for (Application application : applications) {
      if (application instanceof Application.UncaughtExceptionHandler) {
        Application.UncaughtExceptionHandler uncaughtExceptionHandler =
            (Application.UncaughtExceptionHandler) application;
        uncaughtExceptionHandler.uncaughtExceptionThrown(exception);
        n++;
      }
    }

    if (n == 0) {
      exception.printStackTrace();
//@formatter:off
//        Display display = (displays.getLength() > 0) ? displays.get(0) : null;
//        if (display == null) {
//            return;
//        }
//
//        String message = exception.getClass().getName();
//
//        TextArea body = null;
//        String bodyText = exception.getMessage();
//        if (bodyText != null && bodyText.length() > 0) {
//            body = new TextArea();
//            body.setText(bodyText);
//            body.setEditable(false);
//        }
//
//        Alert alert = new Alert(MessageType.ERROR, message, null, body, false);
//        alert.open(display);
//@formatter:on
    }
  }

}
