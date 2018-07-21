package com.me.concurrent;

import com.sun.xml.internal.ws.api.pipe.Fiber;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;

public class RunnableAndCallable {

    private ExecutorService executorService;

    @Before
    public void setUp() {
        executorService = Executors.newSingleThreadExecutor();
    }

    @Test public void
    /**
     *  The Runnable interface is a functional interface and has a single
     *  run() method which doesn't accept any parameters and does not return
     *  any values.
     */
    runnable() {
        class EventLoggingTask implements Runnable {
            private Logger logger = LoggerFactory.getLogger(EventLoggingTask.class);

            @Override
            public void run() {
                logger.info("message");
            }
        }


        Future future = executorService.submit(new EventLoggingTask());
        try {
            //This future object will not hold any value
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }

    @Test public void
    /**
     * The Callable interface is a generic interface containing a single
     * call() method - which returns a generic value V.
     */
    withCallable() {
        class FactorialTask implements Callable<Integer> {
            int number;

            public FactorialTask(int number) {
                this.number = number;
            }

            @Override
            public Integer call() throws Exception {
                int fact = 1;
                for (int count = number; count > 1; count--) {
                    fact = fact * count;
                }
                return fact;
            }
        }

        Future<Integer> future = executorService.submit(new FactorialTask(5));

        try {
            assertEquals(120, future.get().intValue());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    
    @Test public void
    for_example_exception_handling() {

    }
}
