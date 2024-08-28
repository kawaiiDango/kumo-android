package com.kennycason.kumo;


import com.kennycason.kumo.compat.KumoBitmap;
import com.kennycason.kumo.compat.KumoGraphicsFactory;
import com.kennycason.kumo.compat.KumoCanvas;
import com.kennycason.kumo.compat.KumoRect;

import com.kennycason.kumo.collide.Collidable;
import com.kennycason.kumo.collide.checkers.CollisionChecker;
import com.kennycason.kumo.compat.KumoPoint;
import com.kennycason.kumo.image.CollisionRaster;
import com.kennycason.kumo.image.ImageRotation;



/**
 * Created by kenny on 6/29/14.
 */
public class Word implements Collidable {

    private final CollisionChecker collisionChecker;

    private final String word;

    private final int color;

    private final KumoPoint position = new KumoPoint(0, 0);

    private KumoBitmap bufferedImage;

    private CollisionRaster collisionRaster;
    private final double theta;

    public Word(final String word,
                final int color,
                final KumoCanvas fontMetricsCanvas,
                final CollisionChecker collisionChecker,
                final KumoGraphicsFactory graphicsFactory,
                final double theta) {
        this.word = word;
        this.color = color;
        this.collisionChecker = collisionChecker;
        this.theta = theta;
        this.bufferedImage = render(word, color, graphicsFactory, fontMetricsCanvas, theta);

        this.collisionRaster = new CollisionRaster(this.bufferedImage);
    }

    private KumoBitmap render(final String text, final int fontColor, final KumoGraphicsFactory graphicsFactory, final KumoCanvas fontMetricsCanvas, double theta) {
        // get the advance of my text in this font and render context
        final int width = fontMetricsCanvas.measureText(text);
        // get the height of a line of text in this font and render context
        final int height = fontMetricsCanvas.getFontHeight();

        final KumoBitmap rendered = graphicsFactory.createBitmap(width, height);

        final KumoCanvas gOfRendered = graphicsFactory.createCanvas(rendered);

        gOfRendered.setTextSize(fontMetricsCanvas.getTextSize());

        gOfRendered.setColor(fontColor);

        gOfRendered.drawText(
                text, 0, height - fontMetricsCanvas.getDescent() - fontMetricsCanvas.getLeading()
        );
        return ImageRotation.rotate(
                rendered,
                graphicsFactory,
                theta
        );
    }

    public KumoBitmap getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(final KumoBitmap bufferedImage) {
        this.bufferedImage = bufferedImage;
        this.collisionRaster = new CollisionRaster(bufferedImage);
    }

    public String getWord() {
        return word;
    }

    public double getTheta() {
        return theta;
    }

    public KumoPoint getPosition() {
        return position;
    }

    public KumoRect getDimension() {
        return collisionRaster.getDimension();
    }

    @Override
    public CollisionRaster getCollisionRaster() {
        return collisionRaster;
    }

    @Override
    public boolean collide(final Collidable collidable) {
        return collisionChecker.collide(this, collidable);
    }

    public void draw(final CollisionRaster collisionRaster) {
        collisionRaster.mask(collisionRaster, position);
    }

//    public static RenderingHints getRenderingHints() {
//        Map<RenderingHints.Key, Object> hints = new HashMap<>();
//        hints.put(
//                RenderingHints.KEY_ALPHA_INTERPOLATION,
//                RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY
//        );
//        hints.put(
//                RenderingHints.KEY_ANTIALIASING,
//                RenderingHints.VALUE_ANTIALIAS_ON
//        );
//        hints.put(
//                RenderingHints.KEY_COLOR_RENDERING,
//                RenderingHints.VALUE_COLOR_RENDER_QUALITY
//        );
//        hints.put(
//                RenderingHints.KEY_FRACTIONALMETRICS,
//                RenderingHints.VALUE_FRACTIONALMETRICS_ON
//        );
//        hints.put(
//                RenderingHints.KEY_INTERPOLATION,
//                RenderingHints.VALUE_INTERPOLATION_BICUBIC
//        );
//        hints.put(
//                RenderingHints.KEY_TEXT_ANTIALIASING,
//                RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB
//        );
//
//        return new RenderingHints(hints);
//    }

    @Override
    public String toString() {
        return "Word{"
                + "word='" + word + '\''
                + ", color=" + color
                + ", x=" + position.x
                + ", y=" + position.y
                + ", width=" + bufferedImage.getWidth()
                + ", height=" + bufferedImage.getHeight()
                + '}';
    }

}
