package chillverse.jna.gobject;

/**
 * The connection flags are used to specify the behavior of a signal's
 * connection.
 */
public interface GConnectFlags {

  /**
   * Whether the handler should be called before or after the default handler of
   * the signal.
   */
  public static int G_CONNECT_AFTER = 1 << 0;

  /**
   * Whether the instance and data should be swapped when calling the handler;
   * see g_signal_connect_swapped() for an example.
   */
  public static int G_CONNECT_SWAPPED = 1 << 1;

}
