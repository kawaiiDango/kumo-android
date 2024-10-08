package com.kennycason.kumo.examples;

import android.graphics.Rect;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.KumoTestUtils;
import com.kennycason.kumo.KumoUtils;
import com.kennycason.kumo.LayeredWordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.PixelBoundaryBackground;
import com.kennycason.kumo.font.FontWeight;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.ColorPalette;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by kenny on 2/23/16.
 */
public class SimplyMeasuredWordCloudITest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimplyMeasuredWordCloudITest.class);

    @Test
    public void simplymeasured() throws IOException {
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(1000);
        frequencyAnalyzer.setMinWordLength(3);
        frequencyAnalyzer.setStopWords(loadStopWords());

        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(getInputStream("text/simplymeasured.txt"));
        final List<WordFrequency> wordFrequencies2 = frequencyAnalyzer.load(getInputStream("text/simplymeasured.txt"));
        final List<WordFrequency> wordFrequencies3 = frequencyAnalyzer.load(getInputStream("text/simplymeasured.txt"));

        final Rect dimension = new Rect(0, 0, 600, 454);
        final LayeredWordCloud layeredWordCloud = new LayeredWordCloud(4, dimension, CollisionMode.PIXEL_PERFECT);
        layeredWordCloud.setBackgroundColor(0x000000FF);

        layeredWordCloud.setPadding(0, 1);
        layeredWordCloud.setPadding(1, 1);
        layeredWordCloud.setPadding(2, 1);

        layeredWordCloud.setKumoFont(0, new KumoFont("Comic Sans MS", FontWeight.PLAIN));
        layeredWordCloud.setKumoFont(1, new KumoFont("Comic Sans MS", FontWeight.BOLD));
        layeredWordCloud.setKumoFont(2, new KumoFont("Comic Sans MS", FontWeight.ITALIC));

        layeredWordCloud.setBackground(0, new PixelBoundaryBackground(getInputStream("backgrounds/sm-logo-1.png")));
        layeredWordCloud.setBackground(1, new PixelBoundaryBackground(getInputStream("backgrounds/sm-logo-2.png")));
        layeredWordCloud.setBackground(2, new PixelBoundaryBackground(getInputStream("backgrounds/sm-logo-3.png")));

        layeredWordCloud.setColorPalette(0, new ColorPalette(0xff3f5ca9));
        layeredWordCloud.setColorPalette(1, new ColorPalette(0xff6185c3));
        layeredWordCloud.setColorPalette(2, new ColorPalette(0xff8fb6e1));

        layeredWordCloud.setFontScalar(0, new SqrtFontScalar(10, 40));
        layeredWordCloud.setFontScalar(1, new SqrtFontScalar(10, 40));
        layeredWordCloud.setFontScalar(2, new SqrtFontScalar(10, 40));

//        layeredWordCloud.setWordPlacer(0, new LinearWordPlacer());
//        layeredWordCloud.setWordPlacer(1, new LinearWordPlacer());
//        layeredWordCloud.setWordPlacer(2, new LinearWordPlacer());

        final long startTime = System.currentTimeMillis();
        layeredWordCloud.build(0, wordFrequencies);
        layeredWordCloud.build(1, wordFrequencies2);
        layeredWordCloud.build(2, wordFrequencies3);

        LOGGER.info("Took {}ms to build", System.currentTimeMillis() - startTime);
        layeredWordCloud.writeToFile(KumoTestUtils.prependDataDir("output/simplymeasured.png"));
    }

    private static Set<String> loadStopWords() throws IOException {
        return new HashSet<>(KumoUtils.readLines(getInputStream("text/stop_words.txt")));
    }

    private static InputStream getInputStream(final String path) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    }

}
