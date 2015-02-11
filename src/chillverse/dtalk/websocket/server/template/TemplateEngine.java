package chillverse.dtalk.websocket.server.template;

public interface TemplateEngine {
   String renderTemplate(TemplateContext context, String path) throws Exception;
   boolean validTemplate(String path) throws Exception;
}
