package chillverse.dtalk.websocket.server;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.simpleframework.http.socket.service.Service;
import org.simpleframework.transport.trace.TraceAnalyzer;

import chillverse.dtalk.websocket.server.http.resource.ContentTypeResolver;
import chillverse.dtalk.websocket.server.http.resource.FileSystemResource;
import chillverse.dtalk.websocket.server.http.resource.RedirectResource;
import chillverse.dtalk.websocket.server.http.resource.Resource;
import chillverse.dtalk.websocket.server.io.FileManager;
import chillverse.dtalk.websocket.server.io.FileResolver;
import chillverse.dtalk.websocket.server.ssl.Certificate;
import chillverse.dtalk.websocket.server.trace.LogAnalyzer;

public class DTalkServerLauncher {

  private final DTalkServer server;

  public DTalkServerLauncher(final int port) throws IOException {
    final String path = System.getProperty("user.dir") + File.separator + "war";
    final FileManager fileManager = new FileManager(new File(path));
    final FileResolver fileResolver = new FileResolver(fileManager, "index.html");

    final Map<String, String> expressions = new HashMap<String, String>();
    expressions.put(".*.vm", "text/html");
    expressions.put(".*.html", "text/html");
    expressions.put(".*.png", "image/png");
    expressions.put(".*.gif", "image/gif");
    expressions.put(".*.css", "text/css");
    expressions.put(".*.js", "text/javascript");
    expressions.put(".*", "text/html");

    final FileSystemResource fileSystemResource = new FileSystemResource(fileResolver, new ContentTypeResolver(expressions));

    final RedirectResource redirectResource = new RedirectResource(FoundResource.getInstance(), "index.html");

    final Map<String, Resource> resources = new HashMap<String, Resource>(10);
    resources.put(".*favicon.ico", NotFoundResource.getInstance());
    resources.put(".*.html", fileSystemResource);
    resources.put(".*.js", fileSystemResource);
    resources.put("/", redirectResource);

    this.server = new DTalkServer(new DTalkServerConfiguration() {
      @Override
      public Service getService() {
        return new DTalkConnectionInitializer();
      }

      @Override
      public Map<String, Resource> getResources() {
        return resources;
      }

      @Override
      public Certificate getCertificate() {
        return null;
      }

      @Override
      public TraceAnalyzer getTraceAnalizer() {
        return new LogAnalyzer();
      }

      @Override
      public int getPort() {
        return port;
      }
    });
  }

  public void start() throws IOException {
    server.start();
  }

  public void stop() throws IOException {
    server.stop();
  }

  public static void main(String[] args) throws IOException {
    new DTalkServerLauncher(8888).start();
  }
}
