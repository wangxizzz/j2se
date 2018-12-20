package java8.time;

import org.junit.Test;

import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Date;

/**
 * 日期date与时间time是分开的
 */
public class DateTest {

    @Test
    public void test01() {
               /* // 多线程会解析异常 sdf是30个线程共享变量
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                for (int x = 0; x < 100; x++) {
                    Date parseDate = null;
                    try {
                        parseDate = sdf.parse("20160505");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    System.out.println(parseDate);
                }
            }).start();
        }*/
    }
    @Test
    public void testLocalDate() {
        // 自定义日期
        LocalDate localDate = LocalDate.of(2016, 11, 13);
        System.out.println(localDate.getYear());
        System.out.println(localDate.getMonth());
        System.out.println(localDate.getMonthValue());
        System.out.println(localDate.getDayOfYear());
        System.out.println(localDate.getDayOfMonth());
        // 得到这一周的哪一天
        System.out.println(localDate.getDayOfWeek());

        localDate.get(ChronoField.DAY_OF_MONTH);
    }
    @Test
    public void testLocalTime() {
        // 可以拿到当前的时间
        LocalTime time = LocalTime.now();
        System.out.println(time.getHour());
        System.out.println(time.getMinute());
        System.out.println(time.getSecond());
    }

    @Test
    public void combineLocalDateAndTime() {
        // 获取当前日期
        LocalDate localDate = LocalDate.now();
        // 获取当前时间
        LocalTime time = LocalTime.now();

        LocalDateTime localDateTime = LocalDateTime.of(localDate, time);
        System.out.println(localDateTime.toString());
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);

    }

    /**
     * Instant表示一个时间点，当前瞬间。
     */
    @Test
    public void testInstant() throws InterruptedException {
        Instant start = Instant.now();
        Thread.sleep(1000L);
        Instant end = Instant.now();
        // Duration代表一个时间的片段，表示跨了多少时间
        Duration duration = Duration.between(start, end);
        System.out.println(duration.toMillis());
        System.out.println(duration.toHours());
    }

    @Test
    public void testDuration() {
        LocalTime time = LocalTime.now();
        // minusHours 表示subtracted减掉多长时间
        LocalTime beforeTime = time.minusHours(1);
        Duration duration = Duration.between(time, beforeTime);
        System.out.println(duration.toHours());
    }

    private static void testPeriod() {
        // Period表示时间跨度比较大。
        Period period = Period.between(LocalDate.of(2014, 1, 10), LocalDate.of(2016, 1, 10));
        System.out.println(period.getMonths());
        System.out.println(period.getDays());
        System.out.println(period.getYears());
    }

    /**
     * 格式化日期
     */
    @Test
    public void testDateFormat() {
        LocalDate localDate = LocalDate.now();
        String format1 = localDate.format(DateTimeFormatter.BASIC_ISO_DATE);
//        String format2 = localDate.format(DateTimeFormatter.ISO_LOCAL_TIME);
        System.out.println(format1);
//        System.out.println(format2);

        DateTimeFormatter mySelfFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String format = localDate.format(mySelfFormatter);
        System.out.println(format);

        // 格式化日期和时间
        DateTimeFormatter mySelfFormatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        String format2 = localDateTime.format(mySelfFormatter1);
        System.out.println(format2);
    }

    /**
     * 解析时间
     */
    private static void testDateParse() {
        String date1 = "20161113";
        LocalDate localDate = LocalDate.parse(date1, DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println(localDate);
        
        DateTimeFormatter mySelfFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date2 = "2016-11-13";
        LocalDate localDate2 = LocalDate.parse(date2, mySelfFormatter);
        System.out.println(localDate2);
    }
}