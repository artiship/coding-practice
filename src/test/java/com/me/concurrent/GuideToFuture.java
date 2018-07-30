package com.me.concurrent;

import org.junit.Test;

import java.util.concurrent.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * The future class is for asynchronous processing
 */
public class GuideToFuture {

    class SquareCalculator {

        private ExecutorService executor = Executors.newSingleThreadExecutor();

        public Future<Integer> calculate(Integer input) {
            return executor.submit(() -> {
                Thread.sleep(1000);
                return input * input;
            });
        }
    }

    @Test public void
    future_is_down_and_get() throws ExecutionException, InterruptedException {
        Future<Integer> future = new SquareCalculator().calculate(10);

        while(!future.isDone()) {
            System.out.println("calculating ....");
            Thread.sleep(300);
        }

        Integer result = future.get();

        assertThat(result, is(100));
    }

    @Test(expected = CancellationException.class) public void
    future_is_canceled () throws ExecutionException, InterruptedException {
        Future<Integer> future = new SquareCalculator().calculate(4);

        boolean canceled = future.cancel(   true);

        future.get();
    }
}
