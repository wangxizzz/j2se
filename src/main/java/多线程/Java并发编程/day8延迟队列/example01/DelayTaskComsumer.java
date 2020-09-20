package 多线程.Java并发编程.day8延迟队列.example01;

import java.util.concurrent.BlockingQueue;

/**
 * @author wangxi created on 2020/9/18 00:40
 * @version v1.0
 */
public class DelayTaskComsumer extends Thread {
    private final BlockingQueue<DelayTask> queue;

    public DelayTaskComsumer(BlockingQueue<DelayTask> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        DelayTask task = null;
        try {
            while (true) {
                task = queue.take();
                task.execTask();
                DelayTask.taskCount.decrementAndGet();
            }
        } catch (InterruptedException e) {
            System.out.println(getName() + " finished");
        }
    }

}
