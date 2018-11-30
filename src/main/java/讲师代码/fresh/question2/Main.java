package 讲师代码.fresh.question2;

import com.google.common.io.Files;
import com.google.common.io.Resources;
import org.apache.commons.lang3.mutable.MutableInt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.function.Consumer;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static final String START_COMMENT_TOKEN = "<!--";
    private static final String END_COMMENT_TOKEN = "-->";

    // 计算有效行数
    private static int calcValidLines(Iterable<String> data) {
        // 共享的结果记录区域
        MutableInt result = new MutableInt(0);

        // 逐行处理
        data.forEach(new Consumer<String>() {
            // 记录目前是否在一个跨行的块注释之内
            boolean commentBlock = false;

            @Override
            public void accept(String s) {
                if (commentBlock) {
                    int endBlockIndex = s.indexOf(END_COMMENT_TOKEN);
                    if (endBlockIndex != -1) {
                        commentBlock = false;
                        accept(s.substring(endBlockIndex + END_COMMENT_TOKEN.length()));
                    }
                    return;
                }
                boolean isValid = false;
                int validIndex = 0;
                for (;;) {
                    int startCommentIndex = s.indexOf(START_COMMENT_TOKEN, validIndex);
                    if (!s.substring(validIndex, startCommentIndex == -1 ? s.length() : startCommentIndex).trim().isEmpty()) {
                        isValid = true;
                    }
                    if (startCommentIndex == -1) {
                        break;
                    }
                    int endCommentIndex = s.indexOf(END_COMMENT_TOKEN, startCommentIndex + START_COMMENT_TOKEN.length());
                    if (endCommentIndex == -1) {
                        commentBlock = true;
                        break;
                    }
                    validIndex = endCommentIndex + END_COMMENT_TOKEN.length();
                }
                if (isValid) {
                    result.increment();
                }
            }
        });
        return result.getValue();
    }

    public static void main(String[] args) {
        // 从资源文件中读取每一行数据
        List<String> data;
        try {
            data = Resources.readLines(Resources.getResource("StringUtils.java"), Charset.defaultCharset());
        } catch (IOException e) {
            LOGGER.error("资源读取失败", e);
            return;
        }

        // 计算有效行
        int result = calcValidLines(data);

        // 写入到文件
        try {
            Files.asCharSink(new File(Main.class.getResource("/").getPath() + "validLineCount.txt"), Charset.defaultCharset())
                    .write("" + result);
        } catch (IOException e) {
            LOGGER.error("文件写入失败", e);
        }
    }
}
