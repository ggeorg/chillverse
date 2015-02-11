package chillverse.dtalk.websocket.server.template;

import static org.simpleframework.http.Protocol.CONTENT_TYPE;
import static org.simpleframework.http.Status.OK;

import java.io.PrintStream;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;

import chillverse.dtalk.websocket.server.http.resource.Resource;

public class TemplateResource implements Resource {
   
   private final TemplateContext context;
   private final Template template;
   private final String type;
   
   public TemplateResource(Template template, TemplateContext context, String type) {
      this.template = template;
      this.context = context;
      this.type = type;
   }

   @Override
   public void handle(Request request, Response response) throws Throwable {
      PrintStream output = response.getPrintStream();    
      String text = template.render(context);

      response.setStatus(OK);
      response.setValue(CONTENT_TYPE, type);
      output.print(text);      
      output.close(); 
   }
   
   
}
