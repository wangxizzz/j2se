package 多线程.Java并发编程.day8延迟队列.example02;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author wangxi03 created on 2020/12/29 2:41 下午
 * @version v1.0
 */
@Setter
@Getter
public class WangMing implements Delayed {
    private String name;
    private long endTime;
    private TimeUnit timeUnit = TimeUnit.SECONDS;

    public WangMing(String name, long endTime) {
        this.name = name;
        this.endTime = endTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return endTime - System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed o) {
        WangMing wm = (WangMing) o;
        return this.getDelay(timeUnit) - wm.getDelay(timeUnit) > 0 ? 1 :
                (this.getDelay(timeUnit) - wm.getDelay(timeUnit) < 0 ? -1 : 0);
    }
}
