package chillverse.plasma.test;

import chillverse.jna.gobject.GErrorException;
import chillverse.jna.gobject.avahi.Avahi;

public class Test01 {
  public static void main(String[] args) {
    Avahi c = new Avahi();
    
    try {
      c.start();
    } catch (GErrorException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
