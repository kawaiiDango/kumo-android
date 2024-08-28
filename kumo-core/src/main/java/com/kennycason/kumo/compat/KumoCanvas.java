package com.kennycason.kumo.compat;

public interface KumoCanvas {
    void drawBitmap(final KumoBitmap bitmap, final int left, final int top);
    void drawColor(final int color);
    void drawText(final String text, final int x, final int y);
    void rotate(final float degrees, final float x, final float y);
    void translate(final int x, final int y);
    int measureText(final String text);
    void setColor(final int color);
    void setTextSize(final float textSize);
    float getTextSize();
    int getFontHeight();
    int getDescent();
    int getLeading();
}
