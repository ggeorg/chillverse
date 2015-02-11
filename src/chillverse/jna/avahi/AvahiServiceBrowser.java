package chillverse.jna.avahi;

import chillverse.jna.AvahiGObjectLibrary;
import chillverse.jna.NativeObject;
import chillverse.jna.gobject.GError;
import chillverse.jna.gobject.GErrorException;
import chillverse.jna.gobject.GObject;
import chillverse.plasma.signal.SignalConnection;
import chillverse.plasma.signal.SignalHandler;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

public class AvahiServiceBrowser extends GObject {

  public AvahiServiceBrowser(String type) {
    this(AvahiGObjectLibrary.INSTANCE.ga_service_browser_new(type));
  }
  
  public AvahiServiceBrowser(int iface, int protocol, String type, String domain, int lookupFlags) {
    this(AvahiGObjectLibrary.INSTANCE.ga_service_browser_new_full(iface, protocol, type, domain, lookupFlags));
  }

  protected AvahiServiceBrowser(Pointer ptr) {
    super(ptr);
  }

  public void attach(AvahiClient client) throws GErrorException {
    final PointerByReference error = new PointerByReference(null);
    if (!AvahiGObjectLibrary.INSTANCE.ga_service_browser_attach(this, client, error)) {
      throw new GErrorException(error);
    }
  }

  // signals: new_service, removed_service, all_for_now, cache_exhausted, failure
  
  // ---
  
  public SignalConnection connect(NewServiceSignalHandler handler) {
    return super.connect("new-service", handler);
  }
  
  public static abstract class NewServiceSignalHandler implements SignalHandler {
    public final void onSignal(Pointer ptr, int iface, int protocol, String name, String type, String domain, int lookupResultFlags, Pointer userData) {
      final AvahiServiceBrowser browser = NativeObject.instanceFor(ptr);
      onSignal(browser, iface, protocol, name, type, domain, lookupResultFlags);
    }

    protected abstract void onSignal(AvahiServiceBrowser browser, int iface, int protocol, String name, String type, String domain, int lookupResultFlags);
  }
  
  // ---
  
  public SignalConnection connect(RemovedServiceSignalHandler handler) {
    return super.connect("removed-service", handler);
  }
  
  public static abstract class RemovedServiceSignalHandler implements SignalHandler {
    public final void onSignal(Pointer ptr, int iface, int protocol, String name, String type, String domain, int lookupResultFlags, Pointer userData) {
      final AvahiServiceBrowser browser = NativeObject.instanceFor(ptr);
      onSignal(browser, iface, protocol, name, type, domain, lookupResultFlags);
    }

    protected abstract void onSignal(AvahiServiceBrowser browser, int iface, int protocol, String name, String type, String domain, int lookupResultFlags);
  }
  
  // TODO all-for-now
  
  // TODO cache-exhausted
  
  // ---
  
  public SignalConnection connect(FailureSignalHandler handler) {
    return super.connect("failure", handler);
  }
  
  public static abstract class FailureSignalHandler implements SignalHandler {
    public final void onSignal(Pointer ptr, GError error, Pointer userData) {
      final AvahiServiceBrowser browser = instanceFor(ptr);
      onSignal(browser, error);
    }

    protected abstract void onSignal(AvahiServiceBrowser browser, GError error);
  }

}
