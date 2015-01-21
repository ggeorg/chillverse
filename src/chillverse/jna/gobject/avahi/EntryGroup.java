package chillverse.jna.gobject.avahi;

import chillverse.jna.gobject.GObject;

import com.sun.jna.Pointer;


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

  protected EntryGroup(Pointer ptr) {
    super(ptr);
  }
}
