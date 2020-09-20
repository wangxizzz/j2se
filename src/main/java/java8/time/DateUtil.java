package java8.time;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author wangxi created on 2020/9/20 10:44
 * @version v1.0
 */
public class DateUtil {
    /**
     * long 毫秒数转换为字符串时间
     */
    @Test
    public void test01() {
        DateTimeFormatter df= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        String format = df.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(1471337924226L), ZoneId.of("Asia/Shanghai")));

        System.out.println(format);
    }
}
