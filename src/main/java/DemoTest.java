import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.LockSupport;


/**
 * @author wxi.wang
 * 18-12-24
 */
public class DemoTest {
    /**
     * 测试可调度的线程池
     */
    @Test
    public void test01() {
        ConcurrentHashMap<String, Boolean> c = new ConcurrentHashMap<>();
        c.put("11", false);
        System.out.println(c.get("aaa") == null);
        System.out.println(c.get("11") != null);
    }

    @Test
    public void test02() {
        AtomicLong atomicLong = new AtomicLong(1);
        atomicLong.getAndIncrement();
        atomicLong.getAndIncrement();
//        atomicLong.addAndGet(10);
        atomicLong.incrementAndGet();
        System.out.println(atomicLong.get());
    }

    @Test
    public void test03() {   // 240
        LongAdder longAdder = new LongAdder();

    }
}
