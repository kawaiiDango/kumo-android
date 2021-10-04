package com.kennycason.kumo;

import android.graphics.Color;
import android.graphics.Rect;

import com.kennycason.kumo.bg.PixelBoundaryBackground;
import com.kennycason.kumo.examples.WordCloudITest;
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
import java.util.*;
import java.util.List;

    /**
    *  This is part of javadoc is makred by Jiaxiang Li
    *
    */
public class WordCloudAutoFillTest {

    
    /**
     * we have four test cases, and they are being applied to the test the
     * issue of word too few, autofill, default fill, and directly dealing
     * with the string lists
     *
     */

    private static final String INPUT_PATH ="backgrounds/whale.png";
    private static final String DEFAULT_FONT = "Impact";
    private static final String DEFAULT_IMAGE_TYPE = "png";
    
    /** This method is for testing the picture of KumoTestUtils.prependDataDir("output_test/a_whale_word_too_few.png"
     * @throws IOException
     */

    @Test
    public void whaleImgWordTooFewTest() throws IOException {
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(600);
        frequencyAnalyzer.setMinWordLength(5);
        frequencyAnalyzer.setStopWords(loadStopWords());
        final int width = 1500;
        final int height = 1000;

        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(getInputStream("text/hello_world.txt"), true);
        final Rect dimension = new Rect(0, 0, width, height);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(1);
        wordCloud.setBackgroundColor(Color.WHITE);
        InputStream inputStream = null;
        try {
            inputStream = ImageProcessor.readImage(INPUT_PATH, width, height, DEFAULT_IMAGE_TYPE);

            wordCloud.setBackground(new PixelBoundaryBackground(inputStream));

        } finally {
            inputStream.close();
        }
        wordCloud.setKumoFont(new KumoFont(DEFAULT_FONT, FontWeight.PLAIN));
        wordCloud.setColorPalette(new ColorPalette(0xff4055F1, 0xff408DF1, 0xff40AAF1, 0xff40C5F1, 0xff40D3F1, 0xff000000));
        wordCloud.setFontScalar(new SqrtFontScalar(10, 50));
        wordCloud.build(wordFrequencies);
        wordCloud.writeToFile(KumoTestUtils.prependDataDir("output_test/a_whale_word_too_few.png"));
    }
    
    /**
     * This method is for when there are no word after be filtered with
     * the Custom autofill test
     * @throws IOException
     */
    
    @Test
    public void whaleImgNoWordAfterFilterWithCustomAutoFillTest() throws IOException {
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(600);
        frequencyAnalyzer.setMinWordLength(5);
        frequencyAnalyzer.setStopWords(loadStopWords());
        final int width = 1500;
        final int height = 1000;

        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(getInputStream("text/empty.txt"), true, "Dararara");
        final Rect dimension = new Rect(0, 0, width, height);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(1);
        wordCloud.setBackgroundColor(Color.WHITE);
        InputStream inputStream = null;
        try {
            inputStream = ImageProcessor.readImage(INPUT_PATH, width, height, DEFAULT_IMAGE_TYPE);

            wordCloud.setBackground(new PixelBoundaryBackground(inputStream));

        } finally {
            inputStream.close();
        }
        wordCloud.setKumoFont(new KumoFont(DEFAULT_FONT, FontWeight.PLAIN));
        wordCloud.setColorPalette(new ColorPalette(0xff4055F1, 0xff408DF1, 0xff40AAF1, 0xff40C5F1, 0xff40D3F1, 0xff000000));
        wordCloud.setFontScalar(new SqrtFontScalar(10, 50));
        wordCloud.build(wordFrequencies);
        wordCloud.writeToFile(KumoTestUtils.prependDataDir("output_test/a_whale_no_word_with_dararara.png"));
    }
    
    /**
     * This method is for when there are no word after be filtered with
     * the default autofill test
     * @throws IOException
     * 
     */
    //CS304 Issue Link:
    //https://github.com/kennycason/kumo/issues/93

    @Test
    public void whaleImgNoWordAfterFilterWithDefaultAutoFillTest() throws IOException {
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(600);
        //change 5->6
        frequencyAnalyzer.setMinWordLength(6);
        frequencyAnalyzer.setStopWords(loadStopWords());
        final int width = 1500;
        final int height = 1000;

        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(getInputStream("text/empty.txt"), true);
        final Rect dimension = new Rect(0, 0, width, height);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(1);
        wordCloud.setBackgroundColor(Color.WHITE);
        InputStream inputStream = null;
        try {
            inputStream = ImageProcessor.readImage(INPUT_PATH, width, height, DEFAULT_IMAGE_TYPE);

            wordCloud.setBackground(new PixelBoundaryBackground(inputStream));

        } finally {
            inputStream.close();
        }
        wordCloud.setKumoFont(new KumoFont(DEFAULT_FONT, FontWeight.PLAIN));
        wordCloud.setColorPalette(new ColorPalette(0xff4055F1, 0xff408DF1, 0xff40AAF1, 0xff40C5F1, 0xff40D3F1, 0xff000000));
        wordCloud.setFontScalar(new SqrtFontScalar(10, 50));
        wordCloud.build(wordFrequencies);
        wordCloud.writeToFile(KumoTestUtils.prependDataDir("output_test/a_whale_no_word_with_default_autofill.png"));
    }
    
    /**
     * This method is for the image of whale when manually add more duplicated
     * words
     * @throws IOException
     */
    //CS304 Issue Link:
    //https://github.com/kennycason/kumo/issues/93

    @Test
    public void whaleImgWithListOfStringTest() throws IOException{
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(600);
        frequencyAnalyzer.setMinWordLength(5);
        frequencyAnalyzer.setStopWords(loadStopWords());
        final int width = 1500;
        final int height = 1000;
        final List<String> texts = new ArrayList<>();
        texts.add("hello");
        texts.add("world");
        //my added
        texts.add("hello world");

        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(texts, true);
        final Rect dimension = new Rect(0, 0, width, height);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(1);
        wordCloud.setBackgroundColor(Color.WHITE);
        InputStream inputStream = null;
        try {
            inputStream = ImageProcessor.readImage(INPUT_PATH, width, height, DEFAULT_IMAGE_TYPE);

            wordCloud.setBackground(new PixelBoundaryBackground(inputStream));

        } finally {
            inputStream.close();
        }
        wordCloud.setKumoFont(new KumoFont(DEFAULT_FONT, FontWeight.PLAIN));
        wordCloud.setColorPalette(new ColorPalette(0xff4055F1, 0xff408DF1, 0xff40AAF1, 0xff40C5F1, 0xff40D3F1, 0xff000000));
        wordCloud.setFontScalar(new SqrtFontScalar(10, 50));
        wordCloud.build(wordFrequencies);
        wordCloud.writeToFile(KumoTestUtils.prependDataDir("output_test/a_whale_with_string_list.png"));
    }
    
    /**
     * This method is for loading the stop words
     * @return
     */
    
    private static Set<String> loadStopWords() {
        try {
            final List<String> lines = KumoUtils.readLines(getInputStream("text/stop_words.txt"));
            return new HashSet<>(lines);

        } catch (final IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return Collections.emptySet();
    }
    private static final Logger LOGGER = LoggerFactory.getLogger(WordCloudITest.class);
    private static InputStream getInputStream(final String path) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    }


    //CS304 (manually written) issue link:
    //https://github.com/kennycason/kumo/issues/93

    @Test
    public void wordtoosmall() throws IOException{
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(500);
        frequencyAnalyzer.setMinWordLength(6);
        frequencyAnalyzer.setStopWords(loadStopWords());
        final int width = 500;
        final int height = 900;
        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(getInputStream("text/hello_world.txt"), true);
        final Rect dimension = new Rect(0, 0, width, height);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(1);
        wordCloud.setBackgroundColor(Color.WHITE);
        InputStream inputStream = null;
        try {
            inputStream = ImageProcessor.readImage(INPUT_PATH, width, height, DEFAULT_IMAGE_TYPE);
            wordCloud.setBackground(new PixelBoundaryBackground(inputStream));
        } finally {
            inputStream.close();
        }
        wordCloud.setKumoFont(new KumoFont(DEFAULT_FONT, FontWeight.PLAIN));
        wordCloud.setColorPalette(new ColorPalette(0xff4055F1, 0xff408DF1, 0xff40AAF1, 0xff40C5F1, 0xff40D3F1, 0xff000000));
        wordCloud.setFontScalar(new SqrtFontScalar(7, 35));
        wordCloud.build(wordFrequencies);
        wordCloud.writeToFile(KumoTestUtils.prependDataDir("output_test/a_whale_word_too_few.png"));
    }

    //CS304 (manually written) issue link:
    //https://github.com/kennycason/kumo/issues/93
    @Test
    public void duplicatesmall() throws IOException{
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(600);
        frequencyAnalyzer.setMinWordLength(5);
        frequencyAnalyzer.setStopWords(loadStopWords());
        final int width = 300;
        final int height = 400;
        final List<String> texts = new ArrayList<>();
        //could manually add more here
        texts.add("hellohellohello");
        texts.add("worldworldworld");

        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(texts, true);
        final Rect dimension = new Rect(0, 0, width, height);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(1);
        wordCloud.setBackgroundColor(Color.WHITE);
        InputStream inputStream = null;
        try {
            inputStream = ImageProcessor.readImage(INPUT_PATH, width, height, DEFAULT_IMAGE_TYPE);

            wordCloud.setBackground(new PixelBoundaryBackground(inputStream));

        } finally {
            inputStream.close();
        }
        wordCloud.setKumoFont(new KumoFont(DEFAULT_FONT, FontWeight.PLAIN));
        wordCloud.setColorPalette(new ColorPalette(0xff4055F1, 0xff408DF1, 0xff40AAF1, 0xff40C5F1, 0xff40D3F1, 0xff000000));
        wordCloud.setFontScalar(new SqrtFontScalar(8, 30));
        wordCloud.build(wordFrequencies);
        wordCloud.writeToFile(KumoTestUtils.prependDataDir("output_test/a_whale_with_string_list.png"));
    }
}
