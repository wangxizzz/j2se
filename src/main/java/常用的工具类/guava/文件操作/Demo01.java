package 常用的工具类.guava.文件操作;

import com.google.common.io.Files;
import com.google.common.io.Resources;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author wxi.wang
 * 18-12-2
 */
public class Demo01 {
    public static final Logger LOGGER = LoggerFactory.getLogger(常用的工具类.guava.并发.Demo01.class);
    /**
     * 读文件和写文件
     */
    @Test
    public void test01() throws IOException {
        String result = "ssssssssssssss";
        // 读文件，从resources文件夹中读取文件
        List<String> data = Resources.readLines(Resources.getResource("a.txt"), Charset.defaultCharset());
        // 写文件，把result写入文件，路径为当前的项目路径下。
        Files.asCharSink(new File(常用的工具类.guava.并发.Demo01.class.getResource("/").getPath() + "validLineCount.txt"), Charset.defaultCharset())
                .write("" + result);

        // 通过URL读取资源文件，并包装为流对象
        Stream<String> dataStream = Resources.asCharSource(
                new URL("a.txt"),
                Charset.defaultCharset())
                .lines();
        // 将输入流转化为输出流
        Stream<String> convertedStream = dataStream.map((line) -> line + "");
        // 写入文件
        Files.asCharSink(new File(常用的工具类.guava.并发.Demo01.class.getResource("/").getPath() + "sdxl.txt"), Charset.defaultCharset())
                .writeLines(convertedStream);
    }
}
