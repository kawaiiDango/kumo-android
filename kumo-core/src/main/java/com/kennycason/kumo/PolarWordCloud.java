package com.kennycason.kumo;


import com.kennycason.kumo.compat.KumoGraphicsFactory;
import com.kennycason.kumo.compat.KumoPoint;
import com.kennycason.kumo.compat.KumoRect;
import com.kennycason.kumo.palette.ColorPalette;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by kenny on 6/29/14.
 */
public class PolarWordCloud extends WordCloud {
    private static final Random RANDOM = new Random();

    private static final ColorPalette DEFAULT_POSITIVE_COLORS = new ColorPalette(0xff1BE000, 0xff1AC902, 0xff15B000, 0xff129400, 0xff0F7A00, 0xff0B5E00);

    private static final ColorPalette DEFAULT_NEGATIVE_COLORS = new ColorPalette(0xffF50000, 0xffDE0000, 0xffC90202, 0xffB50202, 0xff990202, 0xff800101);

    private final PolarBlendMode polarBlendMode;

    private ColorPalette colorPalette2;

    public PolarWordCloud(final KumoRect dimension,
                          final CollisionMode collisionMode,
                          final KumoGraphicsFactory graphicsFactory
    ) {
        this(dimension, collisionMode, PolarBlendMode.EVEN, graphicsFactory);
        this.colorPalette = DEFAULT_POSITIVE_COLORS;
        this.colorPalette2 = DEFAULT_NEGATIVE_COLORS;
    }

    public PolarWordCloud(
            final KumoRect dimension,
            final CollisionMode collisionMode,
            final PolarBlendMode polarBlendMode,
            final KumoGraphicsFactory graphicsFactory
    ) {
        super(dimension, collisionMode, graphicsFactory);
        this.polarBlendMode = polarBlendMode;
        this.colorPalette = DEFAULT_POSITIVE_COLORS;
        this.colorPalette2 = DEFAULT_NEGATIVE_COLORS;
    }

    public void build(final List<WordFrequency> wordFrequencies, final List<WordFrequency> wordFrequencies2) {
        Collections.sort(wordFrequencies);
        Collections.sort(wordFrequencies2);

        final List<Word> words = buildWords(wordFrequencies, colorPalette);
        final List<Word> words2 = buildWords(wordFrequencies2, colorPalette2);

        final Iterator<Word> wordIterator = words.iterator();
        final Iterator<Word> wordIterator2 = words2.iterator();

        final KumoPoint[] poles = getRandomPoles();
        final KumoPoint pole1 = poles[0];
        final KumoPoint pole2 = poles[1];
        
        // the background masks all none usable pixels and we can only check this raster
        background.mask(backgroundCollidable);
        
        while (wordIterator.hasNext() || wordIterator2.hasNext()) {

            if (wordIterator.hasNext()) {
                final Word word = wordIterator.next();
                final KumoPoint startPosition = getStartPosition(pole1);

                place(word, startPosition);
            }
            if (wordIterator2.hasNext()) {
                final Word word = wordIterator2.next();
                final KumoPoint startPosition = getStartPosition(pole2);

                place(word, startPosition);
            }
        }

        drawForegroundToBackground();
    }

    private KumoPoint getStartPosition(final KumoPoint pole) {
        switch (polarBlendMode) {
            case BLUR:
                final int blurX = dimension.width() / 2;
                final int blurY = dimension.height() / 2;
                return new KumoPoint(
                    pole.x + -blurX + RANDOM.nextInt(blurX * 2),
                    pole.y + -blurY + RANDOM.nextInt(blurY * 2)
                );

            case EVEN:
                return pole;
        }
        throw new IllegalArgumentException("PolarBlendMode must not be null");
    }

    private KumoPoint[] getRandomPoles() {
        final KumoPoint[] max = new KumoPoint[2];
        double maxDistance = 0.0;
        for (int i = 0; i < 100; i++) {
            final int x = RANDOM.nextInt(dimension.width());
            final int y = RANDOM.nextInt(dimension.height());
            final int x2 = RANDOM.nextInt(dimension.width());
            final int y2 = RANDOM.nextInt(dimension.height());
            final double distance = Math.sqrt(Math.pow(x - x2, 2) + Math.pow(y - y2, 2));
            if (distance > maxDistance) {
                maxDistance = distance;
                max[0] = new KumoPoint(x, y);
                max[1] = new KumoPoint(x2, y2);
            }
        }
        return max;
    }

    public void setColorPalette2(final ColorPalette colorPalette2) {
        this.colorPalette2 = colorPalette2;
    }

}
