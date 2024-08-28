package com.arn.kumoexample.kumo.compat;

import com.kennycason.kumo.compat.KumoBitmap;
import com.kennycason.kumo.compat.KumoCanvas;
import com.kennycason.kumo.compat.KumoGraphicsFactory;

import java.io.InputStream;

public class AndroidGraphicsFactory extends KumoGraphicsFactory {
    @Override
    public KumoBitmap createBitmap(int width, int height) {
        return new AndroidBitmap(width, height);
    }

    @Override
    public KumoBitmap decodeStream(InputStream inputStream) {
        return new AndroidBitmap(inputStream);
    }

    @Override
    public KumoCanvas createCanvas(KumoBitmap bitmap) {
        return new AndroidCanvas(((AndroidBitmap) bitmap).getBitmap());
    }
}
