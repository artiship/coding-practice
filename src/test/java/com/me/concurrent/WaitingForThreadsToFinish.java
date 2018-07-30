package com.me.concurrent;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
            if(threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            threadPool.shutdownNow();
            e.printStackTrace();
        }
    }

    @Test public void
    using_count_down_latch() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(2);

        for(int i = 0; i < 2; i++) {
            executorService.submit(() -> {
                latch.countDown();
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class DelayedCallable implements Callable<String> {
        private String response;
        private int delay;

        public DelayedCallable(String response, int delay) {
            this.response = response;
            this.delay = delay;
        }

        @Override
        public String call() throws Exception {
            Thread.sleep(delay);
            return this.response;
        }
    }

    @Test public void
    using_invoke_all() throws InterruptedException, ExecutionException {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Callable<String>> callables = Arrays.asList(
                new DelayedCallable("fast thread", 100),
                new DelayedCallable("slow thread", 3000)
        );

        long startProcessingTime = System.currentTimeMillis();
        List<Future<String>> futures =  executorService.invokeAll(callables);

        long totalProcssingTime = System.currentTimeMillis() - startProcessingTime;

        assertTrue(totalProcssingTime > 3000);

        assertEquals(futures.get(0).get(), "fast thread");
        assertEquals(futures.get(1).get(), "slow thread");
    }


    @Test public void
    using_executor_completion_service() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CompletionService<String> service = new ExecutorCompletionService<>(executorService);

        List<Callable<String>> callables = Arrays.asList(
                new DelayedCallable("fast thread", 100),
                new DelayedCallable("slow thread", 3000)
        );

        callables.forEach(i -> service.submit(i));

        Future<String> future = service.take();
        Future<String> future1 = service.take();
    }

}
