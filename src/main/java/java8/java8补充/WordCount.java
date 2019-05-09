package java8.java8补充;

import org.junit.Test;

import java.nio.file.Files;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * <Description>
 * 利用java8实现wordCount.  测试flatMap,map的用法
 * @author wangxi
 */
public class WordCount {
    private String[] nums = {"public static void", "public class Demo", "static void main"};

    // 测试map与flatMap的返回值
    @Test
    public void test01() {
        // 输出Stream地址
        Arrays.stream(nums).map(x -> Stream.of(x.split(" ")))
                .forEach(System.out::println);

        // 会输出流中的元素
        Arrays.stream(nums).flatMap((x -> Stream.of(x.split(" "))))
                .forEach(System.out::println);
    }

    // WordCount写法
    @Test
    public void test02() {
        Arrays.asList(nums).parallelStream()
                .map(x -> x.split(" "))
                .flatMap(strs -> Arrays.stream(strs))
                .forEach(System.out::print);
        System.out.println("===============================================");
        Arrays.asList(nums).parallelStream()
                .map(x -> x.split(" "))
                .flatMap(strs -> Arrays.stream(strs))
                .filter(word -> word.length() > 0)
                .map(word -> new AbstractMap.SimpleEntry<>(word, 1))
                .collect(groupingBy(AbstractMap.SimpleEntry :: getKey, counting()))
                .entrySet().forEach(System.out :: println);
    }
}

