package chillverse.net.nsd;

import java.net.InetAddress;

/** A class representing service information for network service discovery. */
public class NsdServiceInfo {

  private String mServiceName;
  private String mServiceType;
  
  private DnsSdTxtRecord mTxtRecord;
  
  private InetAddress mHost;
  
  private int mPort;

  public NsdServiceInfo() {
    super();
  }

  public NsdServiceInfo(String sn, String rt, int port, DnsSdTxtRecord tr) {
    mServiceName = sn;
    mServiceType = rt;
    mPort = port;
    mTxtRecord = tr;
  }

  /** Get the service name. */
  public String getServiceName() {
    return mServiceName;
  }

  /** Set the service name. */
  public void setServiceName(String s) {
    mServiceName = s;
  }

  /** Get the service type */
  public String getServiceType() {
    return mServiceType;
  }

  /** Set the service type */
  public void setServiceType(String s) {
    mServiceType = s;
  }

  /** TODO */
  public DnsSdTxtRecord getTxtRecord() {
    return mTxtRecord;
  }

  /** TODO */
  public void setTxtRecord(DnsSdTxtRecord txtRecord) {
    mTxtRecord = txtRecord;
  }

  /** Get the host address. The host address is valid for a resolved service. */
  public InetAddress getHost() {
    return mHost;
  }

  /** Set the host address */
  public void setHost(InetAddress host) {
    mHost = host;
  }

  /** Get port number. The port number is valid for a resolved service. */
  public int getPort() {
    return mPort;
  }

  /** Set port number */
  public void setPort(int p) {
    mPort = p;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();

    sb.append("name: ").append(mServiceName).
        append(", type: ").append(mServiceType).
        append(", host: ").append(mHost).
        append(", port: ").append(mPort).
        append(", txtRecord: ").append(mTxtRecord);
    return sb.toString();
  }

}
