package chillverse.jna.gobject;

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
