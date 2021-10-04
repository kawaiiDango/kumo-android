package com.kennycason.kumo.font;

import android.annotation.SuppressLint;
import android.graphics.Typeface;

import java.io.File;

/**
 * Created by kenny on 7/3/14.
 */
public class KumoFont {
    private static final int DEFAULT_WEIGHT = 10;

    private final Typeface font;

    @SuppressLint("WrongConstant")
    public KumoFont(final String type, final FontWeight weight) {
        this.font = Typeface.create(type, weight.getWeight());
    }

    public KumoFont(final Typeface font) {
        this.font = font;
    }

    public KumoFont(final File file) {
        this(buildAndRegisterFont(file));
    }

    public KumoFont(final String filePath) {
        this(new File(filePath));
    }

//    public KumoTypeface(final InputStream inputStream) {
//        this(buildAndRegisterTypeface(inputStream));
//    }

    private static Typeface buildAndRegisterFont(final File file) {
        final Typeface font = Typeface.createFromFile(file);
//        registerTypeface(font);
        return font;

    }

//    private static Typeface buildAndRegisterFont(final InputStream inputStream) {
//        try {
//            final Typeface font = Typeface.crea(inputStream);
//            registerFont(font);
//            return font;
//
//        } catch (IOException e) {
//            throw new KumoException(e.getMessage(), e);
//        }
//    }

//    private static void registerFont(final Typeface font) {
//        final GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
//        graphicsEnvironment.registerFont(font);
//    }

    public Typeface getFont() {
        return this.font;
    }

}
