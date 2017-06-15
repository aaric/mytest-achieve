package com.github.aaric.achieve.netty;

import java.util.Random;
import java.util.concurrent.*;

/**
 * FutureTaskTest
 *
 * @author Aaric, created on 2017-06-15T16:29.
 * @since 1.0-SNAPSHOT
 */
public class FutureTaskTest {

    /**
     * MyTask
     */
    public static class MyTask implements Callable<String> {

        @Override
        public String call() throws Exception {
            int sleep = new Random().nextInt(15);
            String threadName = Thread.currentThread().getName();
            System.out.println("The " + threadName + " sleep " + sleep + " ms.");
            TimeUnit.SECONDS.sleep(sleep);
            return threadName;
        }
    }

    /**
     * MyFutureTask
     */
    public static class MyFutureTask extends FutureTask<String> {

        public MyFutureTask(Callable<String> callable) {
            super(callable);
        }

        @Override
        protected void done() {
            try {
                System.err.println("The " + get() + " finished.");

            } catch (InterruptedException | ExecutionException e) {
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
        ExecutorService service = Executors.newCachedThreadPool();

        Callable callback;
        FutureTask futureTask;
        for (int i = 0; i < 10; i++) {
            callback = new MyTask();
            futureTask = new MyFutureTask(callback);
            service.submit(futureTask);
        }

        service.shutdown();
    }
}
