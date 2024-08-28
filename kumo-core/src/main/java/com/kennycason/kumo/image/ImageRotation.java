package com.kennycason.kumo.image;


import com.kennycason.kumo.compat.KumoBitmap;
import com.kennycason.kumo.compat.KumoGraphicsFactory;
import com.kennycason.kumo.compat.KumoCanvas;

/**
 * Created by kenny on 6/29/14.
 */
public class ImageRotation {

    private ImageRotation() {}

    public static KumoBitmap rotate(
            final KumoBitmap bufferedImage,
            KumoGraphicsFactory graphicsFactory,
            final double theta
    ) {
        if (theta == 0.0) { return bufferedImage; }

        final double sin = Math.abs(Math.sin(theta));
        final double cos = Math.abs(Math.cos(theta));
        final int weight = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        final float newWeight = (float) Math.floor(weight * cos + height * sin);
        final float newHeight = (float) Math.floor(height * cos + weight * sin);

        final KumoBitmap result = graphicsFactory.createBitmap((int)newWeight, (int)newHeight);
        final KumoCanvas graphics = graphicsFactory.createCanvas(result);

        graphics.rotate((float)Math.toDegrees(theta), newWeight / 2, newHeight / 2);
        graphics.translate((int) ((newWeight - weight) / 2), (int) ((newHeight - height) / 2));
        graphics.drawBitmap(bufferedImage, 0, 0);

        bufferedImage.recycle();

        return result;
    }

}
