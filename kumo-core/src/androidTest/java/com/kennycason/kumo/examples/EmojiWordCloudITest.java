package com.kennycason.kumo.examples;

import android.graphics.Rect;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.KumoTestUtils;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.font.KumoFont;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by kenny on 6/14/16.
 */
public class EmojiWordCloudITest {
    private static final Random RANDOM = new Random();

    @Test
    public void emojiCloud() throws IOException {
        final Rect dimension = new Rect(0, 0, 250, 250);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        // Load font from file
        // wordCloud.setKumoFont(new KumoFont(new File("/Users/kenny/Downloads/OpenSansEmoji.ttf")));
        // Load font from resource
        InputStream fontIs = getClass().getClassLoader().getResourceAsStream("font/OpenSansEmoji.ttf");
        FileOutputStream fontFos = new FileOutputStream(KumoTestUtils.prependDataDir("font/OpenSansEmoji.ttf"));
        KumoTestUtils.copy(fontIs, fontFos);
        wordCloud.setKumoFont(new KumoFont(KumoTestUtils.prependDataDir("font/OpenSansEmoji.ttf")));
        wordCloud.setPadding(2);
        wordCloud.build(buildWordFrequencies());
        wordCloud.writeToFile(KumoTestUtils.prependDataDir("output/wordcloud_emoji.png"));
    }

    
    private static List<WordFrequency> buildWordFrequencies() {
        final List<WordFrequency> wordFrequencies = new ArrayList<>();
        for (final String emoji : EMOJIS) {
            wordFrequencies.add(new WordFrequency(emoji, RANDOM.nextInt(250)));
        }
        return wordFrequencies;
    }
    
    private static final String[] EMOJIS = {
            "emoji",
            "kumo",
            "\uD83D\uDE02",
            "\uD83D\uDC4D",
            "\uD83D\uDE0A",
            "\uD83D\uDE01",
            "\uD83D\uDE12",
            "\uD83D\uDE11",
            "\uD83D\uDE0D",
            "\uD83D\uDC4C",
            "\uD83D\uDC55",
            "\uD83D\uDCAA",
            "\uD83D\uDE4C",
            "\uD83D\uDE2D",
            "\uD83D\uDE29",
            "\uD83D\uDE21",
            "\uD83D\uDD25",
            "\uD83D\uDE33",
            "\uD83D\uDD25",
            "\uD83D\uDE21",
            "\uD83D\uDCA9",
            "\uD83D\uDC7B",
            "\uD83D\uDC7D",
            "\uD83D\uDC57",
            "\uD83D\uDD25",
            "\uD83C\uDF53",
            "\uD83C\uDFB1",
            "\uD83C\uDFA7",
            "\uD83D\uDE91",
            "\uD83C\uDFEB",
            "\uD83D\uDCBE",
            "\uD83D\uDCAF",
            "\uD83D\uDE32",
            "\uD83D\uDE31",
            "\uD83D\uDE0F",
            "\uD83D\uDC44",
            "\uD83D\uDC40 "
    };

}
