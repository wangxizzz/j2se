package 常用的工具类.guava.集合;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author wxi.wang
 * 18-12-3
 */
public class MultiMap {
    public static void main(String[] args) {
        Multimap<Integer, Integer> map = HashMultimap.create();
        map.put(1,2);
        map.put(1,3);
        map.put(1,4);
        map.put(2,22);
        map.put(2,23);
        map.put(2,23);
        map.put(2,23);
        System.out.println(map);
        Collection<Map.Entry<Integer, Integer>> entries = map.entries();// 获取所有的Map.Entry
        System.out.println(entries);
        Set<Integer> integers = map.keySet();
        System.out.println(integers);
    }
}
