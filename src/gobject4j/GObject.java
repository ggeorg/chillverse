package gobject4j;

import gobject4j.jna.GObjectLibrary;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import plasma.signal.SignalConnection;
import plasma.signal.SignalHandler;

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

  protected final synchronized SignalConnection connect(String signal, SignalHandler handler, int connectFlags) {
    final NativeLong connectId = GObjectLibrary.INSTANCE.g_signal_connect_data(this, signal, handler, null, null, connectFlags);
    if (connectId.intValue() == 0) {
      throw new IllegalArgumentException(String.format("Failed to connect signal '%s'", signal));
    }

    // hold a signal handler reference
    sSignalHandlers.put(connectId, handler);

    return new SignalConnection() {
      public void disconnect() {
        GObjectLibrary.INSTANCE.g_signal_handler_disconnect(GObject.this, connectId);

        // release the signal handler reference
        sSignalHandlers.remove(connectId);
      }
    };
  }
}
