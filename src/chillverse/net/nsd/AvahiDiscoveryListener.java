package chillverse.net.nsd;

import chillverse.jna.avahi.AvahiServiceBrowser;
import chillverse.jna.avahi.AvahiServiceBrowser.NewServiceSignalHandler;
import chillverse.jna.avahi.AvahiServiceBrowser.RemovedServiceSignalHandler;
import chillverse.plasma.signal.SignalConnection;

final class AvahiDiscoveryListener implements NsdManager.DiscoveryListener {

  private final String serviceType;
  private final NsdManager.DiscoveryListener listener;

  private AvahiServiceBrowser browser = null;
  private SignalConnection mBrowserNewServiceSignalConn = null;
  private SignalConnection mBrowserRemovedServiceSignalConn = null;

  AvahiDiscoveryListener(String serviceType, NsdManager.DiscoveryListener listener) {
    this.serviceType = serviceType;
    this.listener = listener;
  }

  public String getServiceType() {
    return serviceType;
  }

  public NsdManager.DiscoveryListener getListener() {
    return listener;
  }

  @Override
  public void onDiscoveryStarted(String serviceType) {
    listener.onDiscoveryStarted(serviceType);
  }

  @Override
  public void onDiscoveryStopped(String serviceType) {
    listener.onDiscoveryStopped(serviceType);
  }

  @Override
  public void onServiceFound(NsdServiceInfo serviceInfo) {
    listener.onServiceFound(serviceInfo);
  }

  @Override
  public void onServiceLost(NsdServiceInfo serviceInfo) {
    listener.onServiceLost(serviceInfo);
  }

  @Override
  public void onStartDiscoveryFailed(String serviceType, int errorCode) {
    listener.onStartDiscoveryFailed(serviceType, errorCode);
  }

  @Override
  public void onStopDiscoveryFailed(String serviceType, int errorCode) {
    listener.onStopDiscoveryFailed(serviceType, errorCode);
  }

  void setServiceBrowser(AvahiServiceBrowser browser) {
    if (mBrowserNewServiceSignalConn != null) {
      mBrowserNewServiceSignalConn.disconnect();
      mBrowserNewServiceSignalConn = null;
    }
    if (mBrowserRemovedServiceSignalConn != null) {
      mBrowserRemovedServiceSignalConn.disconnect();
      mBrowserRemovedServiceSignalConn = null;
    }
    this.browser = browser;
  }

  void connect(NewServiceSignalHandler handler) {
    assert mBrowserNewServiceSignalConn == null;
    mBrowserNewServiceSignalConn = browser.connect(handler);
  }

  void connect(RemovedServiceSignalHandler handler) {
    assert mBrowserRemovedServiceSignalConn == null;
    mBrowserRemovedServiceSignalConn = browser.connect(handler);
  }

}
