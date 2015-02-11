package chillverse.dtalk.websocket.server;

import java.io.IOException;

import org.simpleframework.http.socket.service.DirectRouter;
import org.simpleframework.http.socket.service.RouterContainer;

import chillverse.dtalk.websocket.server.http.WebServer;

public final class DTalkServer extends WebServer {
  
  private static final int NTHREADS = 2;

  public DTalkServer(DTalkServerConfiguration config) throws IOException {
    super(new RouterContainer(new DTalkWebContainer(config), new DirectRouter(config.getService()), NTHREADS), 
        config.getCertificate(), 
        config.getTraceAnalizer(), 
        config.getPort());
  }

}
