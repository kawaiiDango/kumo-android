package com.kennycason.kumo.wordstart;

import com.kennycason.kumo.compat.KumoRect;

import com.kennycason.kumo.Word;
import com.kennycason.kumo.compat.KumoPoint;

import java.util.Random;

/**
 * Always returns a random point in the image as starting point
 * 
 * @author &#64;wolfposd
 */
public class RandomWordStart implements WordStartStrategy {

    private static final Random RANDOM = new Random();

    @Override
    public KumoPoint getStartingPoint(final KumoRect dimension, final Word word) {
        final int startX = RANDOM.nextInt(Math.max(dimension.width() - word.getDimension().width(), dimension.width()));
        final int startY = RANDOM.nextInt(Math.max(dimension.height() - word.getDimension().height(), dimension.height()));

        return new KumoPoint(startX, startY);
    }

}
