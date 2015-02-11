package chillverse.dtalk.websocket.server;

import org.simpleframework.http.Status;

import chillverse.dtalk.websocket.server.http.resource.StringResource;

class FailureResource extends StringResource {
  
  private static FailureResource instance = null;
  
  public static FailureResource getInstance() {
    if (instance == null) {
      instance = new FailureResource();
    }
    return instance;
  }

  private FailureResource() {
    super("An error occured serving a resource!", "text/plain; charset=UTF-8", "UTF-8", Status.INTERNAL_SERVER_ERROR);
  }

}
