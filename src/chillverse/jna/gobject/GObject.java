package chillverse.jna.gobject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import chillverse.jna.GObjectLibrary;
import chillverse.jna.NativeObject;
import chillverse.plasma.signal.SignalConnection;
import chillverse.plasma.signal.SignalHandler;

import com.sun.jna.Callback;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;

public class GObject extends NativeObject implements GConnectFlags {

  /*
   * Hold a strong Java reference of all connected signal handlers, so that
   * those are not eligible for GC.
   */
  private static final Map<NativeLong, Callback> sSignalHandlers = new ConcurrentHashMap<NativeLong, Callback>();

  protected GObject(Pointer ptr) {
    super(ptr);
  }

  protected final synchronized SignalConnection connect(String signal, SignalHandler handler) {
    return connect(signal, handler, 0);
  }

  protected final SignalConnection connect(String signal, SignalHandler handler, int connectFlags) {
    final NativeLong connectId = GObjectLibrary.INSTANCE.g_signal_connect_data(this, signal, handler, null, null, connectFlags);
    if (connectId.intValue() == 0) {
      throw new IllegalArgumentException(String.format("Failed to connect signal '%s'", signal));
    }

    // hold a signal handler reference
    sSignalHandlers.put(connectId, handler);

    return new SignalConnection() {
      private NativeLong id = connectId;

      @Override
      public void disconnect() {
        if (sSignalHandlers.remove(id) != null) {
          GObjectLibrary.INSTANCE.g_signal_handler_disconnect(GObject.this, id);
        }
        this.id = null;
      }
    };
  }
}
