package chillverse.text;

public class TextUtils {

  /**
   * Returns {@code true} if the string is {@code null} or 0-length.
   * 
   * @param str
   *          The string to be examined.
   * @return {@code true} if the string is {@code null} or 0-length.
   */
  public static boolean isEmpty(CharSequence str) {
    return (str == null || str.length() == 0);
  }
}
