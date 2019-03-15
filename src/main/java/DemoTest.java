import com.google.common.base.Splitter;
import org.junit.Ignore;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate.toString());
        for (int i = 1; i <= 89; i++) {
            System.out.println(localDate.plusDays(i).toString());
        }
    }

    @Test
    public void test04() {

        List<User> users = new ArrayList<>();
        users.add(new User(1, "22"));
        users.add(new User(12, "22"));
        System.out.println(users);

        Set<User> set = new HashSet<>();
        set.addAll(users);
        System.out.println(users);
        System.out.println(set);
    }
}
