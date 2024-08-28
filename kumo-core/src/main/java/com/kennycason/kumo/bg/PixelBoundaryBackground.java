package com.kennycason.kumo.bg;


import com.kennycason.kumo.compat.KumoBitmap;
import com.kennycason.kumo.compat.KumoRect;

import com.kennycason.kumo.collide.RectanglePixelCollidable;
import com.kennycason.kumo.compat.KumoPoint;
import com.kennycason.kumo.image.CollisionRaster;

import java.io.IOException;

/**
 * Class creates a Background Mode based on the transparent Pixel-boundaries of a loaded image
 * @author kenny
 * @version 2014.06.30
 */
public class PixelBoundaryBackground implements Background {

    private final CollisionRaster collisionRaster;
    
    private final KumoPoint position = new KumoPoint(0, 0);


    public PixelBoundaryBackground(final KumoBitmap bufferedImage) throws IOException {
        this.collisionRaster = new CollisionRaster(bufferedImage);
    }
    
    @Override
    public void mask(final RectanglePixelCollidable background) {
        final KumoRect dimensionOfShape = collisionRaster.getDimension();
        final KumoRect dimensionOfBackground = background.getDimension();
        
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
