package 讲师代码.fresh.question3;

import com.google.common.io.Files;
import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Stream;

/**
 * Copyright (C) Qunar.com - All Rights Reserved.
 *
 * @author Mingxin Wang
 */
public final class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        // 通过URL读取资源文件，并包装为流对象
        Stream<String> dataStream;
        try {
            dataStream = Resources.asCharSource(
                    new URL("https://owncloud.corp.qunar.com/index.php/s/2mElvSWUJgppSBx/download"),
                    Charset.defaultCharset())
                    .lines();
        } catch (IOException e) {
            LOGGER.error("读取资源文件失败，请检查网络连接", e);
            return;
        }

        // 将prop文件组织为字典
        PropDictionary dict;
        try {
            // 按行读取prop文件
            List<String> propList = Resources.readLines(new URL("https://owncloud.corp.qunar.com/index.php/s/2pvS0d2Zs5onsF2/download"), Charset.defaultCharset());

            // 将prop文件组织为字典
            dict = PropDictionary.build(propList);
        } catch (ParsingException e) {
            LOGGER.error("解析prop文件失败", e);
            return;
        } catch (MalformedURLException e) {
            // 内部URL表示错误，如果代码无误这种情况不应该发生
            LOGGER.error("URL表示错误，内部错误", e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            LOGGER.error("读取网络资源失败，请检查网络连接", e);
            return;
        }

        // 将输入流转化为输出流
        Stream<String> convertedStream = dataStream.map(dict::convertLine);

        // 写入文件
        try {
            Files.asCharSink(new File(Main.class.getResource("/").getPath() + "sdxl.txt"), Charset.defaultCharset())
                    .writeLines(convertedStream);
        } catch (IOException e) {
            LOGGER.error("文件写入失败", e);
        }
    }
}
