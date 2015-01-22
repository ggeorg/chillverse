package chillverse.jna.gobject.avahi;

import chillverse.jna.AvahiLibrary;
import chillverse.jna.gobject.GError;
import chillverse.jna.gobject.GErrorException;
import chillverse.jna.gobject.GObject;
import chillverse.jna.gobject.NativeObject;
import chillverse.plasma.signal.SignalConnection;
import chillverse.plasma.signal.SignalHandler;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

public class ServiceBrowser extends GObject {

  public ServiceBrowser(String type) {
    this(AvahiLibrary.INSTANCE.ga_service_browser_new(type));
  }
  
  public ServiceBrowser(int iface, int protocol, String type, String domain, int lookupFlags) {
    this(AvahiLibrary.INSTANCE.ga_service_browser_new_full(iface, protocol, type, domain, lookupFlags));
  }

  protected ServiceBrowser(Pointer ptr) {
    super(ptr);
  }

  public void attach(AvahiClient client) throws GErrorException {
    final PointerByReference error = new PointerByReference(null);
    if (!AvahiLibrary.INSTANCE.ga_service_browser_attach(this, client, error)) {
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
      final ServiceBrowser browser = NativeObject.instanceFor(ptr);
      onSignal(browser, iface, protocol, name, type, domain, lookupResultFlags);
    }

    protected abstract void onSignal(ServiceBrowser browser, int iface, int protocol, String name, String type, String domain, int lookupResultFlags);
  }
  
  // ---
  
  public SignalConnection connect(RemovedServiceSignalHandler handler) {
    return super.connect("removed-service", handler);
  }
  
  public static abstract class RemovedServiceSignalHandler implements SignalHandler {
    public final void onSignal(Pointer ptr, int iface, int protocol, String name, String type, String domain, int lookupResultFlags, Pointer userData) {
      final ServiceBrowser browser = NativeObject.instanceFor(ptr);
      onSignal(browser, iface, protocol, name, type, domain, lookupResultFlags);
    }

    protected abstract void onSignal(ServiceBrowser browser, int iface, int protocol, String name, String type, String domain, int lookupResultFlags);
  }
  
  // TODO all-for-now
  
  // TODO cache-exhausted
  
  // ---
  
  public SignalConnection connect(FailureSignalHandler handler) {
    return super.connect("failure", handler);
  }
  
  public static abstract class FailureSignalHandler implements SignalHandler {
    public final void onSignal(Pointer ptr, GError error, Pointer userData) {
      final ServiceBrowser browser = instanceFor(ptr);
      onSignal(browser, error);
    }

    protected abstract void onSignal(ServiceBrowser browser, GError error);
  }

}
