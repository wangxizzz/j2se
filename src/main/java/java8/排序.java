package java8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class 排序 {
    /**
     * 对list集合自定义排序 采用Integer.compare()
     */
    @Test
    public void testListSort() {
        List<Integer> list = new ArrayList<>();
        for (int i = 8; i > 0; i--) {
            list.add(i);
        }
        Collections.sort(list, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        });
        System.out.println(list);
    }
}
