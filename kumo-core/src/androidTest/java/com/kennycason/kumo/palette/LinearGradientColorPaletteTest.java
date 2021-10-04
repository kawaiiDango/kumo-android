package com.kennycason.kumo.palette;

import org.junit.Test;


import static org.junit.Assert.assertEquals;

import android.graphics.Color;

/**
 * Created by kenny on 2/20/16.
 */
public class LinearGradientColorPaletteTest {

    @Test
    public void singleGradient() {
        final int color1 = Color.rgb(0x00, 0x00, 0x00);
        final int color2 = Color.rgb(0xFF, 0xFF, 0xFF);
        final int gradientSteps = 15;
        final LinearGradientColorPalette colorPalette = new LinearGradientColorPalette(color1, color2, gradientSteps);
        assertEquals(16, colorPalette.getColors().size()); // add two because inclusive beginning and end color
        assertEquals(color1, (int)colorPalette.getColors().get(0));
        assertEquals(color2, (int)colorPalette.getColors().get(gradientSteps));

        final int stepSize = 0xFF / gradientSteps;
        for (int i = 0; i < gradientSteps; i++) {
            assertEquals(i * stepSize, Color.red(colorPalette.getColors().get(i)));
            assertEquals(i * stepSize, Color.green(colorPalette.getColors().get(i)));
            assertEquals(i * stepSize, Color.blue(colorPalette.getColors().get(i)));
        }
    }

    @Test
    public void doubleGradient() {
        final int color1 = Color.rgb(0x00, 0x00, 0x00);
        final int color2 = Color.rgb(0xFF, 0xFF, 0xFF);
        final int color3 = Color.rgb(0x00, 0x00, 0x00);

        final int gradientSteps = 15;
        final LinearGradientColorPalette colorPalette = new LinearGradientColorPalette(color1, color2, color3,
                                                                                gradientSteps, gradientSteps);
        assertEquals(31, colorPalette.getColors().size()); // add two because inclusive beginning and end color
        assertEquals(color1, (int)colorPalette.getColors().get(0));
        assertEquals(color2, (int)colorPalette.getColors().get(gradientSteps));

        final int stepSize = 0xFF / gradientSteps;
        // test first half of gradient
        for (int i = 0; i <= gradientSteps; i++) {
            assertEquals(i * stepSize, Color.red(colorPalette.getColors().get(i)));
            assertEquals(i * stepSize, Color.green(colorPalette.getColors().get(i)));
            assertEquals(i * stepSize, Color.blue(colorPalette.getColors().get(i)));
        }
        // test second half of gradient
        int expectedColor = 0xFF; // gradient is decending
        for (int i = gradientSteps; i < colorPalette.getColors().size(); i++) {
            assertEquals(expectedColor, Color.red(colorPalette.getColors().get(i)));
            assertEquals(expectedColor, Color.green(colorPalette.getColors().get(i)));
            assertEquals(expectedColor, Color.blue(colorPalette.getColors().get(i)));
            expectedColor -= stepSize;
        }
    }
}
