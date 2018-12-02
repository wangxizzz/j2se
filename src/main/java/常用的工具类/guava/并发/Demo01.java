package 常用的工具类.guava.并发;

import com.google.common.collect.Comparators;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import com.google.common.util.concurrent.*;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Auther: wxi.wang
 * @Date: 18-11-28 15:35
 * @Description:
 */
public class Demo01 {
    public static final Logger LOGGER = LoggerFactory.getLogger(Demo01.class);
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

    /**
     * guava的并发
     */
    @Test
    public void test04() throws ExecutionException, InterruptedException {
        // 创建一个线程池
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(4));
        // 提交一任务
        ListenableFuture<String> future = service.submit(() -> {
            String s = "aaa";
            return s;
        });
        LOGGER.info(future.get());
        LOGGER.info("===============================");
        // 设置监听，执行Runnable里的run()
        future.addListener(() -> {
            LOGGER.info("给future设置监听");
        }, service);

        // 设置回调函数
        Futures.addCallback(future, new FutureCallback<String>() {
            @Override
            public void onSuccess(@Nullable String result) {
                LOGGER.info("成功了");
                LOGGER.info("result is {}", result);
                try {
                    LOGGER.info("future is {}", future.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                LOGGER.info("失败了");
            }
        }, service);
    }
}
