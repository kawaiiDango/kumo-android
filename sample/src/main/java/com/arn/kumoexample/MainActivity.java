package com.arn.kumoexample;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Rect;
import android.os.Bundle;
import android.widget.ImageView;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.RectangleBackground;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<WordFrequency> WORD_FREQUENCIES = Arrays.asList(new WordFrequency("apple", 22),
                new WordFrequency("baby", 3),
                new WordFrequency("back", 15),
                new WordFrequency("bear", 9),
                new WordFrequency("boy", 26));

        final Rect dimension = new Rect(0, 0, 500, 500);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(2);
        wordCloud.setBackground(new RectangleBackground(dimension));
        wordCloud.build(WORD_FREQUENCIES);

        final ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageBitmap(wordCloud.getBitmap());
    }
}