package com.kennycason.kumo.nlp.normalize;


/**
 * Created by kenny on 7/1/14.
 */
public class TrimToEmptyNormalizer implements Normalizer {
    @Override
    public String apply(final String text) {
        return text.trim();
    }
}
