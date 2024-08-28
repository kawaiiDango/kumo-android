package com.kennycason.kumo.image;

import com.kennycason.kumo.compat.KumoBitmap;
import com.kennycason.kumo.compat.KumoRect;

import com.kennycason.kumo.compat.KumoPoint;

import java.util.BitSet;

/**
 * Created by kenny on 7/4/14.
 */
public class CollisionRaster {

    private final BitSet data;
    private final KumoRect dimension;

    public CollisionRaster(final KumoBitmap bufferedImage) {
        this(new KumoRect(bufferedImage.getWidth(), bufferedImage.getHeight()));

        int width = dimension.width();
        int height = dimension.height();
        int[] pixels = new int[width * height];
        bufferedImage.getPixels(pixels, 0, width, 0, 0, width, height);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = pixels[y * width + x];
                final boolean pixelIsTransparent = (pixel & 0xFF000000) == 0x00000000;

                if (!pixelIsTransparent) {
                    setPixelIsNotTransparent(x, y);
                }
            }
        }
    }

    public CollisionRaster(final KumoRect dimension) {
        this.dimension = dimension;

        data = new BitSet(dimension.width() * dimension.height());
    }

    public CollisionRaster(CollisionRaster other) {
        this.dimension = other.dimension;
        this.data = (BitSet) other.data.clone();
    }

    private int computeIndex(final int x, final int y) {
        if (x < 0 || x >= dimension.width()) {
            throw new IllegalArgumentException("x is out of bounds");
        } else if (y < 0 || y >= dimension.height()) {
            throw new IllegalArgumentException("y is out of bounds");
        }
        
        return (y * dimension.width()) + x;
    }

    public final void setPixelIsNotTransparent(final int x, final int y) {
        data.set(computeIndex(x, y));
    }

    public void mask(final CollisionRaster collisionRaster, final KumoPoint point) {
        final int maxHeight = Math.min(point.y + collisionRaster.getDimension().height(), dimension.height());
        final int maxWidth = Math.min(point.x + collisionRaster.getDimension().width(), dimension.width());
        
        for (int offY = point.y, offY2 = 0; offY < maxHeight; offY++, offY2++) {
            // we can't set the "line is not transparent" flag here, 
            // the maxWidth might be smaller than the collisionRaster's width
            for (int offX = point.x, offX2 = 0; offX < maxWidth; offX++, offX2++) {
                if (!collisionRaster.isTransparent(offX2, offY2)) {
                    setPixelIsNotTransparent(offX, offY);
                }
            }
        }
    }
    
    /**
     * @param minX (inclusive) start of the pixels to test 
     * @param maxX (exclusive) end of the pixels to test 
     * @param y the line to check
     */
    public int nextNotTransparentPixel(final int minX, final int maxX, int y) {
        if (maxX > dimension.width()) {
            throw new IllegalArgumentException("maxX is out of bounds");
        }
        
        int idx = computeIndex(minX, y);
        int set = data.nextSetBit(idx);

        if (set != -1 && set < idx + maxX - minX) {
            return (set - idx) + minX;
        } else {
            return -1;
        }
    }

    public boolean isTransparent(final int x, final int y) {
        return !data.get(computeIndex(x, y));
    }

    public KumoRect getDimension() {
        return dimension;
    }
    
}
