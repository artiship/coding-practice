package com.me.concurrent;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Locks is utility for blocking other threads from accessing a certain
 * segment of code, apart from the thread that's executing it currently.
 *
 * The main difference between a Lock and Synchronized block is that synchronized
 * block is fully contained in method; however, we can hbase Lock API's lock() and
 * unlock() operation in separate methods.
 */
public class CocurrentLocks {
    @Test public void
    reentrantLock() {
        ReentrantLock lock = new ReentrantLock();
        int counter = 0;

        lock.lock();
        try {
            counter++;
        } finally {
            lock.unlock();
        }

        try {
            boolean isLockAquired = lock.tryLock(1, TimeUnit.SECONDS);

            if (isLockAquired) {
                //critical
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
