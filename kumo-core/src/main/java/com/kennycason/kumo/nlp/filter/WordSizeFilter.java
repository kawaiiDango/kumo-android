package com.kennycason.kumo.nlp.filter;


import android.text.TextUtils;

/**
 * Created by kenny
 */
public class WordSizeFilter extends Filter {

    private final int minLength;

    private final int maxLength;

    public WordSizeFilter(final int minLength, final int maxLength) {
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    @Override
    public boolean test(final String word) {
        return !TextUtils.isEmpty(word.trim())
                && word.length() >= minLength
                && word.length() < maxLength;
    }

}
