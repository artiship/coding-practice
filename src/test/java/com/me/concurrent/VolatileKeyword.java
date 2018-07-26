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


}
