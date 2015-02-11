package chillverse.dtalk;

import org.apache.pivot.util.MessageBus;
import org.json.JSONException;
import org.json.JSONObject;

import chillverse.dtalk.websocket.server.DTalkServerConnection;

public abstract class DTalkBaseConnection implements DTalkConnection {

  @Override
  public void onMessage(String msg) {
    try {
      JSONObject jsonMsg = new JSONObject(msg);

      if (this instanceof DTalkServerConnection) {
        final String from = jsonMsg.optString(DTalkMessage.KEY_FROM);
        if (from != null) {
          ensureConnectionIsRegistared(from);
        } else {
          final String to = jsonMsg.optString(DTalkMessage.KEY_TO);
          if (to != null) {
            MessageBus.sendMessage(new OutgoingMessage(jsonMsg));
            return;
          }
        }
      }

      MessageBus.sendMessage(new IncomingMessage(jsonMsg));

    } catch (JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * Temporary workaround for anonymous connections.
   */
  @Deprecated
  protected void ensureConnectionIsRegistared(String key) {
    throw new UnsupportedOperationException();
  }

}
