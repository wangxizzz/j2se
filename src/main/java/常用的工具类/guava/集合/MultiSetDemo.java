package 常用的工具类.guava.集合;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.util.Set;

/**
 * @author wxi.wang
 * 18-12-3
 *
 */
public class MultiSetDemo {
    public static void main(String[] args) {
        Multiset<Integer> set = HashMultiset.create();
        set.add(1);
        Set<Multiset.Entry<Integer>> entries = set.entrySet();
        for (Multiset.Entry e : entries) {
            System.out.println(e.getElement() + "元素出现的次数是：" + e.getCount());
        }

    }
}
