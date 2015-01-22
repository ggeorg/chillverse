package chillverse.jna.gobject.avahi;

import chillverse.jna.AvahiLibrary;
import chillverse.jna.gobject.GErrorException;
import chillverse.jna.gobject.GObject;
import chillverse.plasma.signal.SignalConnection;
import chillverse.plasma.signal.SignalHandler;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

public class EntryGroup extends GObject {

  /**
   * The group has not yet been commited, the user must still call
   * avahi_entry_group_commit().
   */
  public static final int STATE_UNCOMMITED = 0;

  /** The entries of the group are currently being registered. */
  public static final int STATE_REGISTERING = 1;

  /** The entries have successfully been established. */
  public static final int STATE_ESTABLISHED = 2;

  /**
   * A name collision for one of the entries in the group has been detected, the
   * entries have been withdrawn.
   */
  public static final int STATE_COLLISTION = 3;

  /** Some kind of failure happened, the entries have been withdrawn */
  public static final int STATE_FAILURE = 4;

  public EntryGroup() {
    this(AvahiLibrary.INSTANCE.ga_entry_group_new());
  }

  protected EntryGroup(Pointer ptr) {
    super(ptr);
  }

  public void attach(AvahiClient client) throws GErrorException {
    final PointerByReference error = new PointerByReference(null);
    if (!AvahiLibrary.INSTANCE.ga_entry_group_attach(this, client, error)) {
      throw new GErrorException(error);
    }
  }
  
  public void addService(String serviceName, String serviceType, int port, String ... txt) throws GErrorException {
    final PointerByReference error = new PointerByReference(null);
    if (AvahiLibrary.INSTANCE.ga_entry_group_add_service(this, serviceName, serviceType, (short)port, error, txt) == null) {
      throw new GErrorException(error);
    }
  }
  
  public void commit() throws GErrorException {
    final PointerByReference error = new PointerByReference(null);
    if (!AvahiLibrary.INSTANCE.ga_entry_group_commit(this, error)) {
      throw new GErrorException(error);
    }
  }

  // ---

  public SignalConnection connect(StateChangedSignalHandler handler) {
    return super.connect("state-changed", handler);
  }

  public static abstract class StateChangedSignalHandler implements SignalHandler {
    public final void onSignal(Pointer ptr, int state, Pointer userData) {
      final EntryGroup group = EntryGroup.instanceFor(ptr);
      onSignal(group, state);
    }

    protected abstract void onSignal(EntryGroup group, int state);
  }

}
