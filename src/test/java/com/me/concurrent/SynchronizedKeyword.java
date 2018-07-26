package com.me.concurrent;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SynchronizedKeyword {

    public class SynchronizedMethods {
         private int sum = 0;

        public void calculate() {
            setSum(getSum() + 1);
        }

        public synchronized void synchronizedCalculate() {
            setSum(getSum() + 1);
        }

        public void synchronizedBlock() {
            synchronized (this) {
                setSum(getSum() + 1);
            }
        }

        public int getSum() {
            return sum;
        }

        public void setSum(int sum) {
            this.sum = sum;
        }
    }

    @Test public void
    give_multi_thread_when_non_sync_method() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        SynchronizedMethods synchronizedMethods = new SynchronizedMethods();

        IntStream.range(0, 1000)
                .forEach(count -> executorService.submit(synchronizedMethods::calculate));

        executorService.awaitTermination(100, TimeUnit.MILLISECONDS);

        assertNotEquals(1000, synchronizedMethods.getSum());
    }

    @Test public void
    given_multi_thread_when_method_sync() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        SynchronizedMethods synchronizedMethods = new SynchronizedMethods();

        IntStream.range(0, 1000)
                .forEach(count -> executorService.submit(synchronizedMethods::synchronizedCalculate));

        executorService.awaitTermination(100, TimeUnit.MILLISECONDS);

        assertEquals(1000, synchronizedMethods.getSum());
    }

}
