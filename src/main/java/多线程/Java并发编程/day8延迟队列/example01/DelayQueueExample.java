package 多线程.Java并发编程.day8延迟队列.example01;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;

/**
 * @author wangxi created on 2020/9/18 00:40
 * @version v1.0
 *
 * 参考：https://blog.csdn.net/dkfajsldfsdfsd/article/details/88966814
 */
public class DelayQueueExample {
    public static void main(String[] args) {

        BlockingQueue<DelayTask> queue = new DelayQueue<DelayTask>();
        long currentTime = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            try {
                queue.put(new DelayTask("work " + i, 2000, currentTime, currentTime + i * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ThreadGroup g = new ThreadGroup("Consumers");

        for (int i = 0; i < 1; i++) {
            new Thread(g, new DelayTaskComsumer(queue)).start();
        }

        while (DelayTask.taskCount.get() > 0) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        g.interrupt();
        System.out.println("Main thread finished");
    }

}
