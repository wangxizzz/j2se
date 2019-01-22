import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;


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
}
