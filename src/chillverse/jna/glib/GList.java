package chillverse.jna.glib;

import chillverse.jna.GlibLibrary;
import chillverse.jna.NativeObject;

import com.sun.jna.Pointer;

public class GList<T extends NativeObject> extends NativeObject {

  public GList(Pointer ptr) {
    super(ptr);
  }
  
  public int indexOf(T item) {
    return GlibLibrary.INSTANCE.g_list_index(this, item.getPtr());
  }

}
