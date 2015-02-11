package chillverse.net.nsd;

final class AvahiNsdServiceInfo extends NsdServiceInfo {

  int iface;
  int prototol;
  String domain;
  int lookupResultFlags;

  public AvahiNsdServiceInfo(int iface, int protocol, String name, String type, String domain, int lookupResultFlags) {
    super(name, type, 0, null);
    this.iface = iface;
    this.prototol = protocol;
    this.domain = domain;
    this.lookupResultFlags = lookupResultFlags;
  }

}
