package com.kennycason.kumo.font;


import android.graphics.Typeface;

/**
 * Created by kenny on 7/11/14.
 */
public enum FontWeight {
    PLAIN(Typeface.NORMAL),
    BOLD(Typeface.BOLD),
    ITALIC(Typeface.ITALIC);

    private final int weight;

    FontWeight(final int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

}
