package com.me.concurrent;

/**
 * In java, each thread has a separate memory space known as working memory;
 * This holds the values of different variables for performing operations.
 * After performing operations, thread copies the updated value of the variable
 * to the main memory, and from there other threads can read the latest value.
 *
 * Simply put, the volatile keyword marks a variable to always go to the main
 * memory, for both reads and writes, in the case of multiple threads accessing
 * it.
 */
public class VolatileKeyword {

    /**
     * 1. it can avoid race condition by ensuring the visibility.
     * 2. the atomic data types like AtomicInt or AtomicLong which also
     * can be used to avoid race condition.
     */
    class SharedClass {
        private volatile int count = 0;

        public void increamentCount() {
            count++;
        }

        public int getCount() {
            return count;
        }
    }


}
