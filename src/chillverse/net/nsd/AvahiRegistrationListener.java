package chillverse.net.nsd;

import chillverse.jna.avahi.EntryGroup;
import chillverse.jna.avahi.EntryGroup.StateChangedSignalHandler;
import chillverse.plasma.signal.SignalConnection;

final class AvahiRegistrationListener implements NsdManager.RegistrationListener {

  private final NsdServiceInfo kServiceInfo;
  private final NsdManager.RegistrationListener kListener;

  private EntryGroup mEntryGroup = null;
  private SignalConnection mStateChangedSignalHandlerConn = null;

  AvahiRegistrationListener(NsdServiceInfo s, NsdManager.RegistrationListener listener) {
    kServiceInfo = s;
    kListener = listener;
  }

  public NsdServiceInfo getServiceInfo() {
    return kServiceInfo;
  }

  public NsdManager.RegistrationListener getListener() {
    return kListener;
  }

  @Override
  public void onRegistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
    kListener.onRegistrationFailed(serviceInfo, errorCode);
  }

  @Override
  public void onServiceRegistered(NsdServiceInfo serviceInfo) {
    kListener.onServiceRegistered(serviceInfo);
  }

  @Override
  public void onServiceUnregistered(NsdServiceInfo serviceInfo) {
    kListener.onServiceUnregistered(serviceInfo);
  }

  @Override
  public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
    kListener.onUnregistrationFailed(serviceInfo, errorCode);
  }

  public EntryGroup getEntryGroup() {
    return mEntryGroup;
  }

  public void setEntryGroup(EntryGroup entryGroup) {
    if (mStateChangedSignalHandlerConn != null) {
      mStateChangedSignalHandlerConn.disconnect();
      mStateChangedSignalHandlerConn = null;
    }
    mEntryGroup = entryGroup;
  }

  public void connect(StateChangedSignalHandler handler) {
    assert mStateChangedSignalHandlerConn == null;
    mStateChangedSignalHandlerConn = mEntryGroup.connect(handler);
  }

}
