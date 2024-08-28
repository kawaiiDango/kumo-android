package com.kennycason.kumo;


import com.kennycason.kumo.bg.Background;
import com.kennycason.kumo.compat.KumoBitmap;
import com.kennycason.kumo.compat.KumoGraphicsFactory;
import com.kennycason.kumo.compat.KumoCanvas;
import com.kennycason.kumo.compat.KumoRect;
import com.kennycason.kumo.font.scale.FontScalar;
import com.kennycason.kumo.image.AngleGenerator;
import com.kennycason.kumo.palette.ColorPalette;
import com.kennycason.kumo.placement.RectangleWordPlacer;
import com.kennycason.kumo.wordstart.WordStartStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Created by kenny on 7/5/14.
 */
public class LayeredWordCloud {
    private final String TAG = "LayeredWordCloud";

    private final KumoRect dimension;

    private final List<WordCloud> wordClouds = new ArrayList<>();

    private int backgroundColor = 0xFF000000; // black

    public LayeredWordCloud(
            final int layers,
            final KumoRect dimension,
            final CollisionMode collisionMode,
            final KumoGraphicsFactory graphicsFactory

    ) {
        this.dimension = dimension;

        for (int i = 0; i < layers; i++) {
            final WordCloud wordCloud = new WordCloud(dimension, collisionMode, graphicsFactory);
            wordCloud.setBackgroundColor(0); // transparent
            wordClouds.add(wordCloud);
        }
    }

    public void build(final int layer, final List<WordFrequency> wordFrequencies) {
        wordClouds.get(layer).build(wordFrequencies);
    }

    public void setPadding(final int layer, final int padding) {
        this.wordClouds.get(layer).setPadding(padding);
    }

    public void setColorPalette(final int layer, final ColorPalette colorPalette) {
        this.wordClouds.get(layer).setColorPalette(colorPalette);
    }

    public void setBackground(final int layer, final Background background) {
        this.wordClouds.get(layer).setBackground(background);
    }

    public void setFontScalar(final int layer, final FontScalar fontScalar) {
        this.wordClouds.get(layer).setFontScalar(fontScalar);
    }

    public void setAngleGenerator(final int layer, final AngleGenerator angleGenerator) {
        this.wordClouds.get(layer).setAngleGenerator(angleGenerator);
    }

    public void setWordPlacer(final int layer, final RectangleWordPlacer wordPlacer) {
        this.wordClouds.get(layer).setWordPlacer(wordPlacer);
    }

    public void setBackgroundColor(final int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public KumoBitmap getBufferedImage() {
        WordCloud wordCloud0 = wordClouds.get(0);

        final KumoBitmap bufferedImage = wordCloud0.graphicsFactory.createBitmap(dimension.width(), dimension.height());
        final KumoCanvas graphics = wordCloud0.graphicsFactory.createCanvas(bufferedImage);
        graphics.drawColor(backgroundColor);

        for (final WordCloud wordCloud : wordClouds) {
            graphics.drawBitmap(wordCloud.getBufferedImage(), 0, 0);
        }

        return bufferedImage;
    }

    public WordCloud getLayer(final int layer) {
        return wordClouds.get(layer);
    }

    public WordCloud getAt(final int layer) {
        return getLayer(layer);
    }

    public Set<Word> getSkipped(final int layer) {
        return wordClouds.get(layer).getSkipped();
    }

    public void setWordStartStrategy(final int layer, final WordStartStrategy scheme) {
        wordClouds.get(layer).setWordStartStrategy(scheme);
    }

    public int getLayers() {
        return wordClouds.size();
    }

}
