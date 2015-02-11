package chillverse.dtalk;

import org.json.JSONException;
import org.json.JSONObject;

public class IncomingMessage extends DTalkMessage {
  public IncomingMessage(JSONObject message) throws JSONException {
    super(message);
  }
}
