package chillverse.jna.gobject.avahi;


import chillverse.jna.AvahiLibrary;
import chillverse.jna.gobject.GError;
import chillverse.jna.gobject.GErrorException;
import chillverse.jna.gobject.GObject;
import chillverse.plasma.signal.SignalConnection;
import chillverse.plasma.signal.SignalHandler;

import com.sun.jna.Pointer;


public class Avahi extends GObject {

  public static final int IF_UNSPEC = -1;

  /* Protocol */

  /** IPv4 */
  public static final int PROTO_INET = 0;
  /** IPv6 */
  public static final int PROTO_INET6 = 1;
  /** Unspecified/all protocol(s). */
  public static final int PROTO_UNSPEC = -1;

  /* ClientFlags */

  public static final int FLAG_NO_FLAGS = 0;
  /** Don't read user configuration. */
  public static final int FLAG_IGNORE_USER_CONFIG = 1;
  /**
   * Don't fail if the daemon is not available when avahi_client_new() is
   * called, instead enter AVAHI_CLIENT_CONNECTING state and wait for the daemon
   * to appear.
   */
  public static final int FLAG_NO_FAIL = 2;

  /* ClientState */

  public static final int STATE_NOT_STARTED = -1;
  public static final int STATE_S_REGISTERING = 1;
  public static final int STATE_S_RUNNING = 2;
  public static final int STATE_S_COLLISION = 3;
  public static final int STATE_FAILURE = 100;
  public static final int STATE_CONNECTING = 101;

  /* LookupFlags */

  public static final int LOOKUP_NO_FLAGS = 0;
  /** Force lookup via wide area DNS. */
  public static final int LOOKUP_USE_WIDE_AREA = 1;
  /** Force lookup via multicast DNS. */
  public static final int LOOKUP_USE_MULTICAST = 2;
  /** When doing service resolving, don't lookup TXT record. */
  public static final int LOOKUP_NO_TXT = 3;
  /** When doing service resolving, don't lookup A/AAAA record. */
  public static final int LOOKUP_NO_ADDRESS = 4;

  /* LookupResultFlags */

  /** This response originates from the cache. */
  public static final int LOOKUP_RESULT_CACHED = 0;
  /** This response originates from wide area DNS. */
  public static final int _LOOKUP_RESULT_WIDE_AREA = 1;
  /** This response originates from multicast DNS. */
  public static final int LOOKUP_RESULT_MULTICAST = 2;
  /**
   * This record/service resides on and was announced by the local host. Only
   * available in service and record browsers and only on AVAHI_BROWSER_NEW.
   */
  public static final int LOOKUP_RESULT_LOCAL = 3;
  /**
   * This service belongs to the same local client as the browser object. Only
   * available in avahi-client, and only for service browsers and only on
   * AVAHI_BROWSER_NEW.
   */
  public static final int LOOKUP_RESULT_OUR_OWN = 4;
  /**
   * The returned data has been defined statically by some configuration option.
   */
  public static final int LOOKUP_RESULT_STATIC = 5;

  public Avahi() {
    this(FLAG_NO_FLAGS);
  }

  public Avahi(int flags) {
    this(AvahiLibrary.INSTANCE.ga_client_new(flags));
  }

  protected Avahi(Pointer ptr) {
    super(ptr);
  }

  public void start() throws GErrorException {
    final GError error = new GError();
    if (!AvahiLibrary.INSTANCE.ga_client_start(this, error)) {
      throw new GErrorException(error);
    }
  }

  // TODO getState

  // TODO getFlags

  // ---

  public SignalConnection connect(StateChangedSignalHandler handler) {
    return super.connect("state-changed", handler);
  }

  public static abstract class StateChangedSignalHandler implements SignalHandler {
    public final void onSignal(Pointer clientPtr, int state, Pointer userData) {
      final Avahi client = Avahi.instanceFor(clientPtr);
      onSignal(client, state);
    }

    protected abstract void onSignal(Avahi client, int state);
  }

}
