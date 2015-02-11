package chillverse.dtalk.websocket.client.jetty;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chillverse.dtalk.DTalkBaseConnection;
import chillverse.dtalk.DTalkConnectionRegistry;
import chillverse.net.nsd.NsdServiceInfo;

@WebSocket
public class DTalkClientConnection extends DTalkBaseConnection {
  private static final Logger LOG = LoggerFactory.getLogger(DTalkClientConnection.class);

  protected static URI mkDTalkUri(NsdServiceInfo service) throws URISyntaxException {
    return new URI("ws://" + service.getHost().getHostName() + ":" + service.getPort() + "/dtalksrv");
  }

  private final NsdServiceInfo service;
  private final WebSocketClient client;
  
  private Session session;

  public DTalkClientConnection(NsdServiceInfo service) {
    this.service = service;
    this.client = new WebSocketClient();

    try {
      client.start();
      client.connect(this, mkDTalkUri(service), new ClientUpgradeRequest());
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {
      try {
        client.stop();
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  @OnWebSocketConnect
  public void onConnect(Session session) {
    LOG.trace("Got connect: {}", session);
    this.session = session;

    // register connection...
    DTalkConnectionRegistry.getInstance().register(service.getServiceName(), this);
  }

  @OnWebSocketClose
  public void onClose(int statusCode, String reason) {
    LOG.trace(">>> onClose: [{}] {}", statusCode, reason);

    session = null;

    // unregister connection...
    DTalkConnectionRegistry.getInstance().remove(service.getServiceName());
  }

  @Override
  @OnWebSocketMessage
  public void onMessage(String msg) {
    LOG.trace(">>> onMessage: {}", msg);
    super.onMessage(msg);
  }

  @Override
  public boolean isOpen() {
    return session != null ? session.isOpen() : false;
  }

  @Override
  public void close() {
    if (session != null && session.isOpen()) {
      session.close();
    }
  }

  @Override
  public boolean send(String msg) {
    if (isOpen()) {
      try {
        session.getRemote().sendString(msg);
        return true;
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return false;
  }

}
