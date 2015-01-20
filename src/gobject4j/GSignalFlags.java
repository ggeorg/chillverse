package gobject4j;

public interface GSignalFlags {
//@formatter:off
  public static final int RUN_FIRST   = 1 << 0;
  public static final int RUN_LAST    = 1 << 1;
  public static final int RUN_CLEANUP = 1 << 2;
  public static final int NO_RECURSE  = 1 << 3;
  public static final int DETAILED    = 1 << 4;
  public static final int ACTION      = 1 << 5;
  public static final int NO_HOOKS    = 1 << 6;
//@formatter:on
}
