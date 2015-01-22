package chillverse.net.nsd;

@SuppressWarnings("serial")
public class NsdManagerException extends Exception {

  private final int code;

  public NsdManagerException(String message, int code) {
    super(message);
    this.code = code;
  }

  public int getCode() {
    return code;
  }

}
