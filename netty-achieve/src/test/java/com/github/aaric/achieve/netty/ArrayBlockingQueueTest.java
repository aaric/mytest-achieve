package com.github.aaric.achieve.netty;

import java.text.MessageFormat;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * ArrayBlockingQueueTest
 *
 * @author Aaric, created on 2017-06-15T13:01.
 * @since 1.0-SNAPSHOT
 */
public class ArrayBlockingQueueTest {

    /**
     * Producer
     */
    public static class Producer implements Runnable {

        private BlockingQueue<String> queue;

        public Producer(BlockingQueue<String> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                String msg;
                for (int i = 1; i <= 100; i++) {
                    msg = MessageFormat.format("msg-{0,number,0000}", i);
                    queue.put(msg);
                    System.out.printf("Producer: %s\n", msg);
                }
                queue.put("exit");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Consumer
     */
    public static class Consumer implements Runnable {

        private BlockingQueue<String> queue;

        public Consumer(BlockingQueue<String> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                String msg;
                while (!"exit".equals(msg = queue.take())) {
                    System.err.printf("Consumer: %s\n", msg);
                    Thread.sleep(100L);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Main
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        new Thread(producer).start();
        new Thread(consumer).start();

        System.out.println("over!");
    }
}
