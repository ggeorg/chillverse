package chillverse.jna.mapper;


import chillverse.jna.NativeObject;

import com.sun.jna.FromNativeContext;
import com.sun.jna.Pointer;
import com.sun.jna.ToNativeContext;
import com.sun.jna.TypeConverter;

public final class NativeObjectTypeConverter implements TypeConverter {

  public Object fromNative(Object o, FromNativeContext context) {
    return o != null ? NativeObject.instanceFor((Pointer) o) : null;
  }

  public Class<?> nativeType() {
    return Pointer.class;
  }

  public Object toNative(Object o, ToNativeContext context) {
    return o != null ? ((NativeObject) o).getPtr() : null;
  }

}
