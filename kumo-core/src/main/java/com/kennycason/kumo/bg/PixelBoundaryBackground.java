package com.kennycason.kumo.bg;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;

import com.kennycason.kumo.collide.RectanglePixelCollidable;
import com.kennycason.kumo.image.CollisionRaster;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Class creates a Background Mode based on the transparent Pixel-boundaries of a loaded image
 * @author kenny
 * @version 2014.06.30
 */
public class PixelBoundaryBackground implements Background {

    private final CollisionRaster collisionRaster;
    
    private final Point position = new Point(0, 0);

    /**
     * Creates a PixelBoundaryBackground using an InputStream to load an image
     *
     * @param imageInputStream InputStream containing an image file
     * @throws IOException when fails to open file stream
     */
    public PixelBoundaryBackground(final InputStream imageInputStream) throws IOException {
        final Bitmap bufferedImage = BitmapFactory.decodeStream(imageInputStream);
        this.collisionRaster = new CollisionRaster(bufferedImage);
    }

    /**
     * Creates a PixelBoundaryBackground using an image from the input file
     *
     * @param file a File pointing to an image
     * @throws IOException when fails to open file stream
     */
    public PixelBoundaryBackground(final File file) throws IOException {
        this(new FileInputStream(file));
    }

    /**
     * Creates a PixelBoundaryBackground using an image-path
     *
     * @param filepath path to an image file
     * @throws IOException when fails to open file stream
     */
    public PixelBoundaryBackground(final String filepath) throws IOException {
        this(new File(filepath));
    }
    
    @Override
    public void mask(final RectanglePixelCollidable background) {
        final Rect dimensionOfShape = collisionRaster.getDimension();
        final Rect dimensionOfBackground = background.getDimension();
        
        final int minY = Math.max(position.y, 0);
        final int minX = Math.max(position.x, 0);

        final int maxY = dimensionOfShape.height() - position.y - 1;
        final int maxX = dimensionOfShape.width() - position.x - 1;

        final CollisionRaster rasterOfBackground = background.getCollisionRaster();

        for (int y = 0; y < dimensionOfBackground.height(); y++) {
            if (y < minY || y > maxY) {
                for (int x = 0; x < dimensionOfBackground.width(); x++) {
                    rasterOfBackground.setPixelIsNotTransparent(position.x + x, position.y + y);
                }
            } else {
                for (int x = 0; x < dimensionOfBackground.width(); x++) {
                    if (x < minX || x > maxX || collisionRaster.isTransparent(x, y)) {
                        rasterOfBackground.setPixelIsNotTransparent(position.x + x, position.y + y);
                    }
                }
            }

        }
    }
}
