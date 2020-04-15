package 算法demo.一致性算法;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wxi.wang
 * 18-12-10
 */
public class StringHashCodeTest {
    public static void main(String[] args) {
        System.out.println("192.168.0.0:111的哈希值：" + "192.168.0.0:1111".hashCode());
        System.out.println("192.168.0.1:111的哈希值：" + "192.168.0.1:1111".hashCode());
        System.out.println("192.168.0.2:111的哈希值：" + "192.168.0.2:1111".hashCode());
        System.out.println("192.168.0.3:111的哈希值：" + "192.168.0.3:1111".hashCode());
        System.out.println("192.168.0.4:111的哈希值：" + "192.168.0.4:1111".hashCode());

        Map<Integer, Integer> hashMap = new HashMap<>();
        hashMap.put(1, null);

        Map<Integer, Integer> chm = new ConcurrentHashMap<>();
        // 不可存储Null
        //chm.put(1, null);

        Map<Integer, Integer> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put(1, null);
    }
}
