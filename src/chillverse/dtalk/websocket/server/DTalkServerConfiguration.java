package chillverse.dtalk.websocket.server;

import java.util.Map;

import org.simpleframework.http.socket.service.Service;
import org.simpleframework.transport.trace.TraceAnalyzer;

import chillverse.dtalk.websocket.server.http.resource.Resource;
import chillverse.dtalk.websocket.server.ssl.Certificate;

public interface DTalkServerConfiguration {

  Service getService();

  Map<String, Resource> getResources();

  Certificate getCertificate();

  TraceAnalyzer getTraceAnalizer();

  int getPort();

}
