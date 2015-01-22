package chillverse.jna;

import java.util.HashMap;

import chillverse.jna.gobject.avahi.Address;
import chillverse.jna.gobject.avahi.AvahiClient;
import chillverse.jna.gobject.avahi.EntryGroup;
import chillverse.jna.gobject.avahi.EntryGroupService;
import chillverse.jna.gobject.avahi.RecordBrowser;
import chillverse.jna.gobject.avahi.ServiceBrowser;
import chillverse.jna.gobject.avahi.ServiceResolver;
import chillverse.jna.mapper.GTypeMapper;

import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

public interface AvahiLibrary extends Library {

  String NATIVE_LIBRARY_NAME = "avahi-gobject";

  @SuppressWarnings("serial")
  static AvahiLibrary INSTANCE = ChillversePlatform.loadLibrary(NATIVE_LIBRARY_NAME, AvahiLibrary.class, new HashMap<String, Object>() {
    {
      put(Library.OPTION_TYPE_MAPPER, new GTypeMapper());
    }
  });

//@formatter:off
  Pointer ga_client_new(int flags);
  boolean ga_client_start(AvahiClient self, PointerByReference error);
//@formatter:on

//@formatter:off
  Pointer ga_record_browser_new(String name, short type);
  Pointer ga_record_browser_new_full(int iface, int protocol, String name, short clazz, short type, int lookupFlags);
  boolean ga_record_browser_attach(RecordBrowser self, AvahiClient client, PointerByReference error);
//@formatter:on

//@formatter:off
  Pointer ga_service_browser_new(String type);
  Pointer ga_service_browser_new_full(int iface, int protocol, String type, String domain, int lookupFlags);
  boolean ga_service_browser_attach(ServiceBrowser self, AvahiClient client, PointerByReference error);
//@formatter:on

//@formatter:off
  Pointer ga_service_resolver_new(int iface, int protocol, String name, String type, String domain, int addressProtocol, int lookupFlags);
  boolean ga_service_resolver_attach(ServiceResolver self, AvahiClient client, PointerByReference error);
  boolean ga_service_resolver_get_address(ServiceResolver self, Address address, short[] port);
//@formatter:on

//@formatter:off
  Pointer ga_entry_group_new();
  boolean ga_entry_group_attach(EntryGroup self, AvahiClient client, PointerByReference error);
  EntryGroupService ga_entry_group_add_service(EntryGroup self, String name, String type, short port, PointerByReference error, String ... txt);
  EntryGroupService ga_entry_group_add_service_full(EntryGroup self, int iface, int protocol, int flags, String name, String type, String domain, String host, short port, PointerByReference error, String ... txt);

  //  ga_entry_group_add_record
  //  ga_entry_group_add_record_full
  
  boolean ga_entry_group_commit(EntryGroup self, PointerByReference error);
  
  void ga_entry_group_service_freeze(EntryGroupService self);
  
//@formatter:on
}
