package 多线程.Java并发编程.day7线程池的线程复用;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author:王喜
 * @Description : 测试submit()
 * @Date: 2018/4/28 0028 19:22
 */
public class ThreadPoolDemo2 {

    public static void main(String[] args) {
        //为了测试方便
        ExecutorService executorService =  Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            int index = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    divTask(100, index);
                }
            });
        }
        executorService.shutdown();
    }

    private static void divTask(int a, int b) {
        //此时有异常不会抛出
        double result = a / b;
        System.out.println(result);
    }
}
