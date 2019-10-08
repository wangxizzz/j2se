package 常用的工具类.guava.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.junit.Test;

/**
 * Created by wxi.wang
 * <p>
 * 2019/10/8 17:30
 * Decription:
 */
public class Demo01 {

    @Test
    public void test01() {
        Cache<Integer, String> cache = CacheBuilder.newBuilder().build();
        cache.put(1, "a");
        System.out.println(cache.getIfPresent(1));
        System.out.println(cache.getIfPresent(2));
    }
}
