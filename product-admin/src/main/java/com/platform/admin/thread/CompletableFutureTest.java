package com.platform.admin.thread;

import java.util.concurrent.*;

public class CompletableFutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Callable<Object>() {
            @Override
            public Object call() {
                System.out.println("executorService是否是守护线程" + Thread.currentThread().isDaemon());
                return null;
            }
        });
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("this is lambda supplyAsync");
            System.out.println("supplyAsync 是否为守护线程 " + Thread.currentThread().isDaemon());
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("this lambda is executed by forkJoinPool");
            return "result1";
        });
        executorService.shutdown();
    }
}
