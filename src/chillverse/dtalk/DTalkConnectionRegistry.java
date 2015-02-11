package chillverse.dtalk;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DTalkConnectionRegistry {
  @SuppressWarnings("unused")
  private static final Logger LOG = LoggerFactory.getLogger(DTalkConnectionRegistry.class);

  private static volatile DTalkConnectionRegistry sInstance = null;

  public static DTalkConnectionRegistry getInstance() {
    if (sInstance == null) {
      synchronized (DTalkConnectionRegistry.class) {
        if (sInstance == null) {
          sInstance = new DTalkConnectionRegistry();
        }
      }
    }
    return sInstance;
  }

  private final Map<Object, DTalkConnection> connections;

  private DTalkConnectionRegistry() {
    connections = new ConcurrentHashMap<Object, DTalkConnection>();
  }

  public DTalkConnection get(Object key) {
    return connections.get(key);
  }
  
  public DTalkConnection register(Object key, DTalkConnection connection) {
    return connections.put(key, connection);
  }

  public DTalkConnection remove(Object key) {
    return connections.get(key);
  }
}
