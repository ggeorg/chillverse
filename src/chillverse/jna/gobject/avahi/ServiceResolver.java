package chillverse.jna.gobject.avahi;

import chillverse.jna.AvahiLibrary;
import chillverse.jna.gobject.GError;
import chillverse.jna.gobject.GErrorException;
import chillverse.jna.gobject.GObject;
import chillverse.plasma.signal.SignalConnection;
import chillverse.plasma.signal.SignalHandler;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

public class ServiceResolver extends GObject {

  public static final int LOOKUP_NO_FLAGS = 0;
  
  /** Force lookup via wide area DNS. */
  public static final int LOOKUP_USE_WIDE_AREA = 1;
  
  /** Force lookup via multicast DNS. */
  public static final int LOOKUP_USE_MULTICAST = 2;
  
  /** When doing service resolving, don't lookup TXT record. */
  public static final int LOOKUP_NO_TXT = 3;
  
  /** When doing service resolving, don't lookup A/AAAA record. */
  public static final int LOOKUP_NO_ADDRESS = 4;
  
  public ServiceResolver(int iface, int protocol, String name, String type, String domain, int addressProtocol) {
    this(iface, protocol, name, type, domain, addressProtocol, LOOKUP_NO_FLAGS);
  }

  public ServiceResolver(int iface, int protocol, String name, String type, String domain, int addressProtocol, int lookupFlags) {
    this(AvahiLibrary.INSTANCE.ga_service_resolver_new(iface, protocol, name, type, domain, addressProtocol, lookupFlags));
  }

  protected ServiceResolver(Pointer ptr) {
    super(ptr);
  }

  public void attach(AvahiClient client) throws GErrorException {
    final PointerByReference error = new PointerByReference(null);
    if (!AvahiLibrary.INSTANCE.ga_service_resolver_attach(this, client, error)) {
      throw new GErrorException(error);
    }
  }

  public Address getAddress() throws GErrorException {
    final Address address = new Address();
    final short[] port = new short[1];
    if (!AvahiLibrary.INSTANCE.ga_service_resolver_get_address(this, address, port)) {
      throw new GErrorException();
    }
    return address;
  }
  
  //---

  public SignalConnection connect(FoundSignalHandler handler) {
    return super.connect("found", handler);
  }

  public static abstract class FoundSignalHandler implements SignalHandler {
    public final void onSignal(Pointer ptr, int iface, int protocol, String name, String type, 
        String domain, String hostname, Address address, short port, Pointer txt, int flags,  Pointer userData) {
      final ServiceResolver resolver = ServiceResolver.instanceFor(ptr);
      onSignal(resolver, iface, protocol, name, type, domain, hostname, address, port, txt, flags);
    }

    protected abstract void onSignal(ServiceResolver resolver, int iface, int protocol, String name, String type, 
        String domain, String hostname, Address address, short port, Pointer txt, int flags);
  }
  
  // ---

  public SignalConnection connect(FailureSignalHandler handler) {
    return super.connect("failure", handler);
  }

  public static abstract class FailureSignalHandler implements SignalHandler {
    public final void onSignal(Pointer ptr, GError error, Pointer userData) {
      final ServiceResolver resolver = ServiceResolver.instanceFor(ptr);
      onSignal(resolver, error);
    }

    protected abstract void onSignal(ServiceResolver resolver, GError error);
  }
  
  @Override
  protected void finalize() throws Throwable {
    super.finalize();
    
    System.out.println("===================><=");
  }

}
