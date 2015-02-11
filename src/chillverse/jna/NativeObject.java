package chillverse.jna;


import java.util.concurrent.ConcurrentHashMap;

import chillverse.jna.gobject.GObject;

import com.sun.jna.Pointer;

public abstract class NativeObject {

  private static final ConcurrentHashMap<Pointer, NativeRef> sInstanceMap = new ConcurrentHashMap<Pointer, NativeRef>();

  @SuppressWarnings("unchecked")
  public static <T extends NativeObject> T instanceFor(Pointer ptr) {
    // ignore null pointers
    if (ptr == null) {
      return null;
    }

    NativeRef ref = sInstanceMap.get(ptr);
    NativeObject obj = ref != null ? ref.get() : null;

    // if the reference was there, but the object it pointed to had been
    // collected, remove it from the map
    if (ref != null && obj == null) {
      sInstanceMap.remove(ptr);
    }

    // return object instance
    return (T) obj;
  }

  // ---

  private final Pointer ptr;
  private final NativeRef nativeRef;

  protected NativeObject(Pointer ptr) {
    assert ptr != null : "Pointer cannot be null";

    this.ptr = ptr;
    this.nativeRef = new NativeRef(this);

    if (GObject.class.isAssignableFrom(getClass())) {
      GObjectLibrary.INSTANCE.g_object_ref((GObject) this);
      sInstanceMap.put(this.ptr, this.nativeRef);
    }
  }

  public final Pointer getPtr() {
    return ptr;
  }

  @Override
  protected void finalize() throws Throwable {
    sInstanceMap.remove(ptr, nativeRef);

    if (GObject.class.isAssignableFrom(getClass())) {
      GObjectLibrary.INSTANCE.g_object_unref((GObject) this);
    }

    super.finalize();
  }

}
