package chillverse.plasma.test;

import chillverse.jna.gobject.GErrorException;
import chillverse.jna.gobject.avahi.AvahiClient;

public class Test01 {
  public static void main(String[] args) {
    AvahiClient c = new AvahiClient();
    
    try {
      c.start();
    } catch (GErrorException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
