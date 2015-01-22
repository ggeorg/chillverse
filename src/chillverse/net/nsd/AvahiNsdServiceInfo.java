package chillverse.net.nsd;

import chillverse.jna.gobject.avahi.ServiceBrowser;
import chillverse.jna.gobject.avahi.ServiceBrowser.NewServiceSignalHandler;
import chillverse.jna.gobject.avahi.ServiceBrowser.RemovedServiceSignalHandler;
import chillverse.jna.gobject.avahi.ServiceResolver;
import chillverse.plasma.signal.SignalConnection;

final class AvahiNsdServiceInfo extends NsdServiceInfo {
  
  private ServiceBrowser browser = null;
  private SignalConnection mBrowserNewServiceSignalConn = null;
  private SignalConnection mBrowserRemovedServiceSignalConn = null;
  
  private ServiceResolver resolver = null;
  private Object mResolverFoundSignalHandler = null;
  private Object mResolverFailureSignalHandler = null;
  
  int iface;
  int prototol;
  String domain;
  int lookupResultFlags;
  
  AvahiNsdServiceInfo(ServiceBrowser browser) {
    this.browser = browser;
  }

  public AvahiNsdServiceInfo(int iface, int protocol, String name, String type, String domain, int lookupResultFlags) {
    super(name, type, null);
    this.iface = iface;
    this.prototol = protocol;
    this.domain = domain;
    this.lookupResultFlags = lookupResultFlags;
  }

  void connect(NewServiceSignalHandler handler) {
    assert mBrowserNewServiceSignalConn == null;
    mBrowserNewServiceSignalConn = browser.connect(handler);
  }

  void connect(RemovedServiceSignalHandler handler) {
    assert mBrowserRemovedServiceSignalConn == null;
    mBrowserRemovedServiceSignalConn = browser.connect(handler);
  }
  
  void setServiceResolver(ServiceResolver resolver) {
    this.resolver = resolver;
  }
  
  void connect(ServiceResolver.FoundSignalHandler handler) {
    assert mResolverFoundSignalHandler  == null;
    mResolverFoundSignalHandler = resolver.connect(handler);
  }
  
  void connect(ServiceResolver.FailureSignalHandler handler) {
    assert mResolverFailureSignalHandler   == null;
    mResolverFailureSignalHandler = resolver.connect(handler);
  }

}
