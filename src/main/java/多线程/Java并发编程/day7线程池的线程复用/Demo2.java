package 多线程.Java并发编程.day7线程池的线程复用;

import java.util.concurrent.*;

/**
 * @Author:王喜
 * @Description :
 * @Date: 2018/4/28 0028 19:05
 */
public class Demo2 {
    public static void main(String[] args) {
        //创建线程池规范方式一  不规范可见ThreadPoolDemo一
        ExecutorService executorService = new ThreadPoolExecutor(2,
                2,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(10),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        //规范方式二
//        ThreadPoolExecutor executorService = new ThreadPoolExecutor() 增加参数

        //提交任务
        for (int i = 0; i < 10; i++) {
            int index = i;
            executorService.submit(() -> {
                System.out.println("i:" + index +
                        " executorService");
            });
        }

        executorService.shutdown();
    }
}
