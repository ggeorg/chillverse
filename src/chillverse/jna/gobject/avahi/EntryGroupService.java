package chillverse.jna.gobject.avahi;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;

public class EntryGroupService extends Structure {
  public int iface; // AvahiIfIndex
  public int protocol; // AvahiProtocol
  public int flags; // AvahiPublishFlags
  public String name;
  public String type;
  public String domain;
  public String host;
  public short port; // guint16

  @SuppressWarnings("rawtypes")
  @Override
  protected List getFieldOrder() {
    return Arrays.asList(new String[] {
        "iface", "protocol", "flags", "name", "type", "domain", "host", "port"
    });
  }

}
