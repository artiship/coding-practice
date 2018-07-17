package com.me.concurrent;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * It show how to gracefully shutdown an executor service
 * and wait for already running threads to finish their execution
 */
public class WaitingForThreadsToFinish {
    @Test public void
    awaitTerminationAfterShutdown() {
        ExecutorService threadPool = Executors.newFixedThreadPool(1);

        /**
         * no new tasks will accepted
         */
        threadPool.shutdown();
        try {
            /**
             * blocks until all tasks have completed after a shutdown
             * request
             */
            if(threadPool.awaitTermination(60, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            threadPool.shutdown();
            e.printStackTrace();
        }
    }
}
