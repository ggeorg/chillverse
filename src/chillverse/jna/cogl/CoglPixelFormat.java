package chillverse.jna.cogl;

public interface CoglPixelFormat {

  int COGL_A_BIT = (1 << 4);
  int COGL_BGR_BIT = (1 << 5);
  int COGL_AFIRST_BIT = (1 << 6);
  int COGL_PREMULT_BIT = (1 << 7);
  int COGL_DEPTH_BIT = (1 << 8);
  int COGL_STENCIL_BIT = (1 << 9);

  int COGL_PIXEL_FORMAT_ANY = 0;
  int COGL_PIXEL_FORMAT_A_8 = 1 | COGL_A_BIT;

  int COGL_PIXEL_FORMAT_RGB_565 = 4;
  int COGL_PIXEL_FORMAT_RGBA_4444 = 5 | COGL_A_BIT;
  int COGL_PIXEL_FORMAT_RGBA_5551 = 6 | COGL_A_BIT;
  int COGL_PIXEL_FORMAT_YUV = 7;
  int COGL_PIXEL_FORMAT_G_8 = 8;

  int COGL_PIXEL_FORMAT_RGB_888 = 2;
  int COGL_PIXEL_FORMAT_BGR_888 = (2 | COGL_BGR_BIT);

  int COGL_PIXEL_FORMAT_RGBA_8888 = (3 | COGL_A_BIT);
  int COGL_PIXEL_FORMAT_BGRA_8888 = (3 | COGL_A_BIT | COGL_BGR_BIT);
  int COGL_PIXEL_FORMAT_ARGB_8888 = (3 | COGL_A_BIT | COGL_AFIRST_BIT);
  int COGL_PIXEL_FORMAT_ABGR_8888 = (3 | COGL_A_BIT | COGL_BGR_BIT | COGL_AFIRST_BIT);

  int COGL_PIXEL_FORMAT_RGBA_1010102 = (13 | COGL_A_BIT);
  int COGL_PIXEL_FORMAT_BGRA_1010102 = (13 | COGL_A_BIT | COGL_BGR_BIT);
  int COGL_PIXEL_FORMAT_ARGB_2101010 = (13 | COGL_A_BIT | COGL_AFIRST_BIT);
  int COGL_PIXEL_FORMAT_ABGR_2101010 = (13 | COGL_A_BIT | COGL_BGR_BIT | COGL_AFIRST_BIT);

  int COGL_PIXEL_FORMAT_RGBA_8888_PRE = (3 | COGL_A_BIT | COGL_PREMULT_BIT);
  int COGL_PIXEL_FORMAT_BGRA_8888_PRE = (3 | COGL_A_BIT | COGL_PREMULT_BIT | COGL_BGR_BIT);
  int COGL_PIXEL_FORMAT_ARGB_8888_PRE = (3 | COGL_A_BIT | COGL_PREMULT_BIT | COGL_AFIRST_BIT);
  int COGL_PIXEL_FORMAT_ABGR_8888_PRE = (3 | COGL_A_BIT | COGL_PREMULT_BIT | COGL_BGR_BIT | COGL_AFIRST_BIT);
  int COGL_PIXEL_FORMAT_RGBA_4444_PRE = (COGL_PIXEL_FORMAT_RGBA_4444 | COGL_A_BIT | COGL_PREMULT_BIT);
  int COGL_PIXEL_FORMAT_RGBA_5551_PRE = (COGL_PIXEL_FORMAT_RGBA_5551 | COGL_A_BIT | COGL_PREMULT_BIT);

  int COGL_PIXEL_FORMAT_RGBA_1010102_PRE = (COGL_PIXEL_FORMAT_RGBA_1010102 | COGL_PREMULT_BIT);
  int COGL_PIXEL_FORMAT_BGRA_1010102_PRE = (COGL_PIXEL_FORMAT_BGRA_1010102 | COGL_PREMULT_BIT);
  int COGL_PIXEL_FORMAT_ARGB_2101010_PRE = (COGL_PIXEL_FORMAT_ARGB_2101010 | COGL_PREMULT_BIT);
  int COGL_PIXEL_FORMAT_ABGR_2101010_PRE = (COGL_PIXEL_FORMAT_ABGR_2101010 | COGL_PREMULT_BIT);

  int COGL_PIXEL_FORMAT_DEPTH_16 = (9 | COGL_DEPTH_BIT);
  int COGL_PIXEL_FORMAT_DEPTH_32 = (3 | COGL_DEPTH_BIT);

  int COGL_PIXEL_FORMAT_DEPTH_24_STENCIL_8 = (3 | COGL_DEPTH_BIT | COGL_STENCIL_BIT);

}
