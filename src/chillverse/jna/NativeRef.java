package chillverse.jna;

import java.lang.ref.WeakReference;


final class NativeRef extends WeakReference<NativeObject> {
  public NativeRef(NativeObject referent) {
    super(referent);
  }
}
