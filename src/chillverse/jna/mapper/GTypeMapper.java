package chillverse.jna.mapper;


import chillverse.jna.gobject.NativeObject;

import com.sun.jna.DefaultTypeMapper;
import com.sun.jna.FromNativeConverter;
import com.sun.jna.ToNativeConverter;
import com.sun.jna.TypeConverter;

public class GTypeMapper extends DefaultTypeMapper {
  private static final TypeConverter sNativeObjectTypeConverter = new NativeObjectTypeConverter();

  @Override
  public FromNativeConverter getFromNativeConverter(@SuppressWarnings("rawtypes") Class javaType) {
    if (NativeObject.class.isAssignableFrom(javaType)) {
      return sNativeObjectTypeConverter;
    }
    return super.getFromNativeConverter(javaType);
  }
  
  public ToNativeConverter getToNativeConverter(@SuppressWarnings("rawtypes") Class javaType) {
    if (NativeObject.class.isAssignableFrom(javaType)) {
      return sNativeObjectTypeConverter;
    }
    return super.getToNativeConverter(javaType);
  }
}
