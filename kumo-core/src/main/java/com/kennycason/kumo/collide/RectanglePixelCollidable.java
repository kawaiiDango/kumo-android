package com.kennycason.kumo.collide;

import com.kennycason.kumo.compat.KumoRect;

import com.kennycason.kumo.collide.checkers.RectanglePixelCollisionChecker;
import com.kennycason.kumo.compat.KumoPoint;
import com.kennycason.kumo.image.CollisionRaster;


/**
 * Created by kenny on 7/2/14.
 */
public class RectanglePixelCollidable implements Collidable {

    private static final RectanglePixelCollisionChecker RECTANGLE_PIXEL_COLLISION_CHECKER = new RectanglePixelCollisionChecker();

    private final KumoPoint position;

    private final CollisionRaster collisionRaster;

    public RectanglePixelCollidable(final CollisionRaster collisionRaster, final KumoPoint position) {
        this.collisionRaster = collisionRaster;
        this.position = position;
    }

    @Override
    public boolean collide(final Collidable collidable) {
        return RECTANGLE_PIXEL_COLLISION_CHECKER.collide(this, collidable);
    }

    @Override
    public KumoPoint getPosition() {
        return position;
    }

    @Override
    public KumoRect getDimension() {
        return collisionRaster.getDimension();
    }

    @Override
    public CollisionRaster getCollisionRaster() {
        return collisionRaster;
    }

}
