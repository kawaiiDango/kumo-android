package com.kennycason.kumo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.kennycason.kumo.collide.Collidable;
import com.kennycason.kumo.collide.checkers.CollisionChecker;
import com.kennycason.kumo.image.CollisionRaster;
import com.kennycason.kumo.image.ImageRotation;



/**
 * Created by kenny on 6/29/14.
 */
public class Word implements Collidable {

    private final CollisionChecker collisionChecker;

    private final String word;

    private final int color;

    private final Point position = new Point(0, 0);

    private Bitmap bufferedImage;

    private CollisionRaster collisionRaster;
    private final double theta;

    public Word(final String word,
            final int color,
            final Paint paint,
            final CollisionChecker collisionChecker,
            final double theta) {
        this.word = word;
        this.color = color;
        this.collisionChecker = collisionChecker;
        this.theta = theta;
        this.bufferedImage = render(word, color, paint, theta);

        this.collisionRaster = new CollisionRaster(this.bufferedImage);
    }

    private Bitmap render(final String text, final int fontColor, final Paint paint, double theta) {
        // get the advance of my text in this font and render context
        final float width = paint.measureText(text);
        // get the height of a line of text in this font and render context
        final float height = paint.getFontSpacing();

        final Bitmap rendered = Bitmap.createBitmap(
                (int)width, (int)height, Bitmap.Config.ARGB_8888
        );

        final Canvas gOfRendered = new Canvas(rendered);

        paint.setColor(fontColor);

        gOfRendered.drawText(
                text, 0, height - paint.getFontMetrics().descent - paint.getFontMetrics().leading, paint
        );
        return ImageRotation.rotate(rendered, theta);
    }

    public Bitmap getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(final Bitmap bufferedImage) {
        this.bufferedImage = bufferedImage;
        this.collisionRaster = new CollisionRaster(bufferedImage);
    }

    public String getWord() {
        return word;
    }

    public double getTheta() {
        return theta;
    }

    public Point getPosition() {
        return position;
    }

    public Rect getDimension() {
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

    public static Paint getPaint() {
        return new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
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
