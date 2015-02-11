package chillverse.dtalk.websocket.server;

import org.simpleframework.http.Status;

import chillverse.dtalk.websocket.server.http.resource.StringResource;

class NotFoundResource extends StringResource {

  private static NotFoundResource instance = null;

  public static NotFoundResource getInstance() {
    if (instance == null) {
      instance = new NotFoundResource();
    }
    return instance;
  }

  private NotFoundResource() {
    super("Resource could not be found!!", "text/plain; charset=UTF-8", "UTF-8", Status.NOT_FOUND);
  }

}
