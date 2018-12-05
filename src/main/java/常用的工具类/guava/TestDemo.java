package 常用的工具类.guava;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @author wxi.wang
 * 18-12-3
 */
public class TestDemo {
    @Test
    public void test01() {
        byte[] bytes = Ints.toByteArray(100);
        for (byte b : bytes) {
            System.out.print(b + " ");
        }
        int value = Ints.fromByteArray(bytes);
        System.out.println(value);
        int[] a = {1,2,3,4};
        String s = Ints.join("-", a);
        System.out.println(s);
        List<Integer> list = Ints.asList(a);
        System.out.println(list);
        int c = Ints.tryParse("F",16);
        System.out.println(c);
    }
    @Test
    public void test02() {
        List<String> list = Lists.newArrayList(Splitter.on(',').trimResults().omitEmptyStrings().split("foo,bar,,   qux"));
        System.out.println(list);
        // withKeyValueSeparator表示　key-value利用:切分
        Map<String, String> map = Splitter.on('#').withKeyValueSeparator(":").split("1:2#3:4");
        System.out.println(map);

    }
    @Test
    public void test03() {
        position:
        for (int i = 0; i < 5; i++) {
            System.out.println("i=" + i);
            for (int j = 0; j < 3; j++)  {
                System.out.println("j=" + j);
//                continue position;
                break ;
            }
        }
    }
    @Test
    public void test04() {
        // 指定连接符，拼接字符串
        String s = Joiner.on(' ').join(1,2,3);
        System.out.println(s);
    }
}
