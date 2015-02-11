package chillverse.dtalk;

public interface DTalkConnection {

  void close();
  
  boolean isOpen();
  
  void onMessage(String msg);
  
  boolean send(String msg);

}
