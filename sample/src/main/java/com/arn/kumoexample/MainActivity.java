package com.arn.kumoexample;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import com.arn.kumoexample.kumo.compat.AndroidBitmap;
import com.arn.kumoexample.kumo.compat.AndroidGraphicsFactory;
import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.compat.KumoRect;
import com.kennycason.kumo.font.scale.LinearFontScalar;
import com.kennycason.kumo.image.AngleGenerator;
import com.kennycason.kumo.palette.ColorPalette;
import com.kennycason.kumo.palette.LinearGradientColorPalette;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // random word frequencies
        final List<WordFrequency> wordFrequencies = Arrays.asList(
                new WordFrequency("apple", 22),
                new WordFrequency("baby", 3),
                new WordFrequency("back", 15),
                new WordFrequency("bear", 9),
                new WordFrequency("boy", 26),
                new WordFrequency("brown", 5),
                new WordFrequency("came", 7),
                new WordFrequency("cat", 19),
                new WordFrequency("cloud", 11),
                new WordFrequency("cow", 17),
                new WordFrequency("day", 10),
                new WordFrequency("dog", 13),
                new WordFrequency("fox", 8),
                new WordFrequency("jumped", 14),
                new WordFrequency("lazy", 6),
                new WordFrequency("moon", 12),
                new WordFrequency("quick", 4),
                new WordFrequency("red", 2),
                new WordFrequency("sky", 16),
                new WordFrequency("the", 1),
                new WordFrequency("over", 18),
                new WordFrequency("river", 20),
                new WordFrequency("tree", 21),
                new WordFrequency("two", 23),
                new WordFrequency("white", 24),
                new WordFrequency("bright", 25)
        );

        final KumoRect dimension = new KumoRect(500, 500);
        final WordCloud wordCloud = new WordCloud(
                dimension,
                CollisionMode.PIXEL_PERFECT,
                new AndroidGraphicsFactory()
        );

        final ColorPalette colorPalette = new LinearGradientColorPalette(
                Color.RED,
                Color.GREEN,
                10
        );

        wordCloud.setBackground(new CircleBackground(dimension.width() / 2));
        wordCloud.setBackgroundColor(Color.TRANSPARENT);
        wordCloud.setAngleGenerator(new AngleGenerator(0));
        wordCloud.setPadding(spToPx(4));
        wordCloud.setFontScalar(new LinearFontScalar(spToPx(12), spToPx(48)));
        wordCloud.setColorPalette(colorPalette);
        wordCloud.build(wordFrequencies);

        final AndroidBitmap androidBitmap = (AndroidBitmap) wordCloud.getBufferedImage();
        final ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageBitmap(androidBitmap.getBitmap());
    }

    private int spToPx(int sp) {
        return (int) (sp * getResources().getDisplayMetrics().scaledDensity);
    }
}