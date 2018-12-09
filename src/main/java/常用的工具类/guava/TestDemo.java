package 常用的工具类.guava;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.primitives.Ints;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @author wxi.wang
 * 18-12-3
 * 测试guava中的API.勿删，重点看看！！！
 */
public class TestDemo {
    /**
     * 测试Ints的常用ＡＰＩ
     */
    @Test
    public void test01() {
        // 把int变为byte数组
        byte[] bytes = Ints.toByteArray(100);
        for (byte b : bytes) {
            System.out.print(b + " ");
        }
        // 把byte数组转换成int
        int value = Ints.fromByteArray(bytes);
        System.out.println(value);
        int[] a = {1,2,3,4};
        // 通过连接符把int数组元素连接起来，返回String
        String s = Ints.join("-", a);
        System.out.println(s);
        // 它的功能是将给定的整形数组转换为List
        List<Integer> list = Ints.asList(a);
        System.out.println(list);
        // 按照给定的进制与参数解析为int类型
        int c = Ints.tryParse("F",16);
        System.out.println(c);
    }

    /**
     * 测试Strings常见API
     */
    @Test
    public void test02() {
        // repeat把字符串重复count次
        String s1 = Strings.repeat("hello", 3);
        System.out.println(s1);
        // 寻找两个字符串的公共前缀和后缀
        String s2 = "abcdefght";
        String s3 = "abcght";
        System.out.println(Strings.commonPrefix(s2, s3));
        System.out.println(Strings.commonSuffix(s2, s3));
        String s4 = "abcght";
        System.out.println(Strings.commonPrefix(s3, s4));
    }

    /**
     * 测试Joiner的常见API
     */
    @Test
    public void test03() {
        // 指定连接符，拼接字符串
        String s = Joiner.on('|').join(1,2,3);
        System.out.println(s);
        // 把一个Map拼接成String
        Map<Integer, Integer> map = Maps.newHashMap();
        map.put(1,2);
        map.put(2,3);
        String join = Joiner.on("#").withKeyValueSeparator("=").join(map);
        System.out.println(join);
    }
    /**
     * 测试Splitter
     */
    @Test
    public void test04() {
        List<String> list = Lists.newArrayList(Splitter.on(',').trimResults().omitEmptyStrings().split("foo,bar,,   qux"));
        System.out.println(list);
        // withKeyValueSeparator表示　key-value利用:切分.把一个String分隔组合成Map
        Map<String, String> map = Splitter.on('#').withKeyValueSeparator(":").split("1:2#3:4");
        System.out.println(map);

    }

    /**
     * 测试惰性求值
     * https://my.oschina.net/bairrfhoinn/blog/142985
     */
    @Test
    public void test05() {
        Supplier<Integer> supplier = new Supplier<Integer>() {
            @Override
            public Integer get() {
                // 经过了很长时间的计算
                return 100;
            }
        };
        // 惰性求值，只有调用get()才会进行真正的计算
        System.out.println(supplier.get());
        // 可以把上步计算的结果保存下来，防止重复计算
        Supplier<Integer> memoize = Suppliers.memoize(supplier);

    }
}
