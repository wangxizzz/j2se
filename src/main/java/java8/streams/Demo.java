package java8.streams;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * 测试Stream
 */
public class Demo {
    @Test
    public void test01() {
        List<Integer> list = Arrays.asList(2,3,4,5,5,6,7,1,2);
        list.stream().filter(i -> i > 6)
                .forEach(i -> System.out.println(i));
        System.out.println("======================================");
        // 统计list里面元素的个数
        long count = list.stream().count();
        System.out.println(count);
        int count1 = list.stream().map(i -> i + 1).reduce(0, (a, b) -> a + b);
        System.out.println(count1);
    }
}
