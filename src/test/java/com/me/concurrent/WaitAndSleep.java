package com.me.concurrent;

import org.junit.Test;

/**
 * In general, we should use sleep() for controlling execution time
 * for one thread and wait() fro multi-thread-synchronization.
 */
public class WaitAndSleep {

    private Object lock = new Object();

    @Test public void
    sleep_wait_example() throws InterruptedException {
        /**
         * Thread pauses the current thread and does not releases any lock
         */
        Thread.sleep(1000);

        System.out.println("thread " + Thread.currentThread().getName()
            + "is woken after sleeping for 1 second");

        synchronized (lock) {
            // can be only called from a synchronized block
            lock.wait(1000);
            System.out.println("Object " + lock + " is woken after " +
             "waiting for 1 second.");
        }
    }

}
