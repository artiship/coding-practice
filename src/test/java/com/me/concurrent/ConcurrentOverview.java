package com.me.concurrent;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ConcurrentOverview {
    private final static Logger LOGGER = LoggerFactory.getLogger(ConcurrentOverview.class);


    @Test public void
    /**
     * Executor is an interface that represents and object that executes
     * provided tasks.
     */
    executor() {
        class Invoker implements Executor {
            @Override
            public void execute(Runnable command) {
                command.run();
            }
        }

        Executor executor = new Invoker();
        executor.execute(() -> {
            LOGGER.info("executor run");
        });
    }

    @Test public void
    /**
     * ExecutorService is a complete solution for asynchronous processing. It manages an in-memory
     * queue and schedules submitted tasks bases on thread availability.
     */
    executorService() {
        class Task implements Runnable {
            @Override
            public void run() {
                LOGGER.info("do something...");
            }
        }

        ExecutorService executor = Executors.newFixedThreadPool(10);
        executor.submit(new Task());
        executor.submit(() -> {
            new Task();
        });

        try {
            executor.awaitTermination(20L, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test  public void
    /**
     * ScheduledExecutorServices is a similar interface to ExecutorService, but it can perform
     * tasks periodically
     */
    scheduledExecutorService() {
        ScheduledExecutorService executorService
                = Executors.newSingleThreadScheduledExecutor();

        Future<String> future = executorService
                .schedule(() -> "hello world", 1, TimeUnit.SECONDS);

        try {
            assertEquals(future.get(), "hello world");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test public void
    /**
     * Future is used to represent the result of an asynchronous option.
     * It comes with methods for checking the asynchronous operation is
     * completed or not, getting the computed result.
     */
    future() {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        Future<String> future = executor.submit(() -> {
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "hello world";
        });

        if(future.isDone() && !future.isCancelled()) {
            try {
                assertEquals(future.get(), "hello world");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        try {
            future.get(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }

    @Test public void
    /**
     * CountDownLatch is a utility class which blocks a set of threads
     * util some operation completes.
     */
    countdownLatch() {
        List<String> outputScrapper = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch countDownLatch = new CountDownLatch(5);

        List<Thread> workers = Stream
                .generate(() -> new Thread(() -> {
                    countDownLatch.countDown();
                    outputScrapper.add("Counted down");
                }))
                .limit(5)
                .collect(toList());

        workers.forEach(Thread::start);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        outputScrapper.add("Latch released");

        assertThat(outputScrapper,
                equalTo(Arrays.asList(
                        "Counted down",
                        "Counted down",
                        "Counted down",
                        "Counted down",
                        "Counted down",
                        "Latch released")));
    }

    @Test public void
    /**
     * A CyclicBarrier is synchronizer that allows a set of threads to wait for each other
     * to reach a common execution point, also called a barrier.
     */
    cyclicBarrier() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> {
            LOGGER.info("all previous tasks are completed.");
        });

        List<Thread> threads = Stream.generate(() -> new Thread(() -> {
            LOGGER.info(Thread.currentThread().getName() + " is waiting");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            LOGGER.info(Thread.currentThread().getName() + " is release");
        })).limit(3).collect(toList());


        if (!cyclicBarrier.isBroken()) {
            threads.forEach(Thread::start);
        }
    }

    @Test public void
    /**
     * the semaphore is used to block thread level of access to some part of the physical or logical resource.
     * A semaphore contains a set of permits.whenever a thread tries to enter the critical section,it needs to check
     * the semaphore if a permit is avialable or not
     */
    semaphore() throws InterruptedException {
        Semaphore semaphore = new Semaphore(10);

        assertThat(semaphore.availablePermits(), is(10));
        if(semaphore.tryAcquire()) {
            assertThat(semaphore.availablePermits(), is(9));

            semaphore.acquire();

            assertThat(semaphore.availablePermits(), is(8));

            LOGGER.info("Number of threads waiting to aquire: " + semaphore.getQueueLength());

            semaphore.release();
            assertThat(semaphore.availablePermits(), is(9));
        }

        semaphore.release();
        assertThat(semaphore.availablePermits(), is(10));
    }

    @Test public void
    /**
     * ThreadFactory acts as a thread pool which creates a new
     * thread on demand. It eliminates the need of boilerplate coding
     * for implementing efficient thread creation mechanisms
     */
    threadFactory() {
        class MyThreadFactory implements ThreadFactory {
            private int threadId;
            private String name;

            public MyThreadFactory(String name) {
                threadId = 1;
                this.name = name;
            }

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r, name + "-Thread_" + threadId);
                LOGGER.info("create new thread with id: " + threadId + " and name:" + t.getName());
                threadId++;
                return t;
            }
        }

        MyThreadFactory myThreadFactory = new MyThreadFactory("artiship");
        List<Thread> workers = Stream.generate(() -> myThreadFactory.newThread(() -> {
            LOGGER.info(Thread.currentThread().getName());
        })).limit(10).collect(Collectors.toList());

        workers.forEach(Thread::start);

    }
}
