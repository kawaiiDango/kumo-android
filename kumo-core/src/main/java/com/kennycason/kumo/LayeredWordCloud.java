package com.kennycason.kumo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

import com.kennycason.kumo.bg.Background;
import com.kennycason.kumo.exception.KumoException;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.FontScalar;
import com.kennycason.kumo.image.AngleGenerator;
import com.kennycason.kumo.palette.ColorPalette;
import com.kennycason.kumo.placement.RectangleWordPlacer;
import com.kennycason.kumo.wordstart.WordStartStrategy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import timber.log.Timber;

/**
 * Created by kenny on 7/5/14.
 */
public class LayeredWordCloud {
    private final String TAG = "LayeredWordCloud";

    private final Rect dimension;

    private final List<WordCloud> wordClouds = new ArrayList<>();

    private int backgroundColor = Color.BLACK;

    public LayeredWordCloud(final int layers, final Rect dimension, final CollisionMode collisionMode) {
        this.dimension = dimension;

        for (int i = 0; i < layers; i++) {
            final WordCloud wordCloud = new WordCloud(dimension, collisionMode);
            wordCloud.setBackgroundColor(Color.TRANSPARENT);
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

    public void setKumoFont(final int layer, final KumoFont kumoFont) {
        this.wordClouds.get(layer).setKumoFont(kumoFont);
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

    public Bitmap getBufferedImage() {
        final Bitmap bufferedImage = Bitmap.createBitmap(dimension.width(), dimension.height(), Bitmap.Config.ARGB_8888);
        final Canvas graphics = new Canvas(bufferedImage);
        graphics.drawColor(backgroundColor);

        for (final WordCloud wordCloud : wordClouds) {
            graphics.drawBitmap(wordCloud.getBufferedImage(), 0, 0, null);
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

    public void writeToFile(final String outputFileName) {
        String extension = "";
        final int i = outputFileName.lastIndexOf('.');
        if (i > 0) {
            extension = outputFileName.substring(i + 1);
        }
        try {
            Timber.tag(TAG).i("Saving Layered WordCloud to: %s", outputFileName);
            getBufferedImage().compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(outputFileName));

        } catch (final IOException e) {
            throw new KumoException(e);
        }
    }
    
    public void setWordStartStrategy(final int layer, final WordStartStrategy scheme) {
        wordClouds.get(layer).setWordStartStrategy(scheme);
    }
    
    public int getLayers() {
        return wordClouds.size();
    }

}
