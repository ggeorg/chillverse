package chillverse.dtalk;

import org.json.JSONException;
import org.json.JSONObject;

public class OutgoingMessage extends DTalkMessage {
  public OutgoingMessage(JSONObject message) throws JSONException {
    super(message);
  }
}
