package com.arn.kumoexample.kumo.compat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.kennycason.kumo.compat.KumoBitmap;
import com.kennycason.kumo.compat.KumoCanvas;

public class AndroidCanvas implements KumoCanvas {

    private final Canvas canvas;
    private static final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public AndroidCanvas(final Bitmap bitmap) {
        this.canvas = new Canvas(bitmap);
    }

    @Override
    public void drawBitmap(KumoBitmap bitmap, int left, int top) {
        canvas.drawBitmap(((AndroidBitmap) bitmap).getBitmap(), left, top, null);
    }

    @Override
    public void drawColor(int color) {
        canvas.drawColor(color);
    }

    @Override
    public void drawText(String text, int x, int y) {
        canvas.drawText(text, x, y, paint);
    }

    @Override
    public void rotate(float degrees, float x, float y) {
        canvas.rotate(degrees, x, y);
    }

    @Override
    public void translate(int x, int y) {
        canvas.translate(x, y);
    }

    @Override
    public int measureText(String text) {
        return (int) paint.measureText(text);
    }

    @Override
    public void setColor(int color) {
        paint.setColor(color);
    }

    @Override
    public void setTextSize(float textSize) {
        if (paint.getTextSize() == textSize)
            return;
        paint.setTextSize(textSize);
    }

    @Override
    public int getFontHeight() {
        return (int) paint.getFontSpacing();
    }

    @Override
    public int getDescent() {
        return (int) paint.getFontMetrics().descent;
    }

    @Override
    public int getLeading() {
        return (int) paint.getFontMetrics().leading;
    }

    @Override
    public float getTextSize() {
        return paint.getTextSize();
    }
}
