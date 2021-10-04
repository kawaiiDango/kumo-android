package com.kennycason.kumo.image;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by kenny on 6/29/14.
 */
public class ImageRotation {

    private ImageRotation() {}

    public static Bitmap rotate90(final Bitmap bufferedImage) {
        return rotate(bufferedImage, Math.toRadians(90));
    }

    public static Bitmap rotateMinus90(final Bitmap bufferedImage) {
        return rotate(bufferedImage, Math.toRadians(-90));
    }

    public static Bitmap rotate(final Bitmap bufferedImage, final double theta) {
        if (theta == 0.0) { return bufferedImage; }

        final double sin = Math.abs(Math.sin(theta));
        final double cos = Math.abs(Math.cos(theta));
        final int weight = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        final float newWeight = (float) Math.floor(weight * cos + height * sin);
        final float newHeight = (float) Math.floor(height * cos + weight * sin);

        final Bitmap result = Bitmap.createBitmap((int)newWeight, (int)newHeight, bufferedImage.getConfig());
        final Canvas graphics = new Canvas(result);

        graphics.rotate((float)Math.toDegrees(theta), newWeight / 2, newHeight / 2);
        graphics.translate((newWeight - weight) / 2, (newHeight - height) / 2);
        graphics.drawBitmap(bufferedImage, 0, 0, null);

        bufferedImage.recycle();

        return result;
    }

}
