package com.kennycason.kumo.padding;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.kennycason.kumo.Word;


/**
 * Created by kenny on 7/2/14.
 */
public class RectanglePadder implements Padder {

    @Override
    public void pad(final Word word, final int padding) {
        if (padding <= 0) { return; }

        final Bitmap bufferedImage = word.getBufferedImage();
        final int width = bufferedImage.getWidth() + padding * 2;
        final int height = bufferedImage.getHeight() + padding * 2;

        final Bitmap newBufferedImage = Bitmap.createBitmap(width, height, bufferedImage.getConfig());
        final Canvas graphics = new Canvas(newBufferedImage);
        graphics.drawBitmap(bufferedImage, padding, padding, null);

        bufferedImage.recycle();
        word.setBufferedImage(newBufferedImage);
    }

}
