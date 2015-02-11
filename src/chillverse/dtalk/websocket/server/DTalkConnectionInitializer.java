package chillverse.dtalk.websocket.server;

import java.io.IOException;

import org.simpleframework.http.socket.Session;
import org.simpleframework.http.socket.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DTalkConnectionInitializer implements Service {
  private static final Logger LOG = LoggerFactory.getLogger(DTalkConnectionInitializer.class);

  @Override
  public void connect(Session session) {
    try {
      new DTalkServerConnection(session);
    } catch (IOException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
  }

}
