package chillverse.dtalk;

import org.apache.pivot.util.MessageBus;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class DTalkMessage implements Runnable {
  
  public static final String KEY_TO = "to";
  public static final String KEY_FROM = "from";
  public static final String KEY_SERVICE = "service";
  public static final String KEY_ACTION = "action";

  private final JSONObject message;
  
  protected DTalkMessage(JSONObject message) throws JSONException {
    this.message = message; // TODO clone?
  }
  
  public String getTo() {
    return message.optString(KEY_TO);
  }
  
  public void setTo(String to) {
    try {
      message.put(KEY_TO, to);
    } catch (JSONException e) {
      message.remove(KEY_TO);
    }
  }
  
  public String getFrom() {
    return message.optString(KEY_FROM);
  }
  
  public void setFrom(String to) {
    try {
      message.put(KEY_FROM, to);
    } catch (JSONException e) {
      message.remove(KEY_FROM);
    }
  }

  public String getService() {
    return message.optString(KEY_SERVICE);
  }
  
  public void setService(String service) {
    try {
      message.put(KEY_SERVICE, service);
    } catch (JSONException e) {
      message.remove(KEY_SERVICE);
    }
  }
  
  public String getAction() {
    return message.optString(KEY_ACTION);
  }
  
  public void setAction(String action) {
    try {
      message.put(KEY_ACTION, action);
    } catch (JSONException e) {
      message.remove(KEY_ACTION);
    }
  }

  @Override
  public void run() {
    try {
      MessageBus.sendMessage(getService(), this);
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + " [message=" + message + "]";
  }

}
