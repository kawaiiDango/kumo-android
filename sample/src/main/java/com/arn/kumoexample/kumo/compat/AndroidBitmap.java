package com.arn.kumoexample.kumo.compat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.kennycason.kumo.compat.KumoBitmap;

import java.io.InputStream;

public class AndroidBitmap implements KumoBitmap {
    private final Bitmap bitmap;

    public AndroidBitmap(int width, int height) {
        this.bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    }

    public AndroidBitmap(final InputStream inputStream) {
        this.bitmap = BitmapFactory.decodeStream(inputStream);
    }

    @Override
    public int getWidth() {
        return bitmap.getWidth();
    }

    @Override
    public int getHeight() {
        return bitmap.getHeight();
    }

    @Override
    public void getPixels(final int[] pixels, final int offset, final int stride, final int x, final int y, final int width, final int height) {
        bitmap.getPixels(pixels, offset, stride, x, y, width, height);
    }

    @Override
    public void recycle() {
        bitmap.recycle();
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public Object convertTo() {
        return bitmap;
    }
}
