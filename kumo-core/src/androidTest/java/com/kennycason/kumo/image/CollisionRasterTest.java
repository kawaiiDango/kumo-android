package com.kennycason.kumo.image;

import com.kennycason.kumo.collide.Collidable;
import com.kennycason.kumo.collide.checkers.CollisionChecker;
import com.kennycason.kumo.collide.checkers.RectanglePixelCollisionChecker;
import com.kennycason.kumo.compat.KumoPoint;

import org.junit.Test;

import org.junit.Assert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by kenny on 7/4/14.
 */
public class CollisionRasterTest {

    @Test
    public void basicTests() {
        final int width = 10;
        final int height = 5;
        final Rect dimension = new Rect(0, 0, width, height);
        final CollisionRaster collisionRaster = new CollisionRaster(dimension);

        assertEquals(width, collisionRaster.getDimension().width());
        assertEquals(height, collisionRaster.getDimension().height());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                assertTrue(collisionRaster.isTransparent(x, y));
            }
        }
    }

    /**
     * this test ensures setting one pixel doen't affect another pixel and not
     * other line.
     */
    @Test
    public void correctPixelIsTransparent() {
        final int width = 90;
        final int height = 30;

        for (int ySet = 0; ySet < height; ySet++) {
            for (int xSet = 0; xSet < width; xSet++) {
                final Rect dimension = new Rect(0, 0, width, height);
                final CollisionRaster collisionRaster = new CollisionRaster(dimension);

                collisionRaster.setPixelIsNotTransparent(xSet, ySet);

                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        if (x == xSet && y == ySet) {
                            assertFalse(collisionRaster.isTransparent(x, y));
                        } else {
                            assertTrue(collisionRaster.isTransparent(x, y));
                        }
                    }
                }
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void illegalX() {
        final Rect dimension = new Rect(0, 0, 90, 60);
        final CollisionRaster collisionRaster = new CollisionRaster(dimension);
        collisionRaster.setPixelIsNotTransparent(100, 0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void illegalY() {
        final Rect dimension = new Rect(0, 0, 90, 60);
        final CollisionRaster collisionRaster = new CollisionRaster(dimension);
        collisionRaster.setPixelIsNotTransparent(0, 70);
    }
    
    @Test
    public void collisionsAreFound() {
        CollisionChecker checker = new RectanglePixelCollisionChecker();

        final Rect dimension = new Rect(0, 0, 90, 60);
        final CollisionRaster collisionRasterA = new CollisionRaster(dimension);
        final CollisionRaster collisionRasterB = new CollisionRaster(dimension);

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                collisionRasterA.setPixelIsNotTransparent(20 + x, 20 + y);
                collisionRasterB.setPixelIsNotTransparent(30 + x, 30 + y);
            }
        }

        Assert.assertFalse(checker.collide(
                collidable(new KumoPoint(30, 30), dimension, collisionRasterA),
                collidable(new KumoPoint(30, 30), dimension, collisionRasterB)
        ));
        
        Assert.assertTrue(checker.collide(
                collidable(new KumoPoint(30, 30), dimension, collisionRasterA),
                collidable(new KumoPoint(20, 20), dimension, collisionRasterB)
        ));

        Assert.assertFalse(checker.collide(
                collidable(new KumoPoint(20, 20), dimension, collisionRasterA),
                collidable(new KumoPoint(30, 30), dimension, collisionRasterB)
        ));
    }

    Collidable collidable(KumoPoint p, Rect d, CollisionRaster r) {
        return new Collidable() {
            @Override
            public boolean collide(Collidable collidable) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public KumoPoint getPosition() {
                return p;
            }

            @Override
            public Rect getDimension() {
                return d;
            }

            @Override
            public CollisionRaster getCollisionRaster() {
                return r;
            }
        };
    }

}
