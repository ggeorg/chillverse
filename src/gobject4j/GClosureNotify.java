package gobject4j;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

/**
 * {@code GClosureNotify} is used for various notification callbacks which can
 * be registered on closures.
 */
public interface GClosureNotify extends Callback {
  void callback(Pointer data, Pointer closure);
}
