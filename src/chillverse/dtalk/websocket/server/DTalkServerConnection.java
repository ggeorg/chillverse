package chillverse.dtalk.websocket.server;

import java.io.IOException;

import org.simpleframework.http.socket.Frame;
import org.simpleframework.http.socket.FrameListener;
import org.simpleframework.http.socket.FrameType;
import org.simpleframework.http.socket.Reason;
import org.simpleframework.http.socket.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chillverse.dtalk.DTalkBaseConnection;
import chillverse.dtalk.DTalkConnectionRegistry;
import chillverse.dtalk.DTalkMessage;

public class DTalkServerConnection extends DTalkBaseConnection implements FrameListener {
  private static final Logger LOG = LoggerFactory.getLogger(DTalkServerConnection.class);

  private Session session;
  private Object registrationKey;

  public DTalkServerConnection(Session session) throws IOException {
    this.session = session;

    // TODO: extract registration key from handshake request.
    this.registrationKey = session.hashCode();

    this.session.getChannel().register(this);

    // register connection...
    DTalkConnectionRegistry.getInstance().register(registrationKey, this);
  }

  @Override
  public void close() {
    try {
      session.getChannel().close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public boolean isOpen() {
    return session != null;
  }

  @Override
  public boolean send(String msg) {
    try {
      session.getChannel().send(msg);
      return true;
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public void onFrame(Session session, Frame frame) {
    LOG.trace(">>> onFrame: {}, frame: {}", session, frame);
    if (FrameType.TEXT == frame.getType()) {
      super.onMessage(frame.getText());
    } else {
      LOG.debug("Invalid frame: {}", frame);
    }
  }

  @Override
  public void onError(Session session, Exception cause) {
    LOG.trace(">>> onError: {}, cause: {}", session, cause);
  }

  @Override
  public void onClose(Session session, Reason reason) {
    LOG.trace(">>> onClose: {}, reason: {}", session, reason);
    this.session = null;

    // unregister connection...
    DTalkConnectionRegistry.getInstance().remove(registrationKey);
  }

  protected void ensureConnectionIsRegistared(String key) {
    if (key != null && !key.equals(registrationKey)) {
      DTalkConnectionRegistry.getInstance().remove(registrationKey);
      DTalkConnectionRegistry.getInstance().register(registrationKey = key, this);
    }
  }
}
