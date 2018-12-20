package com.me.concurrent;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Atomics {
    private AtomicInteger inc = new AtomicInteger(0);
    private ExecutorService executor = Executors.newFixedThreadPool(10);
    List<Future<?>> futures = new ArrayList<>();

    public void increase() {
        inc.getAndIncrement();
    }

    @Test
    public void test() {
        IntStream.range(0, 10).forEach(i ->
                futures.add(executor.submit(() -> IntStream.range(0, 1000).forEach(j -> increase())))
        );

        futures.forEach(f -> {
            try {
                f.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Assert.assertEquals(inc.get(), 10000);
    }
}
