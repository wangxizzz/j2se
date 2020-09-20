package 多线程.Java并发编程.day8延迟队列.example01;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangxi created on 2020/9/18 00:38
 * @version v1.0
 */
public class DelayTask implements Delayed {
    protected final String taskName;
    protected final int timeCost;
    protected final long scheduleTime;

    protected static final AtomicInteger taskCount = new AtomicInteger(0);

    // 定时任务之间的启动时间间隔在1~2s之间，timeCost表示处理此任务需要的时间，本示例中为2s
    public DelayTask(String taskName, int timeCost, long currentTime, long delayedTime) {
        this.taskName = taskName;
        this.timeCost = timeCost;
        taskCount.incrementAndGet();
        scheduleTime = currentTime + delayedTime;
    }

    @Override
    public int compareTo(Delayed o) {
        return (int) (this.scheduleTime - ((DelayTask) o).scheduleTime);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long expirationTime = scheduleTime - System.currentTimeMillis();
        return unit.convert(expirationTime, TimeUnit.MILLISECONDS);
    }

    public void execTask() {
        long startTime = System.currentTimeMillis();
        System.out.println("Task " + taskName + ": schedule_start_time=" + scheduleTime + ",real start time="
                + startTime + ",delay=" + (startTime - scheduleTime));
        try {
            Thread.sleep(timeCost);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
