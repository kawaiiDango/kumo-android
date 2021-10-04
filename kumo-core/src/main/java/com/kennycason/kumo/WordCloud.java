package com.kennycason.kumo;

import static com.kennycason.kumo.Word.getPaint;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.kennycason.kumo.bg.Background;
import com.kennycason.kumo.bg.RectangleBackground;
import com.kennycason.kumo.collide.RectanglePixelCollidable;
import com.kennycason.kumo.collide.checkers.CollisionChecker;
import com.kennycason.kumo.collide.checkers.RectangleCollisionChecker;
import com.kennycason.kumo.collide.checkers.RectanglePixelCollisionChecker;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.FontScalar;
import com.kennycason.kumo.font.scale.LinearFontScalar;
import com.kennycason.kumo.image.AngleGenerator;
import com.kennycason.kumo.image.CollisionRaster;
import com.kennycason.kumo.padding.Padder;
import com.kennycason.kumo.padding.RectanglePadder;
import com.kennycason.kumo.padding.WordPixelPadder;
import com.kennycason.kumo.palette.ColorPalette;
import com.kennycason.kumo.placement.RTreeWordPlacer;
import com.kennycason.kumo.placement.RectangleWordPlacer;
import com.kennycason.kumo.wordstart.RandomWordStart;
import com.kennycason.kumo.wordstart.WordStartStrategy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import timber.log.Timber;


/**
 * Created by kenny on 6/29/14.
 */
public class WordCloud {

    protected final Rect dimension;
    protected final CollisionMode collisionMode;
    protected final CollisionChecker collisionChecker;
    protected final RectanglePixelCollidable backgroundCollidable;
    protected final CollisionRaster collisionRaster;
    protected final Bitmap bufferedImage;
    protected final Padder padder;
    protected final Set<Word> skipped = new HashSet<>();
    protected int padding;
    protected Background background;
    protected int backgroundColor = Color.BLACK;
    protected FontScalar fontScalar = new LinearFontScalar(10, 40);
    protected KumoFont kumoFont = new KumoFont(Typeface.DEFAULT_BOLD);
    protected AngleGenerator angleGenerator = new AngleGenerator();
    protected RectangleWordPlacer wordPlacer = new RTreeWordPlacer();
    protected ColorPalette colorPalette = new ColorPalette(0xff02B6F2, 0xff37C2F0, 0xff7CCBE6, 0xffC4E7F2, 0xffFFFFFF);
    protected WordStartStrategy wordStartStrategy = new RandomWordStart();
    private final String TAG = "WordCloud";
    
    public WordCloud(final Rect dimension, final CollisionMode collisionMode) {
        this.collisionMode = collisionMode;
        this.padder = derivePadder(collisionMode);
        this.collisionChecker = deriveCollisionChecker(collisionMode);
        this.collisionRaster = new CollisionRaster(dimension);
        this.bufferedImage = Bitmap.createBitmap(dimension.width(), dimension.height(), Bitmap.Config.ARGB_8888);
        this.backgroundCollidable = new RectanglePixelCollidable(collisionRaster, new Point(0, 0));
        this.dimension = dimension;
        this.background = new RectangleBackground(dimension);
    }

    public void build(final List<WordFrequency> wordFrequencies) {
        Collections.sort(wordFrequencies);

        wordPlacer.reset();
        skipped.clear();
        
        // the background masks all none usable pixels and we can only check this raster
        background.mask(backgroundCollidable);
        
        int currentWord = 1;
        for (final Word word : buildWords(wordFrequencies, this.colorPalette)) {
            final Point point = wordStartStrategy.getStartingPoint(dimension, word);
            final boolean placed = place(word, point);

            if (placed) {
                Timber.tag(TAG).i("placed: %s (%d/%d)", word.getWord(), currentWord, wordFrequencies.size());
            } else {
                Timber.tag(TAG).i("skipped: %s (%s/%d)", word.getWord(), currentWord, wordFrequencies.size());
                skipped.add(word);
            }
            currentWord++;
        }
        
        drawForegroundToBackground();
    }

    public void writeToFile(final String outputFileName) throws IOException {
        Timber.tag(TAG).i("Saving WordCloud to: %s", outputFileName);
        FileOutputStream fos = new FileOutputStream(outputFileName);
        bufferedImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        fos.close();
    }

    /**
     * Write to output stream as PNG
     *
     * @param outputStream the output stream to write the image data to
     */
    public void writeToStreamAsPNG(final OutputStream outputStream) {
        writeToStream(Bitmap.CompressFormat.PNG, outputStream);
    }

    /**
     * Write wordcloud image data to stream in the given format
     *
     * @param format       the image format
     * @param outputStream the output stream to write image data to
     */
    public void writeToStream(final Bitmap.CompressFormat format, final OutputStream outputStream) {
        Timber.tag(TAG).d("Writing WordCloud image data to output stream");
        boolean success = bufferedImage.compress(format, 100, outputStream);
        if (success)
            Timber.tag(TAG).d("Done writing WordCloud image data to output stream");
        else
            Timber.tag(TAG).d("Failed to write WordCloud image data to output stream");

    }

    /**
     * create background, then draw current word cloud on top of it.
     * Doing it this way preserves the transparency of the this.bufferedImage's pixels
     * for a more flexible pixel perfect collision
     */
    protected void drawForegroundToBackground() {
        final Bitmap backgroundBufferedImage = Bitmap.createBitmap(dimension.width(), dimension.height(), this.bufferedImage.getConfig());
        final Canvas graphics = new Canvas(backgroundBufferedImage);

        // draw current color
        graphics.drawColor(backgroundColor);
        graphics.drawBitmap(bufferedImage, 0, 0, null);

        // draw back to original
        final Canvas graphics2 = new Canvas(bufferedImage);
        graphics2.drawBitmap(backgroundBufferedImage, 0, 0, null);

        backgroundBufferedImage.recycle();
    }

    /**
     * compute the maximum radius for the placing spiral
     *
     * @param dimension the size of the backgound
     * @param start the center of the spiral
     * @return the maximum usefull radius
     */
    static int computeRadius(final Rect dimension, final Point start) {
        final int maxDistanceX = Math.max(start.x, dimension.width() - start.x) + 1;
        final int maxDistanceY = Math.max(start.y, dimension.height() - start.y) + 1;
        
        // we use the pythagorean theorem to determinate the maximum radius
        return (int) Math.ceil(Math.sqrt(maxDistanceX * maxDistanceX + maxDistanceY * maxDistanceY));
    }
    
    /**
     * try to place in center, build out in a spiral trying to place words for N steps
     * @param word the word being placed
     * @param start the place to start trying to place the word
     */
    protected boolean place(final Word word, final Point start) {
        final Canvas graphics = new Canvas(this.bufferedImage);

        final int maxRadius = computeRadius(dimension, start);
        final Point position = word.getPosition();
        
        for (int r = 0; r < maxRadius; r += 2) {
            for (int x = Math.max(-start.x, -r); x <= Math.min(r, dimension.width() - start.x - 1); x++) {
                position.x = start.x + x;

                final int offset = (int) Math.sqrt(r * r - x * x);
                
                // try positive root
                position.y = start.y + offset;
                if (position.y >= 0 && position.y < dimension.height() && canPlace(word)) {
                    collisionRaster.mask(word.getCollisionRaster(), position);
                    graphics.drawBitmap(word.getBufferedImage(), position.x, position.y, null);
                    return true;
                }
                
                // try negative root (if offset != 0)
                position.y = start.y - offset;
                if (offset != 0 && position.y >= 0 && position.y < dimension.height() && canPlace(word)) {
                    collisionRaster.mask(word.getCollisionRaster(), position);
                    graphics.drawBitmap(word.getBufferedImage(), position.x, position.y, null);
                    return true;
                }
            }
        }

        return false;
    }

    private boolean canPlace(final Word word) {
        final Point position = word.getPosition();
        final Rect dimensionOfWord = word.getDimension();
        
        // are we inside the background?
        if (position.y < 0 || position.y + dimensionOfWord.height() > dimension.height()) {
            return false;
        } else if (position.x < 0 || position.x + dimensionOfWord.width() > dimension.width()) {
            return false;
        }
        
        switch (collisionMode) {
            case RECTANGLE:
                return !backgroundCollidable.collide(word) // is there a collision with the background shape?
                        && wordPlacer.place(word); // is there a collision with the existing words?
            case PIXEL_PERFECT:
                return !backgroundCollidable.collide(word); // is there a collision with the background shape?
        }
        return false;
    }
    
    protected List<Word> buildWords(final List<WordFrequency> wordFrequencies, final ColorPalette colorPalette) {
        final int maxFrequency = maxFrequency(wordFrequencies);

        final List<Word> words = new ArrayList<>();
        for (final WordFrequency wordFrequency : wordFrequencies) {
            // the text shouldn't be empty, however, in case of bad normalizers/tokenizers, this may happen
            if (!wordFrequency.getWord().isEmpty()) {
                words.add(buildWord(wordFrequency, maxFrequency, colorPalette));
            }
        }
        return words;
    }

    private Word buildWord(final WordFrequency wordFrequency, final int maxFrequency, final ColorPalette colorPalette) {
        final int frequency = wordFrequency.getFrequency();
        final float fontHeight = this.fontScalar.scale(frequency, maxFrequency);
        final Typeface typeface = (wordFrequency.hasFont() ? wordFrequency.getFont() : kumoFont).getFont();
        final Paint paint = getPaint();
        paint.setTypeface(typeface);
        paint.setTextSize(fontHeight);

        final double theta = angleGenerator.randomNext();
        final Word word = new Word(
                wordFrequency.getWord(), colorPalette.next(),
                paint, this.collisionChecker, theta
        );
       
        if (padding > 0) {
            padder.pad(word, padding);
        }
        return word;
    }

    private static int maxFrequency(final List<WordFrequency> wordFrequencies) {
        if (wordFrequencies.isEmpty()) { return 1; }

        return wordFrequencies.get(0).getFrequency();
    }

    private static Padder derivePadder(final CollisionMode collisionMode) {
        switch (collisionMode) {
            case PIXEL_PERFECT:
                return new WordPixelPadder();
            case RECTANGLE:
                return new RectanglePadder();
        }
        throw new IllegalArgumentException("CollisionMode can not be null");
    }

    private static CollisionChecker deriveCollisionChecker(final CollisionMode collisionMode) {
        switch (collisionMode) {
            case PIXEL_PERFECT:
                return new RectanglePixelCollisionChecker();
            case RECTANGLE:
                return new RectangleCollisionChecker();
        }
        throw new IllegalArgumentException("CollisionMode can not be null");
    }

    public void setBackgroundColor(final int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setPadding(final int padding) {
        this.padding = padding;
    }

    public void setColorPalette(final ColorPalette colorPalette) {
        this.colorPalette = colorPalette;
    }

    public void setBackground(final Background background) {
        this.background = background;
    }

    public void setFontScalar(final FontScalar fontScalar) {
        this.fontScalar = fontScalar;
    }

    public void setKumoFont(final KumoFont kumoFont) {
        this.kumoFont = kumoFont;
    }

    public void setAngleGenerator(final AngleGenerator angleGenerator) {
        this.angleGenerator = angleGenerator;
    }

    public Bitmap getBufferedImage() {
        return bufferedImage;
    }

    public Bitmap getBitmap() {
        return bufferedImage;
    }

    public Set<Word> getSkipped() {
        return skipped;
    }
    
    public void setWordStartStrategy(final WordStartStrategy wordStartStrategy) {
        this.wordStartStrategy = wordStartStrategy;
    }

    public void setWordPlacer(final RectangleWordPlacer wordPlacer) {
        this.wordPlacer = wordPlacer;
    }

}
