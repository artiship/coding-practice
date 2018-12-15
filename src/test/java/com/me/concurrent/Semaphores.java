package com.me.concurrent;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Semaphores {
    class Semaphore {
        private  boolean signal = false;

        public void take() {
            this.signal = true;
            this.notify();
        }

        public void release() throws InterruptedException {
            if(!this.signal) this.wait();
            this.signal = false;
        }
    }

    class SendingThread implements Runnable {
        Semaphore semaphore = null;

        public SendingThread(Semaphore semaphore){
            this.semaphore = semaphore;
        }

        public void run(){
            while(true){
                //do something, then signal
                this.semaphore.take();
            }
        }
    }

    class RecevingThread implements Runnable {
        Semaphore semaphore = null;

        public RecevingThread(Semaphore semaphore){
            this.semaphore = semaphore;
        }

        public void run(){
            while(true){
                try {
                    this.semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //receive signal, then do something...
            }
        }
    }

    @Test
    public void simple_semaphore() {
        Semaphore semaphore = new Semaphore();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(new RecevingThread(semaphore));
        executor.submit(new SendingThread(semaphore));
    }
}

