package chillverse.jna;

import java.util.HashMap;

import chillverse.jna.avahi.Address;
import chillverse.jna.avahi.AvahiClient;
import chillverse.jna.avahi.EntryGroup;
import chillverse.jna.avahi.EntryGroupService;
import chillverse.jna.avahi.AvahiServiceBrowser;
import chillverse.jna.avahi.AvahiServiceResolver;
import chillverse.jna.mapper.GTypeMapper;

import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

public interface AvahiGObjectLibrary extends Library {

  String NATIVE_LIBRARY_NAME = "avahi-gobject";

  @SuppressWarnings("serial")
  static AvahiGObjectLibrary INSTANCE = ChillversePlatform.loadLibrary(NATIVE_LIBRARY_NAME, AvahiGObjectLibrary.class, new HashMap<String, Object>() {
    {
      put(Library.OPTION_TYPE_MAPPER, new GTypeMapper());
    }
  });

//@formatter:off
  Pointer ga_client_new(int flags);
  boolean ga_client_start(AvahiClient self, PointerByReference error);
//@formatter:on

//@formatter:off
  Pointer ga_service_browser_new(String type);
  Pointer ga_service_browser_new_full(int iface, int protocol, String type, String domain, int lookupFlags);
  boolean ga_service_browser_attach(AvahiServiceBrowser self, AvahiClient client, PointerByReference error);
//@formatter:on

//@formatter:off
  Pointer ga_service_resolver_new(int iface, int protocol, String name, String type, String domain, int addressProtocol, int lookupFlags);
  boolean ga_service_resolver_attach(AvahiServiceResolver self, AvahiClient client, PointerByReference error);
  boolean ga_service_resolver_get_address(AvahiServiceResolver self, Address address, short[] port);
//@formatter:on

//@formatter:off
  Pointer ga_entry_group_new();
  boolean ga_entry_group_attach(EntryGroup self, AvahiClient client, PointerByReference error);
  EntryGroupService ga_entry_group_add_service_strlist(EntryGroup group, String name, String type, short port, PointerByReference error, Pointer txt);
  EntryGroupService ga_entry_group_add_service(EntryGroup self, String name, String type, short port, PointerByReference error, String ... txt);
  EntryGroupService ga_entry_group_add_service_full(EntryGroup self, int iface, int protocol, int flags, String name, String type, String domain, String host, short port, PointerByReference error, String ... txt);

  //  ga_entry_group_add_record
  //  ga_entry_group_add_record_full
  
  boolean ga_entry_group_commit(EntryGroup self, PointerByReference error);
  
  void ga_entry_group_service_freeze(EntryGroupService self);
  
//@formatter:on
}
