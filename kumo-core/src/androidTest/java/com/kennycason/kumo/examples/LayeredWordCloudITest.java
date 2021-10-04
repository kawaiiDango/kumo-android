package com.kennycason.kumo.examples;

import android.graphics.Color;
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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by kenny on 7/5/14.
 */
public class LayeredWordCloudITest {
    private static final Logger LOGGER = LoggerFactory.getLogger(LayeredWordCloudITest.class);

    @Test
    public void layeredExample() throws IOException {
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(300);
        frequencyAnalyzer.setMinWordLength(5);
        frequencyAnalyzer.setStopWords(loadStopWords());

        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(getInputStream("text/new_york_positive.txt"));
        final List<WordFrequency> wordFrequencies2 = frequencyAnalyzer.load(getInputStream("text/new_york_negative.txt"));
        final Rect dimension = new Rect(0, 0, 600, 386);
        final LayeredWordCloud layeredWordCloud = new LayeredWordCloud(2, dimension, CollisionMode.PIXEL_PERFECT);

        layeredWordCloud.setPadding(0, 1);
        layeredWordCloud.setPadding(1, 1);

        layeredWordCloud.setKumoFont(0, new KumoFont("LICENSE PLATE", FontWeight.BOLD));
        layeredWordCloud.setKumoFont(1, new KumoFont("Comic Sans MS", FontWeight.BOLD));

        layeredWordCloud.setBackground(0, new PixelBoundaryBackground(getInputStream("backgrounds/cloud_bg.bmp")));
        layeredWordCloud.setBackground(1, new PixelBoundaryBackground(getInputStream("backgrounds/cloud_fg.bmp")));

        layeredWordCloud.setColorPalette(0, new ColorPalette(0xffABEDFF, 0xff82E4FF, 0xff55D6FA));
        layeredWordCloud.setColorPalette(1, new ColorPalette(0xffFFFFFF, 0xffDCDDDE, 0xffCCCCCC));

        layeredWordCloud.setFontScalar(0, new SqrtFontScalar(10, 40));
        layeredWordCloud.setFontScalar(1, new SqrtFontScalar(10, 40));

        final long startTime = System.currentTimeMillis();
        layeredWordCloud.build(0, wordFrequencies);
        layeredWordCloud.build(1, wordFrequencies2);
        LOGGER.info("Took {}ms to build", System.currentTimeMillis() - startTime);
        layeredWordCloud.writeToFile(KumoTestUtils.prependDataDir("output/layered_word_cloud.png"));
    }

    @Test
    public void layeredHaskellExample() throws IOException {
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(300);
        frequencyAnalyzer.setMinWordLength(5);
        frequencyAnalyzer.setStopWords(loadStopWords());

        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(getInputStream("text/haskell_hate.txt"));
        final List<WordFrequency> wordFrequencies2 = frequencyAnalyzer.load(getInputStream("text/haskell_love.txt"));
        final Rect dimension = new Rect(0, 0, 600, 424);
        final LayeredWordCloud layeredWordCloud = new LayeredWordCloud(2, dimension, CollisionMode.PIXEL_PERFECT);
        layeredWordCloud.setBackgroundColor(Color.WHITE);

        layeredWordCloud.setPadding(0, 1);
        layeredWordCloud.setPadding(1, 1);

        layeredWordCloud.setKumoFont(0, new KumoFont("LICENSE PLATE", FontWeight.BOLD));
        layeredWordCloud.setKumoFont(1, new KumoFont("Comic Sans MS", FontWeight.BOLD));

        layeredWordCloud.setBackground(0, new PixelBoundaryBackground(getInputStream("backgrounds/haskell_1.bmp")));
        layeredWordCloud.setBackground(1, new PixelBoundaryBackground(getInputStream("backgrounds/haskell_2.bmp")));

        layeredWordCloud.setColorPalette(0, new ColorPalette(0xffFA6C07, 0xffFF7614, 0xffFF8936));
        layeredWordCloud.setColorPalette(1, new ColorPalette(0xff080706, 0xff3B3029, 0xff47362A));

        layeredWordCloud.setFontScalar(0, new SqrtFontScalar(10, 40));
        layeredWordCloud.setFontScalar(1, new SqrtFontScalar(10, 40));

        final long startTime = System.currentTimeMillis();
        layeredWordCloud.build(0, wordFrequencies);
        layeredWordCloud.build(1, wordFrequencies2);
        LOGGER.info("Took {}ms to build", System.currentTimeMillis() - startTime);
        layeredWordCloud.writeToFile(KumoTestUtils.prependDataDir("output/layered_haskell.png"));
    }

    @Test
    public void layeredPhoBowl() throws IOException {
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(1000);
        frequencyAnalyzer.setMinWordLength(3);
        frequencyAnalyzer.setStopWords(loadStopWords());

        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(getInputStream("text/pho_history.txt"));
        final List<WordFrequency> wordFrequencies2 = frequencyAnalyzer.load(getInputStream("text/pho_history_viet.txt"));
        final List<WordFrequency> wordFrequencies3 = frequencyAnalyzer.load(getInputStream("text/pho_recipee.txt"));
        final List<WordFrequency> wordFrequencies4 = frequencyAnalyzer.load(getInputStream("text/pho_chopsticks.txt"));

        final Rect dimension = new Rect(0, 0, 1000, 976);
        final LayeredWordCloud layeredWordCloud = new LayeredWordCloud(4, dimension, CollisionMode.PIXEL_PERFECT);
        layeredWordCloud.setBackgroundColor(0xff333333);

        layeredWordCloud.setPadding(0, 1);
        layeredWordCloud.setPadding(1, 1);
        layeredWordCloud.setPadding(2, 1);
        layeredWordCloud.setPadding(3, 1);

        layeredWordCloud.setKumoFont(0, new KumoFont("Comic Sans MS", FontWeight.PLAIN));
        layeredWordCloud.setKumoFont(1, new KumoFont("Comic Sans MS", FontWeight.BOLD));
        layeredWordCloud.setKumoFont(2, new KumoFont("Comic Sans MS", FontWeight.ITALIC));
        layeredWordCloud.setKumoFont(3, new KumoFont("Comic Sans MS", FontWeight.BOLD));

        layeredWordCloud.setBackground(0, new PixelBoundaryBackground(getInputStream("backgrounds/pho_1.bmp")));
        layeredWordCloud.setBackground(1, new PixelBoundaryBackground(getInputStream("backgrounds/pho_2.bmp")));
        layeredWordCloud.setBackground(2, new PixelBoundaryBackground(getInputStream("backgrounds/pho_3.bmp")));
        layeredWordCloud.setBackground(3, new PixelBoundaryBackground(getInputStream("backgrounds/pho_4.bmp")));

        layeredWordCloud.setColorPalette(0, new ColorPalette(0xff26A621, 0xff21961D, 0xff187A15));
        layeredWordCloud.setColorPalette(1, new ColorPalette(0xff5963F0, 0xff515CF0, 0xff729FED));
        layeredWordCloud.setColorPalette(2, new ColorPalette(0xffEDC672, 0xffDBB258, 0xffDE7C1B));
        layeredWordCloud.setColorPalette(3, new ColorPalette(0xff70572B, 0xff857150, 0xffB09971));

        layeredWordCloud.setFontScalar(0, new SqrtFontScalar(8, 30));
        layeredWordCloud.setFontScalar(1, new SqrtFontScalar(8, 40));
        layeredWordCloud.setFontScalar(2, new SqrtFontScalar(8, 30));
        layeredWordCloud.setFontScalar(3, new SqrtFontScalar(8, 30));

        final long startTime = System.currentTimeMillis();
        layeredWordCloud.build(0, wordFrequencies);
        layeredWordCloud.build(1, wordFrequencies2);
        layeredWordCloud.build(2, wordFrequencies3);
        layeredWordCloud.build(3, wordFrequencies4);

        LOGGER.info("Took {}ms to build", System.currentTimeMillis() - startTime);
        layeredWordCloud.writeToFile(KumoTestUtils.prependDataDir("output/layered_pho_bowl.png"));
    }

    private static Set<String> loadStopWords() {
        try {
            final List<String> lines = KumoUtils.readLines(getInputStream("text/stop_words.txt"));
            return new HashSet<>(lines);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return Collections.emptySet();
    }

    private static InputStream getInputStream(final String path) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    }

}
