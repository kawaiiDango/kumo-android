package com.kennycason.kumo.collide;

import com.kennycason.kumo.compat.KumoRect;

import com.kennycason.kumo.compat.KumoPoint;
import com.kennycason.kumo.image.CollisionRaster;


/**
 * Created by kenny on 6/29/14.
 */
public interface Collidable {
    boolean collide(Collidable collidable);
    KumoPoint getPosition();
    KumoRect getDimension();
    CollisionRaster getCollisionRaster();
}
