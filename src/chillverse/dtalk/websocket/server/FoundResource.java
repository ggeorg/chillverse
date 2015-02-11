package chillverse.dtalk.websocket.server;

import org.simpleframework.http.Status;

import chillverse.dtalk.websocket.server.http.resource.StringResource;

class FoundResource extends StringResource {

  private static FoundResource instance = null;

  public static StringResource getInstance() {
    if (instance == null) {
      instance = new FoundResource();
    }
    return instance;
  }

  public FoundResource() {
    super("Redirecting to /index.html", "text/plain; charset=UTF-8", "UTF-8", Status.FOUND);
  }
}
