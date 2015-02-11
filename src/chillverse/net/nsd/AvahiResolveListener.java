package chillverse.net.nsd;

import chillverse.jna.avahi.AvahiServiceResolver;
import chillverse.plasma.signal.SignalConnection;

final class AvahiResolveListener implements NsdManager.ResolveListener {

  private final NsdManager.ResolveListener listener;

  private AvahiServiceResolver resolver = null;
  private SignalConnection mResolverFoundSignalHandler = null;
  private SignalConnection mResolverFailureSignalHandler = null;

  public AvahiResolveListener(NsdManager.ResolveListener listener) {
    this.listener = listener;
  }

  @Override
  public void onServiceResolved(NsdServiceInfo serviceInfo) {
    listener.onServiceResolved(serviceInfo);

    // disconnect
    setServiceResolver(null);
  }

  @Override
  public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
    listener.onResolveFailed(serviceInfo, errorCode);

    // disconnect
    setServiceResolver(null);
  }

  void setServiceResolver(AvahiServiceResolver resolver) {
    if (mResolverFoundSignalHandler != null) {
      mResolverFoundSignalHandler.disconnect();
      mResolverFoundSignalHandler = null;
    }
    if (mResolverFailureSignalHandler != null) {
      mResolverFailureSignalHandler.disconnect();
      mResolverFailureSignalHandler = null;
    }
    this.resolver = resolver;
  }

  void connect(AvahiServiceResolver.FoundSignalHandler handler) {
    assert mResolverFoundSignalHandler == null;
    mResolverFoundSignalHandler = resolver.connect(handler);
  }

  void connect(AvahiServiceResolver.FailureSignalHandler handler) {
    assert mResolverFailureSignalHandler == null;
    mResolverFailureSignalHandler = resolver.connect(handler);
  }

}
