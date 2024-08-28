package com.kennycason.kumo.bg;

import com.kennycason.kumo.compat.KumoRect;

import com.kennycason.kumo.collide.RectanglePixelCollidable;
import com.kennycason.kumo.compat.KumoPoint;
import com.kennycason.kumo.image.CollisionRaster;


/**
 * A Background Collision Mode in the shape of a rectangle
 * 
 * @author kenny, wolfposd
 * @version 2015.11.26
 */
public class RectangleBackground implements Background {
    private static final KumoPoint ZERO = new KumoPoint(0, 0);

    private final KumoPoint position;
    
    private final KumoRect dimension;

    /**
     * Creates a rectangle background starting at (0|0) with specified width/height
     * @param dimension dimension of background
     */
    public RectangleBackground(final KumoRect dimension) {
        this(ZERO, dimension);
    }

    /**
     * Creates a rectangle background using {@link KumoPoint} and {@link KumoRect} for starting points and width/height
     * @param position the point where the rectangle lives on screen
     * @param dimension dimension of background
     */
    public RectangleBackground(final KumoPoint position, final KumoRect dimension) {
        this.position = position;
        this.dimension = dimension;
    }

    @Override
    public void mask(RectanglePixelCollidable background) {
        KumoRect dimensionOfShape = dimension;
        
        int minY = Math.max(position.y, 0);
        int minX = Math.max(position.x, 0);
        
        int maxY = dimensionOfShape.height() + position.y - 1;
        int maxX = dimensionOfShape.width() + position.x - 1;

        KumoRect dimensionOfBackground = background.getDimension();
        CollisionRaster rasterOfBackground = background.getCollisionRaster();
        
        for (int y = 0; y < dimensionOfBackground.height(); y++) {
            for (int x = 0; x < dimensionOfBackground.width(); x++) {
                if ((y < minY) || (y > maxY) || (x < minX) || (x > maxX)) {
                     rasterOfBackground.setPixelIsNotTransparent(x, y);
                }
            }
        }
    }
    
    @Override
    public String toString() {
        return "RectangleBackground [x=" + position.x + ", y=" + position.y +
                ", width=" + dimension.width() + ", height=" + dimension.height() + "]";
    }

}
