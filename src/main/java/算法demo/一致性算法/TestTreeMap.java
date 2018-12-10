package 算法demo.一致性算法;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author wxi.wang
 * 18-12-10
 * 测试TreeMap相关API
 * https://blog.csdn.net/x_i_y_u_e/article/details/46372023
 */
public class TestTreeMap {
    public static void main(String[] args) {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(30,3);
        map.put(23,2);
        map.put(13,4);
        map.put(100,4);
        map.put(10,4);
        // 返回TreeMap中的第一个元素(经过排序后)
        System.out.println(map.firstKey());
        // 得到所有大于参数23的数字中最小的那个
        Integer k = map.higherKey(23);
        System.out.println(k);
        // 返回此映射的部分视图，其键大于（或等于，如果 inclusive 为 true）fromKey
        SortedMap<Integer, Integer> subMap = map.tailMap(13);
        System.out.println(subMap);
        subMap.put(13, 1000);
        System.out.println(subMap);
        System.out.println(map);

    }
}
