package com.kennycason.kumo.collide.checkers;

import android.graphics.Point;

import com.kennycason.kumo.collide.Collidable;


/**
 * Created by kenny on 6/29/14.
 */
public class RectangleCollisionChecker implements CollisionChecker {

    @Override
    public boolean collide(final Collidable collidable, final Collidable collidable2) {
        final Point position = collidable.getPosition();
        final Point position2 = collidable2.getPosition();

        if ((position.x + collidable.getDimension().width() < position2.x)
                || (position2.x + collidable2.getDimension().width() < position.x)) {
            return false;
        }
        if ((position.y + collidable.getDimension().height() < position2.y)
                || (position2.y + collidable2.getDimension().height() < position.y)) {
            return false;
        }
        return true;
    }

}
