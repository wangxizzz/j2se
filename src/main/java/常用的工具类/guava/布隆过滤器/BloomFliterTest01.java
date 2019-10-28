package 常用的工具类.guava.布隆过滤器;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.Test;

import java.util.stream.IntStream;

/**
 * Created by wxi.wang
 * <p>
 * 2019/10/28 19:27
 * Decription:
 * guava的布隆过滤器
 * 误判：可能会把不是集合内的元素判定为存在于集合内
 * https://segmentfault.com/a/1190000012620152
 * https://juejin.im/post/5cc5aa7ce51d456e431adac5
 */
public class BloomFliterTest01 {
    @Test
    public void test01() {
        BloomFilter<Integer> filter = BloomFilter.create(
                Funnels.integerFunnel(),
                500,
                0.01);
        filter.put(1);
        filter.put(2);
        filter.put(3);
        System.out.println(filter.mightContain(1));
        System.out.println(filter.mightContain(2));
        System.out.println(filter.mightContain(3));
    }

    @Test
    public void test02() {
        BloomFilter<Integer> filter = BloomFilter.create(
                Funnels.integerFunnel(),
                5,
                0.01);
        IntStream.range(0, 10000).forEach(filter::put);
        System.out.println(filter.mightContain(23));
    }
}
