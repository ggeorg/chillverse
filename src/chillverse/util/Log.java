package chillverse.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class Log {

  private Log() {
    // no way to create
  }

  private static final void log(Logger logger, Level level, String msg) {
    logger.log(level, msg);
  }

  private static final void log(Logger logger, Level level, String msg, Throwable t) {
    logger.log(level, msg, t);
  }

  private static final void log(Logger logger, Level level, String fmt, Object arg) {
    logger.log(level, String.format(fmt, arg));
  }

  private static final void log(Logger logger, Level level, String fmt, Object arg, Throwable t) {
    logger.log(level, String.format(fmt, arg), t);
  }

  private static final void log(Logger logger, Level level, String fmt, Object... args) {
    logger.log(level, String.format(fmt, args));
  }

  /* VERBOSE */

  public static void v(Logger logger, String s) {
    if (logger.isLoggable(Level.FINEST)) {
      log(logger, Level.FINEST, s);
    }
  }

  public static void v(Logger logger, String s, Throwable t) {
    if (logger.isLoggable(Level.FINEST)) {
      log(logger, Level.FINEST, s, t);
    }
  }

  public static void v(Logger logger, String s, Object arg) {
    if (logger.isLoggable(Level.FINEST)) {
      log(logger, Level.FINEST, s, arg);
    }
  }

  public static void v(Logger logger, String s, Object arg, Throwable t) {
    if (logger.isLoggable(Level.FINEST)) {
      log(logger, Level.FINEST, s, arg, t);
    }
  }

  /* DEBUG */

  public static void d(Logger logger, String s) {
    if (logger.isLoggable(Level.FINE)) {
      log(logger, Level.FINE, s);
    }
  }

  public static void d(Logger logger, String s, Throwable t) {
    if (logger.isLoggable(Level.FINE)) {
      log(logger, Level.FINE, s, t);
    }
  }

  public static void d(Logger logger, String s, Object arg) {
    if (logger.isLoggable(Level.FINE)) {
      log(logger, Level.FINE, s, arg);
    }
  }

  public static void d(Logger logger, String s, Object arg, Throwable t) {
    if (logger.isLoggable(Level.FINE)) {
      log(logger, Level.FINE, s, arg, t);
    }
  }

  public static void d(Logger logger, String s, Object... args) {
    if (logger.isLoggable(Level.FINE)) {
      log(logger, Level.FINE, s, args);
    }
  }

  /* INFO */

  public static void i(Logger logger, String s) {
    if (logger.isLoggable(Level.INFO)) {
      log(logger, Level.INFO, s);
    }
  }

  public static void i(Logger logger, String s, Throwable t) {
    if (logger.isLoggable(Level.INFO)) {
      log(logger, Level.INFO, s, t);
    }
  }

  public static void i(Logger logger, String s, Object arg) {
    if (logger.isLoggable(Level.INFO)) {
      log(logger, Level.INFO, s, arg);
    }
  }

  public static void i(Logger logger, String s, Object arg, Throwable t) {
    if (logger.isLoggable(Level.INFO)) {
      log(logger, Level.INFO, s, arg, t);
    }
  }

  public static void i(Logger logger, String s, Object... args) {
    if (logger.isLoggable(Level.INFO)) {
      log(logger, Level.INFO, s, args);
    }
  }

  /* WARNING */

  public static void w(Logger logger, String s) {
    if (logger.isLoggable(Level.WARNING)) {
      log(logger, Level.WARNING, s);
    }
  }

  public static void w(Logger logger, String s, Throwable t) {
    if (logger.isLoggable(Level.WARNING)) {
      log(logger, Level.WARNING, s, t);
    }
  }

  public static void w(Logger logger, String s, Object arg) {
    if (logger.isLoggable(Level.WARNING)) {
      log(logger, Level.WARNING, s, arg);
    }
  }

  public static void w(Logger logger, String s, Object arg, Throwable t) {
    if (logger.isLoggable(Level.WARNING)) {
      log(logger, Level.WARNING, s, arg, t);
    }
  }

  public static void w(Logger logger, String s, Object... args) {
    if (logger.isLoggable(Level.WARNING)) {
      log(logger, Level.WARNING, s, args);
    }
  }

  /* ERROR */

  public static void e(Logger logger, String s) {
    if (logger.isLoggable(Level.SEVERE)) {
      log(logger, Level.SEVERE, s);
    }
  }

  public static void e(Logger logger, String s, Throwable t) {
    if (logger.isLoggable(Level.SEVERE)) {
      log(logger, Level.SEVERE, s, t);
    }
  }

  public static void e(Logger logger, String s, Object arg) {
    if (logger.isLoggable(Level.SEVERE)) {
      log(logger, Level.SEVERE, s, arg);
    }
  }

  public static void e(Logger logger, String s, Object arg, Throwable t) {
    if (logger.isLoggable(Level.SEVERE)) {
      log(logger, Level.SEVERE, s, arg, t);
    }
  }

  public static void e(Logger logger, String s, Object... args) {
    if (logger.isLoggable(Level.SEVERE)) {
      log(logger, Level.SEVERE, s, args);
    }
  }

}
