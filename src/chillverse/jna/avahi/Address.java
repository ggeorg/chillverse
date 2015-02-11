package chillverse.jna.avahi;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;
import com.sun.jna.Union;

public class Address extends Structure {
  
  public static class IPv4Address extends Structure {
    public char[] address = new char[16];

    @SuppressWarnings("rawtypes")
    @Override
    protected List getFieldOrder() {
      return Arrays.asList(new String[] {"address"});
    }
  }
  
  public static class IPv6Address extends Structure {
    public int address;

    @SuppressWarnings("rawtypes")
    @Override
    protected List getFieldOrder() {
      return Arrays.asList(new String[] {"address"});
    }
  }
  
  public static class AddressData extends Union {
    public IPv4Address ipv4;
    public IPv6Address ipv6;
    public byte[] data = new byte[1];
  }

  public int protocol;
  public AddressData data;

  @SuppressWarnings("rawtypes")
  @Override
  protected List getFieldOrder() {
    return Arrays.asList(new String[] { "protocol", "data" });
  }
}
