package 测试demos;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


/**
 * @author wxi.wang
 * 18-12-24
 */
public class Test02 {
    /**
     * HashMap key可以为空， ConcurrentHashMap key不能为null
     */
    @Test
    public void test01() {
        ConcurrentHashMap<String, Boolean> c = new ConcurrentHashMap<>();
        HashMap<String, String> map = new HashMap<>();
        map.put(null, null);
        c.put(null, null);
        c.put("11", false);
        System.out.println(c.get("aaa") == null);
        System.out.println(c.get("11") != null);
    }

    @Test
    public void test03() {   // 240
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate.toString());
        for (int i = 1; i <= 89; i++) {
            System.out.println(localDate.plusDays(i).toString());
        }
    }

    @Test
    public void test04() {

        List<User> users = new ArrayList<>();
        users.add(new User(1, "22"));
        users.add(new User(12, "22"));
        System.out.println(users);

        Set<User> set = new HashSet<>();
        set.addAll(users);
        System.out.println(users);
        System.out.println(set);
    }

    /**
     * 使用StringUtils.split切分字符串，会自动去掉空格
     * 切分字符串使用StringUtils.split， 避免转义的问题
     * 需要转移的有：
     * (    [     {    /    ^    -    $     |    }    ]    )    ?    *    +    .
     *
     * String.contains()方法是不需要转义的
     */
    @Test
    public void test05() {
        System.out.println(System.getProperty("user.dir"));
        int i = 0;
        System.out.println(i++ + "======" + (++i));

        String s = "I am   1  ";
        System.out.println(Arrays.toString(s.split(" ")));
        System.out.println(StringUtils.split(s).length);
        System.out.println("===========================");
        String s2 = "12345|2019-09-23";
        System.out.println(s2.split("|")[0]);   // 错误的切分方式
        /**
         * 正确的切分方式1 (不需要人工转义)
         */
        System.out.println(StringUtils.split(s2, "|")[0]);
        // 切分方式2 (需要人工转义)
        System.out.println(s2.split("\\|")[0]);
    }

    @Test
    public void test06() {
        List<String> list = new ArrayList<>();
        for (String s : list) {
            System.out.println("=====");
        }
        list.forEach((x) -> {
            System.out.println("-------");
        });

    }

    /**
     * 测试map.computeIfAbsent 与 JDK版的MultiMap
     */
    @Test
    public void test07() {
        Map<String, Integer> map = Maps.newHashMap();
        // 如果key不存在，那么value就是后面函数计算的值，其中k表示key值
        int a = map.computeIfAbsent("2", k -> Integer.parseInt(k));
        // 如果key存在，那么直接返回key对应的value
        int b = map.computeIfAbsent("2", k -> Integer.parseInt(k) + 100);
        System.out.println(map);
        System.out.println(a);
        System.out.println(b);

        Map<String, List<Integer>> map2 = new HashMap<>();
        map2.computeIfAbsent("1", k -> new ArrayList<>()).add(1);
        map2.computeIfAbsent("1", k -> new ArrayList<>()).add(1);
        System.out.println(map2);
        System.out.println(map2.get("1").getClass());
    }

    /**
     * 四舍五入
     */
    @Test
    public void test08() {
        long round1 = Math.round(2.5);
        System.out.println(round1);
        long round2 = Math.round(2.4);
        System.out.println(round2);
    }
}
