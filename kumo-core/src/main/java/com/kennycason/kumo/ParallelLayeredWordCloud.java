package com.kennycason.kumo;

import com.kennycason.kumo.compat.KumoGraphicsFactory;
import com.kennycason.kumo.compat.KumoRect;

import com.kennycason.kumo.exception.KumoException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * A LayeredWordCloud which can process each layer in its own Thread, thus
 * minimizing processing time.
 *
 * @author &#64;wolfposd
 */
public class ParallelLayeredWordCloud extends LayeredWordCloud {
    private final String TAG = "ParallelLayeredWordClou";

    private final Logger logger = Logger.getLogger(TAG);

    private final List<Future<?>> executorFutures = new ArrayList<>();

    private final ExecutorService executorservice;

    public ParallelLayeredWordCloud(
            final int layers,
            final KumoRect dimension,
            final CollisionMode collisionMode,
            final KumoGraphicsFactory graphicsFactory
    ) {
        super(layers, dimension, collisionMode, graphicsFactory);
        logger.setLevel(Level.INFO);
        executorservice = Executors.newFixedThreadPool(layers);
    }

    /**
     * constructs the wordcloud specified by layer using the given
     * wordfrequencies.<br>
     * This is a non-blocking call.
     *
     * @param layer
     *            Wordcloud Layer
     * @param wordFrequencies
     *            the WordFrequencies to use as input
     */
    @Override
    public void build(final int layer, final List<WordFrequency> wordFrequencies) {
        final Future<?> completionFuture = executorservice.submit(() -> {
            logger.info("Starting to build WordCloud Layer " + layer + " in new Thread");
            super.build(layer, wordFrequencies);
        });

        executorFutures.add(completionFuture);
    }

    private void waitForFuturesToBlockCurrentThread() {
        // we're not shutting down the executor as this would render it
        // non-functional afterwards. this way it can still be used if we
        // plan on building another layer on top of the previous ones
        logger.info("Awaiting Termination of Executors");

        for (int i = 0; i < executorFutures.size(); i++) {
            final Future<?> future = executorFutures.get(i);
            try {
                // cycle through all futures, invoking get() will block
                // current task until the future can return a result

                logger.info("Performing get on Future:" + (i + 1) + "/" + executorFutures.size());
                future.get();

            } catch (InterruptedException | ExecutionException e) {
                throw new KumoException("Error while waiting for Future of Layer " + i, e);
            }
        }
        executorFutures.clear();
        logger.info("Termination Complete, Processing to File now");
    }

    /**
     * Signals the ExecutorService to shutdown and await Termination.<br>
     * This is a blocking call.
     */
    public void shutdown() {
        executorservice.shutdown();
        try {
            executorservice.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new KumoException("Executor awaitTermination was interrupted, consider manual termination", e);
        }
    }

}
