package 多线程.Java并发编程.day7线程池的线程复用;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author:王喜
 * @Description : 对ThreadPoolDemo2的改良
 * @Date: 2018/4/28 0028 19:40
 */
public class ThreadPoolDemo3 {
    /**
     * execute和submit的区别

     （1）execute()方法用于提交不需要返回值的任务，所以无法判断任务是否被线程池执行成功。
     通过以下代码可知execute()方法输入的任务是一个Runnable类的实例。

     （2）submit()方法用于提交需要返回值的任务。线程池会返回一个future类型的对象，
         通过这个future对象可以判断任务是否执行成功，并且可以通过future的get()方法来获取返回值，
         get()方法会阻塞当前线程直到任务完成，而使用get（long timeout，TimeUnit unit）方法则会阻塞当前线程一段时间后立即返回，
         这时候有可能任务没有执行完。
     */
    public static void main(String[] args) {
        //为了测试方便
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            int index = i;
            Future future = executorService.submit(() -> {
                divTask(100, index);
            });
            try {
                future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
    }

    private static void divTask(int a, int b) {
        //此时有异常不会抛出
        double result = a / b;
        System.out.println(result);
    }
}
