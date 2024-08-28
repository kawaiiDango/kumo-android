package com.kennycason.kumo.padding;


import com.kennycason.kumo.Word;
import com.kennycason.kumo.compat.KumoBitmap;
import com.kennycason.kumo.compat.KumoCanvas;
import com.kennycason.kumo.compat.KumoGraphicsFactory;


/**
 * Created by kenny on 7/2/14.
 */
public class RectanglePadder implements Padder {

    @Override
    public void pad(final Word word, final int padding, final KumoGraphicsFactory graphicsFactory) {
        if (padding <= 0) { return; }

        final KumoBitmap bufferedImage = word.getBufferedImage();
        final int width = bufferedImage.getWidth() + padding * 2;
        final int height = bufferedImage.getHeight() + padding * 2;

        final KumoBitmap newBufferedImage = graphicsFactory.createBitmap(width, height);
        final KumoCanvas graphics = graphicsFactory.createCanvas(newBufferedImage);
        graphics.drawBitmap(bufferedImage, padding, padding);

        bufferedImage.recycle();
        word.setBufferedImage(newBufferedImage);
    }

}
