package 讲师代码.fresh.question1;

import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.List;

/**
 * Copyright (C) Qunar.com - All Rights Reserved.
 *
 * @author Mingxin Wang
 */
public final class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        // 从资源文件中读取每一行数据
        List<String> data;
        try {
            data = Resources.readLines(Resources.getResource("a.txt"), Charset.defaultCharset());
        } catch (IOException e) {
            LOGGER.error("资源读取失败", e);
            return;
        }

        // 将日志加载至统计器
        Statistic statistic = new Statistic();
        data.forEach(line -> {
            try {
                statistic.add(line);
            } catch (ParseException e) {
                LOGGER.warn("不可识别的日志条目", e);
            }
        });

        // 生成统计数据
        String result = statistic.getResult();

        LOGGER.info("统计结果：{}", result);
    }
}
