package 常用的工具类.guava.集合;

import com.google.common.collect.Comparators;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wxi.wang
 * 18-12-2
 */
public class Demo01 {
    public static final Logger LOGGER = LoggerFactory.getLogger(Demo01.class);
    /**
     *list集合的使用
     */
    @Test
    public void test0１() {
        // 创建list
        List<Integer> list = Lists.newArrayList();

    }

    /**
     * Multiset的使用。统计list中出现次数最多的2个数
     */
    @Test
    public void test0２() {
        List<Integer> list = Lists.newArrayList();
        list.add(1);
        list.add(3);
        list.add(1);
        list.add(1);
        list.add(2);
        list.add(2);
        list.forEach((x) -> System.out.println(x));
        // 创建set
        Multiset<Integer> set = HashMultiset.create();
        set.addAll(list.stream().map(x -> x + 100)
                .collect(Collectors.toList()));
        List<Multiset.Entry<Integer>> result = set.entrySet().stream()
                .collect(Comparators.greatest(2, Comparator.comparingInt(Multiset.Entry::getCount)));

        for (Multiset.Entry<Integer> val : result) {
            System.out.println(val.getElement());
        }
    }
    @Test
    public void test03() {

    }
}
