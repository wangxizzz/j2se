package java8.completableFuture;

import java.util.concurrent.*;

/**
 * JDK中Future的简单应用。
 */
public class FutureInAction2 {

    public static void main(String[] args)
            throws ExecutionException, InterruptedException, TimeoutException {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(() -> {
            try {
                Thread.sleep(10000L);
                return "I am finished.";
            } catch (InterruptedException e) {
                return "I am Error.";
            }
        });

        //...在等结果的时候，这里可以做一些自己的逻辑。
        //...在等结果的时候，这里可以做一些自己的逻辑。
        //...在等结果的时候，这里可以做一些自己的逻辑。
        //...
        /**
         * get()方法，是异步返回结果，如果结果一直没有来，就会一直阻塞。
         * 可以设置超时时间，在一定的时间，还没有得到结果就会抛出异常。这里是10 ms
         */
//        String value = future.get(10, TimeUnit.MICROSECONDS);
        while (!future.isDone()) {
            Thread.sleep(10);
        }
        System.out.println(future.get());
        /**
         * 线程池需要关闭，否则的话程序根本没有停。
         */
        executorService.shutdown();
    }
}