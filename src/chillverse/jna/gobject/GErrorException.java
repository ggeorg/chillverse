package chillverse.jna.gobject;

import com.sun.jna.ptr.PointerByReference;

@SuppressWarnings("serial")
public class GErrorException extends Exception {
  private int domain;
  private int code;
  
  public GErrorException() {
    super();
  }
  
  public GErrorException(GError error) {
    super(error.message);
    this.domain = error.domain;
    this.code = error.code;
  }

  public GErrorException(PointerByReference error) {
    this(new GError(error));
  }

  public int getDomain() {
    return domain;
  }

  public void setDomain(int domain) {
    this.domain = domain;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

}
