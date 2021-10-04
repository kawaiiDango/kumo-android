package com.kennycason.kumo.examples;

import android.graphics.Color;
import android.graphics.Rect;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.KumoTestUtils;
import com.kennycason.kumo.KumoUtils;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.bg.PixelBoundaryBackground;
import com.kennycason.kumo.bg.RectangleBackground;
import com.kennycason.kumo.font.FontWeight;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.LinearFontScalar;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.image.AngleGenerator;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.ColorPalette;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.List;

/**
 * Created by kenny on 6/29/14.
 */
public class WordCloudITest {
    private static final Logger LOGGER = LoggerFactory.getLogger(WordCloudITest.class);

    private static final Random RANDOM = new Random();

    @Test
    public void simpleCircleTest() throws IOException {
        final List<WordFrequency> wordFrequencies = buildWordFrequencies().subList(0, 150);
        final Rect dimension = new Rect(0, 0, 600, 600);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.RECTANGLE);
        wordCloud.setPadding(0);
        wordCloud.setBackground(new CircleBackground(300));
        wordCloud.setColorPalette(buildRandomColorPalette(20));
        wordCloud.setFontScalar(new LinearFontScalar(10, 40));
        wordCloud.build(wordFrequencies);
        wordCloud.writeToFile(KumoTestUtils.prependDataDir("output/wordcloud_circle.png"));
    }


    @Test
    public void simpleRectangleTest() throws IOException {
        final List<WordFrequency> wordFrequencies = buildWordFrequencies().subList(0, 150);
        final Rect dimension = new Rect(0, 0, 600, 600);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.RECTANGLE);
        wordCloud.setPadding(0);
        wordCloud.setBackground(new RectangleBackground(dimension));
        wordCloud.setColorPalette(buildRandomColorPalette(20));
        wordCloud.setFontScalar(new LinearFontScalar(10, 40));
        wordCloud.build(wordFrequencies);
        wordCloud.writeToFile(KumoTestUtils.prependDataDir("output/wordcloud_rectangle2.png"));
    }

    @Test
    public void loadWikipediaFromUrl() throws IOException {
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(1000);
        frequencyAnalyzer.setMinWordLength(1);

        final List<WordFrequency> wordFrequencies = KumoTestUtils.loadFreqFromUrl(frequencyAnalyzer, new URL("https://en.wikipedia.org/wiki/Main_Page"));
        final Rect dimension = new Rect(0, 0, 1000, 1000);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(1);
        wordCloud.setBackground(new RectangleBackground(dimension));
        wordCloud.setKumoFont(new KumoFont("Impact", FontWeight.PLAIN));
        // wordCloud.setAngleGenerator(new AngleGenerator(-60, 60, 5));
        wordCloud.setColorPalette(buildRandomColorPalette(5));
        wordCloud.setFontScalar(new LinearFontScalar(18, 70));
        wordCloud.build(wordFrequencies);
        wordCloud.writeToFile(KumoTestUtils.prependDataDir("output/wikipedia.png"));
    }

    @Test
    public void readCNN() throws IOException {
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(500);
        frequencyAnalyzer.setMinWordLength(3);

        final List<WordFrequency> wordFrequencies = KumoTestUtils.loadFreqFromUrl(frequencyAnalyzer, new URL("https://www.cnn.com/"));
        final Rect dimension = new Rect(0, 0, 600, 600);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(1);
        wordCloud.setBackground(new RectangleBackground(dimension));
        wordCloud.setKumoFont(new KumoFont("Impact", FontWeight.PLAIN));
        // wordCloud.setAngleGenerator(new AngleGenerator(-60, 60, 5));
        wordCloud.setColorPalette(buildRandomColorPalette(5));
        wordCloud.setFontScalar(new LinearFontScalar(18, 70));
        wordCloud.build(wordFrequencies);
        wordCloud.writeToFile(KumoTestUtils.prependDataDir("output/cnn.png"));
    }


    @Test
    public void whaleImgLargeTest() throws IOException {
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(600);
        frequencyAnalyzer.setMinWordLength(5);
        frequencyAnalyzer.setStopWords(loadStopWords());

        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(getInputStream("text/datarank.txt"));
        final Rect dimension = new Rect(0, 0, 990, 618);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(1);
        wordCloud.setBackgroundColor(Color.WHITE);
        wordCloud.setBackground(new PixelBoundaryBackground(getInputStream("backgrounds/whale.png")));
        wordCloud.setKumoFont(new KumoFont("Impact", FontWeight.PLAIN));
        wordCloud.setColorPalette(new ColorPalette(0xff4055F1, 0xff408DF1, 0xff40AAF1, 0xff40C5F1, 0xff40D3F1, 0xff000000));
        wordCloud.setFontScalar(new SqrtFontScalar(10, 50));
        wordCloud.build(wordFrequencies);
        wordCloud.writeToFile(KumoTestUtils.prependDataDir("output/whale_wordcloud_large_impact.png"));
    }

    @Test
    public void whaleImgSmallTest() throws IOException {
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(300);
        frequencyAnalyzer.setMinWordLength(5);
        frequencyAnalyzer.setStopWords(loadStopWords());

        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(getInputStream("text/datarank.txt"));
        final Rect dimension = new Rect(0, 0, 500, 312);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(1);
        wordCloud.setBackground(new PixelBoundaryBackground(getInputStream("backgrounds/whale_small.png")));
        wordCloud.setColorPalette(new ColorPalette(0xff4055F1, 0xff408DF1, 0xff40AAF1, 0xff40C5F1, 0xff40D3F1, 0xffFFFFFF));
        wordCloud.setFontScalar(new LinearFontScalar(10, 40));
        wordCloud.build(wordFrequencies);
        wordCloud.writeToFile(KumoTestUtils.prependDataDir("output/whale_wordcloud_small.png"));
    }

    @Test
    public void whaleImgSmallAnglesTest() throws IOException {
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(100);
        frequencyAnalyzer.setMinWordLength(5);
        frequencyAnalyzer.setStopWords(loadStopWords());

        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(getInputStream("text/datarank.txt"));
        final Rect dimension = new Rect(0, 0, 500, 312);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(2);
        wordCloud.setAngleGenerator(new AngleGenerator(-90, 90, 10));
        wordCloud.setBackground(new PixelBoundaryBackground(getInputStream("backgrounds/whale_small.png")));
        wordCloud.setColorPalette(new ColorPalette(0xff4055F1, 0xff408DF1, 0xff40AAF1, 0xff40C5F1, 0xff40D3F1, 0xffFFFFFF));
        wordCloud.setFontScalar(new LinearFontScalar(10, 30));
        wordCloud.build(wordFrequencies);
        wordCloud.writeToFile(KumoTestUtils.prependDataDir("output/whale_wordcloud_small_angles4.png"));
    }

    @Test
    public void whaleImgLargeAnglesTest() throws IOException {
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(600);
        frequencyAnalyzer.setMinWordLength(5);
        frequencyAnalyzer.setStopWords(loadStopWords());

        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(getInputStream("text/datarank.txt"));
        final Rect dimension = new Rect(0, 0, 990, 618);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(2);
        wordCloud.setAngleGenerator(new AngleGenerator(-90, 90, 10));
        wordCloud.setBackground(new PixelBoundaryBackground(getInputStream("backgrounds/whale.png")));
        wordCloud.setColorPalette(new ColorPalette(0xff4055F1, 0xff408DF1, 0xff40AAF1, 0xff40C5F1, 0xff40D3F1, 0xffFFFFFF));
        wordCloud.setFontScalar(new LinearFontScalar(20, 50));
        wordCloud.build(wordFrequencies);
        wordCloud.writeToFile(KumoTestUtils.prependDataDir("output/whale_wordcloud_large_angles2.png"));
    }

    @Test
    public void datarankCircle() throws IOException {
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(750);
        frequencyAnalyzer.setMinWordLength(5);
        frequencyAnalyzer.setStopWords(loadStopWords());

        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(getInputStream("text/datarank.txt"));
        final Rect dimension = new Rect(0, 0, 600, 600);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(2);
        wordCloud.setBackground(new CircleBackground(300));
        wordCloud.setColorPalette(new ColorPalette(0xff4055F1, 0xff408DF1, 0xff40AAF1, 0xff40C5F1, 0xff40D3F1, 0xffFFFFFF));
        wordCloud.setFontScalar(new SqrtFontScalar(10, 40));
        final long startTime = System.currentTimeMillis();
        wordCloud.build(wordFrequencies);
        LOGGER.info("Took {}ms to build", System.currentTimeMillis() - startTime);
        wordCloud.writeToFile(KumoTestUtils.prependDataDir("output/datarank_wordcloud_circle_sqrt_font.png"));
    }

    @Test
    public void datarankCircleLarge() throws IOException {
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(750);
        frequencyAnalyzer.setMinWordLength(5);
        frequencyAnalyzer.setStopWords(loadStopWords());

        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(getInputStream("text/datarank.txt"));
        final Rect dimension = new Rect(0, 0, 1000, 1000);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(2);
        wordCloud.setBackground(new CircleBackground(500));
        wordCloud.setColorPalette(new ColorPalette(0xff4055F1, 0xff408DF1, 0xff40AAF1, 0xff40C5F1, 0xff40D3F1, 0xffFFFFFF));
        wordCloud.setFontScalar(new SqrtFontScalar(10, 50));
        final long startTime = System.currentTimeMillis();
        wordCloud.build(wordFrequencies);
        LOGGER.info("Took {}ms to build", System.currentTimeMillis() - startTime);
        wordCloud.writeToFile(KumoTestUtils.prependDataDir("output/datarank_wordcloud_circle_large2.png"));
    }

    @Test
    public void datarankEarthImage() throws IOException {
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(300);
        frequencyAnalyzer.setMinWordLength(5);
        frequencyAnalyzer.setStopWords(loadStopWords());

        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(getInputStream("text/datarank.txt"));
        final Rect dimension = new Rect(0, 0, 600, 600);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(2);
        wordCloud.setKumoFont(new KumoFont("Simple Slumg__G", FontWeight.BOLD));
        wordCloud.setBackground(new PixelBoundaryBackground(getInputStream("backgrounds/earth.bmp")));
        wordCloud.setColorPalette(buildRandomColorPalette(3));
        wordCloud.setFontScalar(new SqrtFontScalar(10, 40));
        final long startTime = System.currentTimeMillis();
        wordCloud.build(wordFrequencies);
        LOGGER.info("Took {}ms to build", System.currentTimeMillis() - startTime);
        wordCloud.writeToFile(KumoTestUtils.prependDataDir("output/earth_image_word_cloud_different_fonts.png"));
    }

    @Test
    public void catImage() throws IOException {
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(300);
        frequencyAnalyzer.setMinWordLength(5);
        frequencyAnalyzer.setStopWords(loadStopWords());

        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(getInputStream("text/tidy_cat_litter_top.txt"));
        final Rect dimension = new Rect(0, 0, 600, 600);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(1);
        wordCloud.setKumoFont(new KumoFont("Marker Felt", FontWeight.PLAIN));
        //wordCloud.setAngleGenerator(new AngleGenerator(0));
        wordCloud.setBackgroundColor(Color.BLACK);
        wordCloud.setBackground(new PixelBoundaryBackground(getInputStream("backgrounds/cat.bmp")));
        wordCloud.setColorPalette(new ColorPalette(0xffcccccc, 0xffdddddd, 0xffffffff));
        wordCloud.setFontScalar(new SqrtFontScalar(10, 50));
        final long startTime = System.currentTimeMillis();
        wordCloud.build(wordFrequencies);
        LOGGER.info("Took {}ms to build", System.currentTimeMillis() - startTime);
        wordCloud.writeToFile(KumoTestUtils.prependDataDir("output/tidy_cat_litter_black_cat.png"));
    }

    @Ignore
    @Test
    public void datarankCode() throws IOException {
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(600);
        frequencyAnalyzer.setMinWordLength(5);
        frequencyAnalyzer.setStopWords(loadStopWords());

        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(new FileInputStream("/tmp/code.txt"));
        final Rect dimension = new Rect(0, 0, 990, 618);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(2);
        wordCloud.setBackgroundColor(Color.WHITE);
        wordCloud.setBackground(new PixelBoundaryBackground(getInputStream("backgrounds/whale.png")));
        wordCloud.setColorPalette(buildRandomColorPalette(3));
        wordCloud.setFontScalar(new SqrtFontScalar(10, 50));
        wordCloud.build(wordFrequencies);
        wordCloud.writeToFile("/tmp/datarank_code.png");
    }

    @Ignore
    @Test
    public void largeCircleTest() throws IOException {
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(5000);
        frequencyAnalyzer.setMinWordLength(2);

        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(new FileInputStream("/tmp/code.txt"));
        final Rect dimension = new Rect(0, 0, 600, 600);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(1);
        wordCloud.setBackground(new CircleBackground(500));
        wordCloud.setColorPalette(buildRandomColorPalette(20));
        wordCloud.setFontScalar(new LinearFontScalar(10, 100));
        wordCloud.build(wordFrequencies);
        wordCloud.writeToFile(KumoTestUtils.prependDataDir("output/wordcloud_large_code_circle.png"));
    }

    @Ignore
    @Test
    public void matchOnlineExample() throws IOException {
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(600);
        frequencyAnalyzer.setMinWordLength(5);
        frequencyAnalyzer.setStopWords(loadStopWords());
        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(new FileInputStream("/tmp/code.txt"));
        final Rect dimension = new Rect(0, 0, 600, 600);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(1);
        wordCloud.setBackground(new CircleBackground(300));
        wordCloud.setBackgroundColor(Color.WHITE);
        wordCloud.setColorPalette(buildRandomColorPalette(2));
        wordCloud.setKumoFont(new KumoFont("Helvitica", FontWeight.PLAIN));
        wordCloud.setFontScalar(new LinearFontScalar(8, 130));
        wordCloud.build(wordFrequencies);
        wordCloud.writeToFile(KumoTestUtils.prependDataDir("output/wordcloud_match_online_example.png"));
    }

    @Test
    public void anotherRectangleTest() throws IOException {
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(250);
        frequencyAnalyzer.setMinWordLength(5);
        frequencyAnalyzer.setStopWords(loadStopWords());
        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(getInputStream("text/sample.txt"));
        final Rect dimension = new Rect(0, 0, 600, 600);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(2);
        wordCloud.setBackground(new RectangleBackground(dimension));
        wordCloud.setColorPalette(buildRandomColorPalette(4));
        wordCloud.setFontScalar(new LinearFontScalar(30, 60));
        wordCloud.build(wordFrequencies);
        wordCloud.writeToFile(KumoTestUtils.prependDataDir("output/random_sample.png"));
    }

    private static ColorPalette buildRandomColorPalette(final int n) {
        final int[] colors = new int[n];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = Color.rgb(RANDOM.nextInt(230) + 25, RANDOM.nextInt(230) + 25, RANDOM.nextInt(230) + 25);
        }
        return new ColorPalette(colors);
    }

    private static List<WordFrequency> buildWordFrequencies() throws IOException {
        final List<String> pokemonNames = getPokemonNames();
        final List<WordFrequency> wordFrequencies = new ArrayList<>();
        for (final String pokemon : pokemonNames) {
            wordFrequencies.add(new WordFrequency(pokemon, RANDOM.nextInt(100) + 1));
        }
        return wordFrequencies;
    }

    private static List<String> getPokemonNames() throws IOException {
        return KumoUtils.readLines(getInputStream("text/pokemon.txt"));
    }

    private static Set<String> loadStopWords() {
        try {
            final List<String> lines = KumoUtils.readLines(getInputStream("text/stop_words.txt"));
            return new HashSet<>(lines);

        } catch (final IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return Collections.emptySet();
    }

    private static InputStream getInputStream(final String path) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    }

}
