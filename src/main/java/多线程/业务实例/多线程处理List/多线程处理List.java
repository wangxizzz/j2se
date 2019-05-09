package 多线程.业务实例.多线程处理List;

import 常用的工具类.guava.并发.CompletableFutures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <Description>
 *  面对大数据量的List集合的数据处理
 *  在大数据量的集合中查找
 * @author wangxi
 */
public class 多线程处理List {
    public static final int MAX_THREAD = 1000;
    private static ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREAD);
    private static String result = "4553543";
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        Random random = new Random();
        List<String> list = new ArrayList<>(10000000);
        for (int i = 0; i < 10000000; i++) {
            list.add(random.nextInt(1000000000) + "");
        }
        System.out.println("===========================================");
        //findElementByNThread(list);
        findElementByIterator(list);
    }

    public static void findElementByNThread(List<String> list) {
        long start = System.currentTimeMillis();
        List<CompletableFuture> completableFutures = new ArrayList<>();
        for (int i = 0; i < MAX_THREAD; i++) {
            List<String> temp = list.subList(i * 10000, (i + 1) * 10000);
            CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
//                System.out.println(Thread.currentThread().getName());
                for (String val : temp) {
                    if (val.equals(result)) {
                        System.out.println("findElementByNThread val = " + val);
                        break;
                    }
                }
            }, executorService);
            completableFutures.add(completableFuture);
        }
        CompletableFuture
                .anyOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()]));
        System.out.println("findElementByNThread : " + (System.currentTimeMillis() - start));
        executorService.shutdown();
    }

    public static void findElementByIterator(List<String> list) {
        long start = System.currentTimeMillis();
        for (String val : list) {
            if (val.equals(result)) {
                System.out.println("findElementByIterator val = " + val);
                break;
            }
        }
        System.out.println("findElementByIterator : " + (System.currentTimeMillis() - start));
    }
}

