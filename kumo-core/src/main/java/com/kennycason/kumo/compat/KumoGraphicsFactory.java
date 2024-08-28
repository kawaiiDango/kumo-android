package com.kennycason.kumo.compat;

import java.io.InputStream;

public abstract class KumoGraphicsFactory {
    public abstract KumoBitmap createBitmap(final int width, final int height);

    public abstract KumoBitmap decodeStream(InputStream inputStream);

    public abstract KumoCanvas createCanvas(final KumoBitmap bitmap);
}
