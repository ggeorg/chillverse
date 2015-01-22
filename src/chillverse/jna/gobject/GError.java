package chillverse.jna.gobject;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;
import com.sun.jna.ptr.PointerByReference;

public class GError extends Structure {

  public int domain; // guint32

  public int code; // gint

  public String message;
  
  public GError(PointerByReference error) {
    useMemory(error.getValue());
  }

  @SuppressWarnings("rawtypes")
  @Override
  protected List getFieldOrder() {
    return Arrays.asList(new String[] { "domain", "code", "message" });
  }

}
