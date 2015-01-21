package chillverse.jna.gobject.avahi;

import chillverse.jna.AvahiLibrary;
import chillverse.jna.gobject.GError;
import chillverse.jna.gobject.GErrorException;
import chillverse.jna.gobject.GObject;

import com.sun.jna.Pointer;


public class ServiceResolver extends GObject {

  public ServiceResolver(int iface, int protocol, String name, String type, String domain, int addressProtocol, int lookupFlags) {
    this(AvahiLibrary.INSTANCE.ga_service_resolver_new(iface, protocol, name, type, domain, addressProtocol, lookupFlags));
  }

  protected ServiceResolver(Pointer ptr) {
    super(ptr);
  }
  
  public void attach(Avahi client) throws GErrorException {
    final GError error = new GError();
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
    return address; // TODO convert to Java
  }

}
