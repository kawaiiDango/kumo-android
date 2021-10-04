package com.kennycason.kumo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
*Class to process image with a required size.
*
* the static method {@link #readImage(String fileName, int width, int height, String imageType)}
* can read the path of the file and resize the image
 */
public final class ImageProcessor {

    private ImageProcessor(){}
    private static ImageProcessor imageProcessor = new ImageProcessor();
    public static ImageProcessor getInstance(){
        return imageProcessor;
    }
    /**
    *@param fileName is the path of the file
     * @param imageType can define the type of image
     * @param width is the resized image's width
     * @param height is the resized image's height
     * @return the resized image in ByteArrayInputStream form
     */
    public static InputStream readImage(String fileName, int width, int height, String imageType) throws IOException {
        final Bitmap originImage = BitmapFactory.decodeStream(getInputStream(fileName));
        final Bitmap scaledImage = Bitmap.createScaledBitmap(originImage, width, height, false);
        final Bitmap bufferedImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);


        final Canvas graphics = new Canvas(bufferedImage);
        graphics.drawBitmap(scaledImage, 0, 0, null);


        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bufferedImage.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        originImage.recycle();
        scaledImage.recycle();
        bufferedImage.recycle();
        return new ByteArrayInputStream(outputStream.toByteArray());
    }
    
    private static InputStream getInputStream(final String path) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    }

}
