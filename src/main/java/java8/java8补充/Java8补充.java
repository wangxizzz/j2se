package java8.java8补充;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * <Description>
 *
 * @author wangxi
 */
public class Java8补充 {
    /**
     * 4中对list排序方法
     * 对list集合自定义排序 采用Integer.compare()，可以有效地防止溢出
     */
    @Test
    public void testListSort() {
        List<Integer> list = new ArrayList<>();
        for (int i = 8; i > 0; i--) {
            list.add(i);
        }
        // 获得list里面的值.
        list.sort(Comparator.comparingInt(Integer::intValue));
        // 获得list里面的值
        list.sort(Integer::compare);
        System.out.println(list);
        list.sort((Integer o1, Integer o2) -> Integer.compare(o1, o2));
        System.out.println(list);
        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        });
        System.out.println(list);
    }

    /**
     * java8对list元素求和
     */
    @Test
    public void test01() {
        List<Integer> list = new ArrayList<>();
        for (int i = 4; i > 0; i--) {
            list.add(i);
        }
        int sum = list.stream().mapToInt(Integer::intValue).sum();
        System.out.println(sum);
    }
}

