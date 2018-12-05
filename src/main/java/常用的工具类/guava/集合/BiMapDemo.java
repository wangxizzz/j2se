package 常用的工具类.guava.集合;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.junit.Test;

/**
 * @author wxi.wang
 * 18-12-3
 */
public class BiMapDemo {
    /**
     * 测试inverse方法。
     */
    @Test
    public void test01() {
        HashBiMap<Integer,String> logfileMap = HashBiMap.create();
        logfileMap.put(1,"a.log");
        logfileMap.put(2,"b.log");
        logfileMap.put(3,"c.log");
        System.out.println("logfileMap:"+logfileMap);
        BiMap<String,Integer> filelogMap = logfileMap.inverse();
        System.out.println("filelogMap:"+filelogMap);

        logfileMap.put(4,"d.log");

        System.out.println("logfileMap:"+logfileMap);
        System.out.println("filelogMap:"+filelogMap);
    }

    /**
     * 测试添加put()
     */
    @Test
    public void test02() {
        BiMap<Integer,String> logfileMap = HashBiMap.create();
        logfileMap.put(1,"a.log");
        logfileMap.put(2,"b.log");
        logfileMap.put(3,"c.log");
        logfileMap.put(4,"d.log");
        logfileMap.put(5,"d.log");
    }

    /**
     * 测试强制添加
     */
    @Test
    public void test03() {
        BiMap<Integer,String> logfileMap = HashBiMap.create();
        logfileMap.put(1,"a.log");
        logfileMap.put(4,"d.log");
        logfileMap.forcePut(5,"d.log");
        System.out.println("logfileMap:"+logfileMap);
    }
}
