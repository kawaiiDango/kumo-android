package com.kennycason.kumo.palette;

import java.util.ArrayList;
import java.util.List;

/**
 * A LinearGradient ColorPalette
 * 
 * @author &#64;wolfposd
 */
public class LinearGradientColorPalette extends ColorPalette {

    /**
     * <p>
     * Creates a ColorPalette using a linear gradient between the two colors
     * specified
     * </p>
     * <p>
     * The following example code will generate a gradient between red and blue,
     * with 18 colors in between red and blue, thus totaling 20 colors:
     * </p>
     * <code>
     * new LinearGradientColorPalette(Color.RED, 18, Color.Blue)
     * </code>
     * 
     * @param color1
     *            start color
     * @param color2
     *            end color
     * @param gradientSteps
     *            specifies the amount of colors for this gradient between
     *            color1 and color2
     */
    public LinearGradientColorPalette(final int color1,
                                      final int color2,
                                      final int gradientSteps) {
        super(createLinearGradient(color1, color2, gradientSteps));
    }

    /**
     * Creates a ColorPalette using a linear gradient between the three colors
     * specified
     *
     * @param color1
     *            start color
     * @param color2
     *            middle color
     * @param color3
     *            end color
     * @param gradientStepsC1AndC2
     *            number of colors to be generated between color1 and color2
     *            this includes both color1 and color2
     * @param gradientStepsC2AndC3
     *            number of colors to be generated between color2 and color3
     *            this includes both color2 and color3
     */
    public LinearGradientColorPalette(final int color1,
                                      final int color2,
                                      final int color3,
                                      final int gradientStepsC1AndC2,
                                      final int gradientStepsC2AndC3) {
        super(createTwoLinearGradients(color1, color2, color3, gradientStepsC1AndC2, gradientStepsC2AndC3));
    }

    private static List<Integer> createTwoLinearGradients(final int color1,
                                                        final int color2,
                                                        final int color3,
                                                        final int gradientStepsC1AndC2,
                                                        final int gradientStepsC2AndC3) {
        final List<Integer> colors = new ArrayList<>();

        final List<Integer> gradient1 = createLinearGradient(color1, color2, gradientStepsC1AndC2);
        final List<Integer> gradient2 = createLinearGradient(color2, color3, gradientStepsC2AndC3);

        colors.addAll(gradient1);
        // the first item will overlap with the color2, so ignore it
        colors.addAll(gradient2.subList(1, gradient2.size()));

        return colors;
    }

    /**
     * Creates a linear Gradient between two colors
     *
     * @param color1
     *            start color
     * @param color2
     *            end color
     * @param gradientSteps
     *            specifies the amount of colors in this gradient between color1
     *            and color2, this includes both color1 and color2
     * @return List of colors in this gradient
     */
    private static List<Integer> createLinearGradient(final int color1, final int color2, final int gradientSteps) {
        final List<Integer> colors = new ArrayList<>(gradientSteps + 1);

        // add beginning color to the gradient
        colors.add(color1);

        for (int i = 1; i < gradientSteps; i++) {
            float ratio = (float) i / (float) gradientSteps;

            final int red = (int) (getRed(color2) * ratio + getRed(color1) * (1 - ratio));
            final int green = (int) (getGreen(color2) * ratio + getGreen(color1) * (1 - ratio));
            final int blue = (int) (getBlue(color2) * ratio + getBlue(color1) * (1 - ratio));

            colors.add(rgb(red, green, blue));
        }
        // add end color to the gradient
        colors.add(color2);

        return colors;
    }

    private static int getRed(int color) {
        return (color >> 16) & 0xFF;
    }

    private static int getGreen(int color) {
        return (color >> 8) & 0xFF;
    }

    private static int getBlue(int color) {
        return color & 0xFF;
    }

    private static int rgb(int red, int green, int blue) {
        return 0xff000000 | (red << 16) | (green << 8) | blue;
    }
}
