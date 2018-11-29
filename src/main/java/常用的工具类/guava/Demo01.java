package 常用的工具类.guava;

import com.google.common.collect.Comparators;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Auther: wxi.wang
 * @Date: 18-11-28 15:35
 * @Description:
 */
public class Demo01 {
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

    /**
     * 读文件和写文件
     */
    @Test
    public void test03() throws IOException {
        String result = "ssssssssssssss";
        // 读文件，从resources文件夹中读取文件
        List<String> data = Resources.readLines(Resources.getResource("a.txt"), Charset.defaultCharset());
        // 写文件，把result写入文件，路径为当前的项目路径下。
        Files.asCharSink(new File(Demo01.class.getResource("/").getPath() + "validLineCount.txt"), Charset.defaultCharset())
                .write("" + result);

        // 通过URL读取资源文件，并包装为流对象
        Stream<String> dataStream = Resources.asCharSource(
                new URL("https://owncloud.corp.qunar.com/index.php/s/2mElvSWUJgppSBx/download"),
                Charset.defaultCharset())
                .lines();
        // 将输入流转化为输出流
        Stream<String> convertedStream = dataStream.map((line) -> line + "");
        // 写入文件
        Files.asCharSink(new File(Demo01.class.getResource("/").getPath() + "sdxl.txt"), Charset.defaultCharset())
                .writeLines(convertedStream);
    }

    @Test
    public void test04() {

    }
}
