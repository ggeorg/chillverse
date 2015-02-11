package chillverse.dtalk;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.pivot.util.MessageBus;
import org.apache.pivot.util.MessageBusListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chillverse.dtalk.websocket.client.jetty.DTalkClientConnection;
import chillverse.net.nsd.NsdServiceInfo;

final class DTalkDispatcher {
  private static final Logger LOG = LoggerFactory.getLogger(DTalkDispatcher.class);

  private static volatile DTalkDispatcher sInstance = null;

  public static DTalkDispatcher getInstance() {
    if (sInstance == null) {
      synchronized (DTalkDispatcher.class) {
        if (sInstance == null) {
          sInstance = new DTalkDispatcher();
        }
      }
    }
    return sInstance;
  }

  // -------------------------------------------------------------------
  // Incoming Message listener
  // -------------------------------------------------------------------

  private final MessageBusListener<IncomingMessage> incomingMsgListener = new MessageBusListener<IncomingMessage>() {
    @Override
    public void messageSent(IncomingMessage message) {
      LOG.trace(">>> incomingMsgListener:messageSent: {}", message);

      try {
        onIncomingMsg(message);
      } catch (Throwable t) {
        LOG.error("Unhandled exception", t);
      }
    }
  };

  // -------------------------------------------------------------------
  // Outgoing Message listener
  // -------------------------------------------------------------------

  private final MessageBusListener<OutgoingMessage> outgoingMsgListener = new MessageBusListener<OutgoingMessage>() {
    @Override
    public void messageSent(OutgoingMessage message) {
      LOG.trace(">>> outgoingMsgListener:messageSent: {}", message);

      try {
        onOutgoingMsg(message);
      } catch (Throwable t) {
        LOG.error("Unhandled exception", t);
      }
    }
  };

  // -------------------------------------------------------------------

  private final Map<String, Subscription> subscribers;
  private final ExecutorService executor;

  private DTalkDispatcher() {
    subscribers = new ConcurrentHashMap<String, Subscription>();
    executor = Executors.newSingleThreadExecutor();

    MessageBus.subscribe(IncomingMessage.class, incomingMsgListener);
    MessageBus.subscribe(OutgoingMessage.class, outgoingMsgListener);
  }

  final void shutdown() {
    LOG.trace(">>> shutdown");

    try {
      MessageBus.unsubscribe(IncomingMessage.class, incomingMsgListener);
    } finally {
      try {
        MessageBus.unsubscribe(OutgoingMessage.class, outgoingMsgListener);
      } finally {
        executor.shutdownNow();
      }
    }
  }

  private void onIncomingMsg(IncomingMessage message) {
    LOG.trace(">>> onIncomingMsg: {}", message);

    final String service = message.getService();

    if (DTalk.SERVICE_DTALK_DISPATCHER.equals(service)) {
      final String from = message.getFrom();
      if (from != null) {
        final String action = message.getAction();
        if (DTalk.ACTION_SUBSCRIBE.equals(action)) {
          // TODO onSubscribe()
        } else {
          // TODO onUnsubscribe();
        }
      }
    } else {
      executor.submit(message);
    }
  }

  private void onOutgoingMsg(OutgoingMessage message) {
    LOG.trace(">>> onOutgoingMsg: {}", message);
    
    final String to = message.getTo();
    if (to == null) {
      // TODO log
      return;
    }
    
    // Get connection by DTalk service name.
    DTalkConnection conn = DTalkConnectionRegistry.getInstance().get(to);
    
    if (conn != null && !conn.isOpen()) {
      // lazy clean up registry...
      DTalkConnectionRegistry.getInstance().remove(to);
      // force re-connection.
      conn = null;
    }
    
    if (conn == null) {
      conn = connectTo(to);
    }
    
    if (conn != null) {
      // TODO
    } else {
      // TODO warn
    }
  }

  /** Connect as client. */
  private DTalkConnection connectTo(String to) {
    final NsdServiceInfo service = null; // TODO
    if (service != null) {
      return new DTalkClientConnection(service);
    }
    return null;
  }

  private class Subscription implements MessageBusListener<DTalkMessage> {

    @Override
    public void messageSent(DTalkMessage message) {
      // TODO Auto-generated method stub

    }

  }
}
