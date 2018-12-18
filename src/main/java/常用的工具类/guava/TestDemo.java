package 常用的工具类.guava;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Stopwatch;
import com.google.common.base.Strings;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Comparators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
        // omitEmptyStrings移除结果子串中的空字符串，trimResults移除每个子串中的空格，比如前后空格
        List<String> list = Lists.newArrayList(Splitter.on(',').trimResults().omitEmptyStrings().split("foo,bar,,   qux"));
        System.out.println(list);
        Splitter.on(',').trimResults().omitEmptyStrings().split("foo,bar,,   qux");
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

    /**
     * 测试CharMatcher常用API
     */
    @Test
    public void test06() {
        System.out.println(CharMatcher.is('a').countIn("aabb"));
        // 首先是a，然后需要全部匹配后面的序列。也就是后面的序列需要全部都是a
        System.out.println(CharMatcher.is('a').matchesAllOf("aaaa"));
        // 是字符a,后面的字符序列只要一个a即可返回true
        System.out.println(CharMatcher.is('a').matchesAnyOf("aba"));
        // 后面的字符序列一个都不匹配a
        System.out.println(CharMatcher.is('a').matchesNoneOf("fdgdg"));
    }

    /**
     * 测试Stopwatch常见API
     */
    @Test
    public void test07() throws InterruptedException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Thread.sleep(2000);
        stopwatch.stop(); // optional
        long millis = stopwatch.elapsed(TimeUnit.SECONDS);
        System.out.println(millis);
    }

    /**
     * Lists常用API
     */
    @Test
    public void test08() {
        /**测试reverse()*/
        List<Integer> list = Lists.newArrayList(1,2,3);
        List<Integer> reverse = Lists.reverse(list);
        System.out.println(reverse);
        // 修改会反映的原list上
        reverse.add(100);
        System.out.println(reverse);
        System.out.println(list);
        /**测试partition,把list进行分区*/
        List<List<Integer>> partition = Lists.partition(Arrays.asList(1, 2, 3, 4), 2);
        System.out.println(partition);
        /**测试transform，转换list的元素**/
        List<Integer> transform = Lists.transform(list, i -> i + 1);
        System.out.println(transform);
    }

    /**
     * 测试Sets常用API
     */
    @Test
    public void test09() {
        Set<Integer> set1 = Sets.newHashSet(1,2,3,4);
        HashSet<Integer> set2 = Sets.newHashSet(2, 4, 5, 6);
        // 求并集
        Sets.SetView<Integer> union = Sets.union(set1, set2);
        System.out.println(union);
        // 求交集
        Sets.SetView<Integer> intersection = Sets.intersection(set1, set2);
        System.out.println(intersection);
        // 在A不在B中
        Sets.SetView<Integer> difference = Sets.difference(set1, set2);
        System.out.println(difference);
        // 传入的是一个回调方法，然后过滤部分值
        Set<Integer> filter = Sets.filter(set1, (i) -> i > 2);
        System.out.println(filter);
    }

    /**
     * 测试Ordering
     */
    @Test
    public void test10() {
        List<Integer> list = Lists.newArrayList(1,2,20,3,4,5);
        // for java8 users
        list.stream().collect(Comparators.greatest(3, (Integer x, Integer y) -> Integer.compare(x, y)));
        Ordering<Integer> ordering = new Ordering<Integer>() {
            @Override
            public int compare(@Nullable Integer left, @Nullable Integer right) {
                return Integer.compare(left, right);
            }
        };
        List<Integer> list1 = ordering.greatestOf(list, 3);
        System.out.println(list1);
    }
}
