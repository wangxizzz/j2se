package java8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class 排序 {
    /**
     * 对list集合自定义排序 采用Integer.compare()，可以有效地防止溢出
     */
    @Test
    public void testListSort() {
        List<Integer> list = new ArrayList<>();
        for (int i = 8; i > 0; i--) {
            list.add(i);
        }
        // 获得list里面的值.
        list.sort(Comparator.comparing(Integer::intValue));
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
}
