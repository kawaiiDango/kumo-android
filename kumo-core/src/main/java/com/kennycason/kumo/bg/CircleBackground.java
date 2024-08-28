package com.kennycason.kumo.bg;

import com.kennycason.kumo.compat.KumoRect;

import com.kennycason.kumo.collide.RectanglePixelCollidable;
import com.kennycason.kumo.compat.KumoPoint;
import com.kennycason.kumo.image.CollisionRaster;


/**
 * Created by kenny on 6/30/14.
 */
public class CircleBackground implements Background {

    private final int radius;

    private final KumoPoint position;

    public CircleBackground(final int radius) {
        this.radius = radius;
        this.position = new KumoPoint(0, 0);
    }
    
    @Override
    public void mask(RectanglePixelCollidable background) {
        KumoRect dimensionOfBackground = background.getDimension();
        CollisionRaster rasterOfBackground = background.getCollisionRaster();
        
        for (int y = 0; y < dimensionOfBackground.height(); y++) {
            for (int x = 0; x < dimensionOfBackground.width(); x++) {
                if (!inCircle(x, y)) {
                     rasterOfBackground.setPixelIsNotTransparent(
                             position.x + x, position.y + y
                     );
                }
            }
        }
    }

    private boolean inCircle(final int x, final int y) {
        final int centerX = position.x + x - radius;
        final int centerY = position.y + y - radius;
        return  (centerX * centerX) + (centerY * centerY) <= radius * radius;
    }

}
