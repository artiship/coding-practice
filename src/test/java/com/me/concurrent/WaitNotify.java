package com.me.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class WaitNotify {

    //1. wait() tells the calling thread to give up the monitor and go to sleep until some other
    //2. notify() wakes up the first thread that called wait() on the same object
    //3. notifyAll() wakes up all the threads that called wait() on the same object. the highest priority
    //thread will run first.

    //!! These methods can be used to implement producer consumer problem where consumer threads are waiting
    //for the objects in Queue and producer threads and producer threads put object in queue and notify the
    //waiting threads

    class Message {
        private String msg;

        public Message(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    public class Waiter implements Runnable {
        private Message msg;

        public Waiter(Message msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            synchronized (msg) {
                try{
                    System.out.println(name+" waiting to get notified at time:"+System.currentTimeMillis());
                    msg.wait();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println(name+" waiter thread got notified at time:"+System.currentTimeMillis());
                //process the message now
                System.out.println(name + " processed: ");
            }
        }
    }

    class Notifier implements Runnable {

        private Message msg;

        public Notifier(Message msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            System.out.println(name+" started");
            try {
                Thread.sleep(1000);
                synchronized (msg) {
                    msg.setMsg(name+" Notifier work done");
                    msg.notify();
                    // msg.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test public void
    test() {
        Message msg = new Message("process it");
        Waiter waiter = new Waiter(msg);
        new Thread(waiter,"waiter").start();

        Waiter waiter1 = new Waiter(msg);
        new Thread(waiter1, "waiter1").start();

        Notifier notifier = new Notifier(msg);
        new Thread(notifier, "notifier").start();
        System.out.println("All the threads are started");
    }

}
