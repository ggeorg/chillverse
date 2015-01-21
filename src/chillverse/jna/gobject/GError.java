package chillverse.jna.gobject;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;

public class GError extends Structure {

  public int domain;

  public int code;

  public String message;

  @SuppressWarnings("rawtypes")
  @Override
  protected List getFieldOrder() {
    return Arrays.asList(new String[] { "domain", "code", "message" });
  }

}
