package com.kennycason.kumo.compat;

public interface KumoBitmap {
    int getWidth();
    int getHeight();
    void getPixels(int[] pixels, int offset, int stride, int x, int y, int width, int height);
    void recycle();
    // for converting to compose ImageBitmap etc
    Object convertTo();
}
