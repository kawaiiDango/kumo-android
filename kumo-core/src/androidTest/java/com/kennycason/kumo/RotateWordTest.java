package com.kennycason.kumo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joerg1985
 */
//@RunWith(Parameterized.class)
public class RotateWordTest {

    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        List<Object[]> params = new ArrayList<>();
        String text = "rotating text is great!!";

        for (int i = 20; i < 100; i += 10) {
            params.add(new Object[]{Typeface.create("Arial", Typeface.NORMAL), text.toLowerCase()});
            params.add(new Object[]{Typeface.create("Arial", Typeface.NORMAL), text});
            params.add(new Object[]{Typeface.create("Arial", Typeface.NORMAL), text.toUpperCase()});
        }

        return params;
    }

    private final Typeface _font;
    private final String _text;

    public RotateWordTest(Typeface font, String text) {
        _font = font;
        _text = text;
    }

    @Ignore
    public void checkRotatedTextIsNotCropped() throws IOException {
        Bitmap image = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        Canvas graphics = new Canvas(image);

        // set the rendering hint here to ensure the font metrics are correct
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTypeface(_font);

        for (int angle = 0; angle < 360; angle += 5) {
            Word word = new Word(
                    _text, Color.RED, paint, null, Math.toRadians(angle)
            );

            Bitmap rendered = word.getBufferedImage();

            int width = rendered.getWidth();
            int height = rendered.getHeight();

            try {
                for (int y = 0; y < height; y++) {
                    Assert.assertTrue(
                            "text doesn't touch the outer left line",
                            nearlyTransparent(rendered.getPixel(0, y))
                    );
                    Assert.assertTrue(
                            "text doesn't touch the outer right line",
                            nearlyTransparent(rendered.getPixel(width - 1, y))
                    );
                }

                for (int x = 0; x < width; x++) {
                    Assert.assertTrue(
                            "text doesn't touch the top line",
                            nearlyTransparent(rendered.getPixel(x, 0))
                    );
                    Assert.assertTrue(
                            "text doesn't touch the bottom line",
                            nearlyTransparent(rendered.getPixel(x, height - 1))
                    );
                }
            } catch (AssertionError e) {
                FileOutputStream fos = new FileOutputStream(KumoTestUtils.prependDataDir("output/FailedRotateWordTest_" + System.currentTimeMillis() + ".png"));
                rendered.compress(Bitmap.CompressFormat.PNG, 100, fos);

                throw e;
            }
        }
    }

    private static boolean nearlyTransparent(int argb) {
        return (argb & 0xFF000000) < 0x40000000;
    }
}
