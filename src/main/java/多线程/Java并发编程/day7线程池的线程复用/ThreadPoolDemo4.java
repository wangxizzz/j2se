package 多线程.Java并发编程.day7线程池的线程复用;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author:王喜
 * @Description :
 * @Date: 2018/4/28 0028 19:40
 */
public class ThreadPoolDemo4 {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        for (int i = 0; i < 5; i++) {
            int index = i;
            executorService.execute(() -> divTask(100, index));
        }
        executorService.shutdown();
    }

    private static void divTask(int a, int b) {
        double result = a / b;
        System.out.println(result);
    }
}
