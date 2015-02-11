package chillverse.dtalk.websocket.server;

import chillverse.dtalk.websocket.server.http.WebContainer;
import chillverse.dtalk.websocket.server.http.resource.RegularExpressionEngine;
import chillverse.dtalk.websocket.server.http.resource.ResourceContainer;

class DTalkWebContainer extends WebContainer {
  public DTalkWebContainer(DTalkServerConfiguration config) {
    super(new ResourceContainer(new RegularExpressionEngine(config.getResources(),
        NotFoundResource.getInstance()),
        FailureResource.getInstance()), "DTalk/1");
  }
}
