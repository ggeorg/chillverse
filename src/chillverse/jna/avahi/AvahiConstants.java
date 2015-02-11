package chillverse.jna.avahi;

public interface AvahiConstants {

  public final int IF_UNSPEC = -1;

  /* Protocol */

  /** IPv4 */
  public final int PROTO_INET = 0;
  /** IPv6 */
  public final int PROTO_INET6 = 1;
  /** Unspecified/all protocol(s). */
  public final int PROTO_UNSPEC = -1;

  /* ClientFlags */

  public final int CLIENT_FLAG_NO_FLAGS = 0;
  /** Don't read user configuration. */
  public final int CLIENT_FLAG_IGNORE_USER_CONFIG = 1;
  /**
   * Don't fail if the daemon is not available when avahi_client_new() is
   * called, instead enter AVAHI_CLIENT_CONNECTING state and wait for the daemon
   * to appear.
   */
  public final int CLIENT_FLAG_NO_FAIL = 2;

  /* ClientState */

  public final int STATE_NOT_STARTED = -1;
  public final int STATE_S_REGISTERING = 1;
  public final int STATE_S_RUNNING = 2;
  public final int STATE_S_COLLISION = 3;
  public final int STATE_FAILURE = 100;
  public final int STATE_CONNECTING = 101;

  /* LookupResultFlags */

  /** This response originates from the cache. */
  public final int LOOKUP_RESULT_CACHED = 0;
  /** This response originates from wide area DNS. */
  public final int _LOOKUP_RESULT_WIDE_AREA = 1;
  /** This response originates from multicast DNS. */
  public final int LOOKUP_RESULT_MULTICAST = 2;
  /**
   * This record/service resides on and was announced by the local host. Only
   * available in service and record browsers and only on AVAHI_BROWSER_NEW.
   */
  public final int LOOKUP_RESULT_LOCAL = 3;
  /**
   * This service belongs to the same local client as the browser object. Only
   * available in avahi-client, and only for service browsers and only on
   * AVAHI_BROWSER_NEW.
   */
  public final int LOOKUP_RESULT_OUR_OWN = 4;
  /**
   * The returned data has been defined statically by some configuration option.
   */
  public final int LOOKUP_RESULT_STATIC = 5;
}
