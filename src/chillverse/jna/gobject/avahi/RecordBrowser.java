package chillverse.jna.gobject.avahi;

import chillverse.jna.AvahiLibrary;
import chillverse.jna.gobject.GErrorException;
import chillverse.jna.gobject.GObject;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

public class RecordBrowser extends GObject {

  public RecordBrowser(String name, short type) {
    this(AvahiLibrary.INSTANCE.ga_record_browser_new(name, type));
  }

  protected RecordBrowser(Pointer ptr) {
    super(ptr);
  }

  public void attach(AvahiClient client) throws GErrorException {
    final PointerByReference error = new PointerByReference(null);
    if (!AvahiLibrary.INSTANCE.ga_record_browser_attach(this, client, error)) {
      throw new GErrorException(error);
    }
  }

  // signals: new_record, removed_record, all_for_now, cache_exhausted, failure

}
