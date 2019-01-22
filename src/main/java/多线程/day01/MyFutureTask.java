package 多线程.day01;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author wxi.wang
 * 19-1-22
 */
public class MyFutureTask implements Callable<String> {
    @Override
    public String call() {
        return "aaaa";
    }

    public static void main(String[] args) throws InterruptedException {
        // FutureTask是Future,Runnable的具体实现类
        FutureTask<String> task = new FutureTask<>(new MyFutureTask());
        new Thread(task).start();
        try {
            String s = task.get();
            System.out.println(s);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
