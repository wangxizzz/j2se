package 多线程.Java并发编程.day8延迟队列;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author wangxi created on 2020/9/18 00:30
 * @version v1.0
 */
public class DelayQueueTest {
    // 可以用来执行定时任务
    static BlockingQueue<MyTask> task = new DelayQueue<>();

    public static void main(String[] args) throws InterruptedException {
        long now = System.currentTimeMillis();
        MyTask t1 = new MyTask(now + 1000);
        MyTask t2 = new MyTask(now + 2000);
        MyTask t3 = new MyTask(now + 1500);
        MyTask t4 = new MyTask(now + 2500);
        MyTask t5 = new MyTask(now + 500);

        task.put(t1);
        task.put(t2);
        task.put(t3);
        task.put(t4);
        task.put(t5);

        System.out.println(task);

        for (int i = 0; i < 5; i++) {
            System.out.println(task.take());
        }
    }

    static class MyTask implements Delayed {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        long runningTime;

        MyTask(long rt) {
            this.runningTime = rt;
        }

        @Override
        public int compareTo(Delayed o) {
            if (this.getDelay(TimeUnit.MICROSECONDS) < o.getDelay(TimeUnit.MICROSECONDS))
                return -1;
            else if (this.getDelay(TimeUnit.MICROSECONDS) > o.getDelay(TimeUnit.MICROSECONDS))
                return 1;
            else
                return 0;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(runningTime - System.currentTimeMillis(), TimeUnit.MICROSECONDS);
        }

        @Override
        public String toString() {
           return formatter.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(runningTime), ZoneId.of("Asia/Shanghai")));
        }
    }
}
