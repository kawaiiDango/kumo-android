package com.kennycason.kumo.collide;

import android.graphics.Point;
import android.graphics.Rect;

import com.kennycason.kumo.image.CollisionRaster;


/**
 * Created by kenny on 6/29/14.
 */
public interface Collidable {
    boolean collide(Collidable collidable);
    Point getPosition();
    Rect getDimension();
    CollisionRaster getCollisionRaster();
}
