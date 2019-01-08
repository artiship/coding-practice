package com.me.concurrent;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class YeildAndJoin {
    Logger log = LoggerFactory.getLogger(YeildAndJoin.class);

    @Test public void
    /**
     * A hint to the scheduler that the current thread is willing to yield
     * its current usage of a processor. The scheduler is free to ignore this
     * hint.
     */
    yield_example() {
        Thread producer = new Thread(() -> {
            for (int i = 0; i< 5; i++) {
                log.info("Producer: " + i);
                Thread.yield();
            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                log.info("Consumer: " + i);
                Thread.yield();
            }
        });

        producer.setPriority(Thread.MIN_PRIORITY);
        consumer.setPriority(Thread.MAX_PRIORITY);

        producer.start();
        consumer.start();
    }

    @Test public void
    /**
     * Waits for this thread to die.
     */
    join_example() {
        Thread t1 = new Thread(() -> {
            log.info("first task started");
            log.info("first task sleep 2 seconds");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            log.info("Second task completed");
        });

        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
    }

    private List<Integer> list = new ArrayList<>();

    @Test public void
    test_join() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            test();
        }
        list.clear();
    }

    public void test() throws InterruptedException {
        Runnable runnable = () -> {
            for (int i = 0; i < 10000; i++) {
                    list.add(i);
            }
        };


        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        Thread t3 = new Thread(runnable);

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();
        //Since ArrayList is not thread safe, this list size is random and might even cause ArrayIndexOutOfBoundsException
        //Assert.assertEquals(3 * 10000, list.size());
    }
}
