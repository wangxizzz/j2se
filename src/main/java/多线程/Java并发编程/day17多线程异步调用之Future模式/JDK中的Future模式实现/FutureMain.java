package 多线程.Java并发编程.day17多线程异步调用之Future模式.JDK中的Future模式实现;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @Author:王喜
 * @Description : 主类 用来处理Client的请求
 * @Date: 2018/5/1 0001 15:45
 */
public class FutureMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        /**
         * 通过：FutureTask<String> futureTask = new FutureTask<>(new RealData("Hello"));
         * 这一行构造了一个futureTask 对象，表示这个任务是有返回值的，返回类型为String
         */
        FutureTask<String> futureTask = new FutureTask<>(new RealData("Hello"));

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(futureTask);

        System.out.println("请求完毕！");

        //异步操作返回数据
        System.out.println("真实数据：" + futureTask.get());
        executorService.shutdown();
    }
}
