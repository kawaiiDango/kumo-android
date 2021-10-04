package com.kennycason.kumo;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author joerg1985
 */
public class SpiralTest {

    @Test
    public void maxRadius() throws IOException {
        // draw the spiral as image?
        // red pixels -> only returned by the old implementation
        // blue pixels -> only returned by the new implementation
        // pink pixels -> returned by the old and the new implementation
        boolean debug = false;
        
        final int white = 0;
        final int red = 0xFFFF0000;
        final int blue = 0xFF0000FF;
        final int pink = red | blue;

        // we seed to get the same numbers on each run
        Random random = new Random(42);

        for (int i = 0; i < 20; i++) {
            Rect dimension = new Rect(
                    0,
                    0,
                    100 + random.nextInt(900),
                    100 + random.nextInt(900)
            );

            for (int j = 0; j < 20; j++) {
                Point start = new Point(
                        random.nextInt(dimension.width()),
                        random.nextInt(dimension.height())
                );

                List<Point> original = originalSpiral(
                        dimension, start, dimension.width()
                );
                List<Point> optimized = originalSpiral(
                        dimension, start, WordCloud.computeRadius(dimension, start)
                );

                Bitmap img = Bitmap.createBitmap(
                        dimension.width(), dimension.height(), Bitmap.Config.ARGB_8888
                );

                original.forEach((p) -> img.setPixel(p.x, p.y, red));
                optimized.forEach((p) -> {
                    if (img.getPixel(p.x, p.y) != 0) {
                        img.setPixel(p.x, p.y, pink);
                    } else {
                        img.setPixel(p.x, p.y, blue);
                    }
                });

                boolean next = false;
                
                for (int y = 0; !next && y < dimension.height(); y++) {
                    for (int x = 0; !next && x < dimension.width(); x++) {
                        int rgb = img.getPixel(x, y);

                        if (rgb == red) {
                            img.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream("output\\failed_spiral_test.png"));
                            Assert.fail();
                        } else if (debug && rgb != white && rgb != pink) {
                            img.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream("output\\debug_spiral_test_" + System.currentTimeMillis() + ".png"));
                            next = true;
                        } 
                    }
                }
            }
        }
    }
    
    @Test
    public void noIdenticalPoints() {
        // we seed to get the same numbers on each run
        Random random = new Random(42);

        for (int i = 0; i < 20; i++) {
            Rect dimension = new Rect(
                    0,
                    0,
                    100 + random.nextInt(900),
                    100 + random.nextInt(900)
            );

            for (int j = 0; j < 20; j++) {
                Point start = new Point(
                        random.nextInt(dimension.width()),
                        random.nextInt(dimension.height())
                );

                List<Point> points = optimizedSpiral(dimension, start);
                Set<Point> unique = new HashSet<>(points);
                
                Assert.assertEquals(
                        "no duplicate points", 
                        points.size(), unique.size()
                );
            }
        }
    }

    private List<Point> originalSpiral(Rect dimension, Point start, int maxRadius) {
        List<Point> points = new ArrayList<>();
        Point position = new Point();

        for (int r = 0; r < maxRadius; r += 2) {
            for (int x = -r; x <= r; x++) {
                if (start.x + x < 0) {
                    continue;
                }
                if (start.x + x >= dimension.width()) {
                    continue;
                }
                
                position.x = start.x + x; 

                // try positive root
                final int y1 = (int) Math.sqrt(r * r - x * x);
                if (start.y + y1 >= 0 && start.y + y1 < dimension.height()) {
                    position.y = start.y + y1; 
                    points.add(new Point(position));
                }
                // try negative root
                final int y2 = -y1;
                if (start.y + y2 >= 0 && start.y + y2 < dimension.height()) {
                    position.y = start.y + y2; 
                    points.add(new Point(position));
                }
            }
        }

        return points;
    }
    
    private List<Point> optimizedSpiral(Rect dimension, Point start) {
        final int maxRadius = WordCloud.computeRadius(dimension, start);
        
        List<Point> points = new ArrayList<>();
        Point position = new Point();

        for (int r = 0; r < maxRadius; r += 2) {
            for (int x = Math.max(-start.x, -r); x <= Math.min(r, dimension.width() - start.x - 1); x++) {
                position.x = start.x + x;

                final int offset = (int) Math.sqrt(r * r - x * x);
                
                // try positive root
                position.y = start.y + offset;
                if (position.y >= 0 && position.y < dimension.height()) {
                    points.add(new Point(position));
                }
                
                // try negative root (if offset != 0)
                position.y = start.y - offset;
                if (offset != 0 && position.y >= 0 && position.y < dimension.height()) {
                    points.add(new Point(position));
                }
            }
        }

        return points;
    }
}
