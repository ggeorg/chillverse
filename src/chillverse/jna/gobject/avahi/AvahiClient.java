package chillverse.jna.gobject.avahi;

import chillverse.jna.AvahiLibrary;
import chillverse.jna.gobject.GErrorException;
import chillverse.jna.gobject.GObject;
import chillverse.plasma.signal.SignalConnection;
import chillverse.plasma.signal.SignalHandler;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;


public class AvahiClient extends GObject implements AvahiConstants {

  public AvahiClient() {
    this(CLIENT_FLAG_NO_FLAGS);
  }

  public AvahiClient(int flags) {
    this(AvahiLibrary.INSTANCE.ga_client_new(flags));
  }

  protected AvahiClient(Pointer ptr) {
    super(ptr);
  }

  public void start() throws GErrorException {
    final PointerByReference error = new PointerByReference(null);
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
      final AvahiClient client = AvahiClient.instanceFor(clientPtr);
      onSignal(client, state);
    }

    protected abstract void onSignal(AvahiClient client, int state);
  }

}
