import com.google.common.base.CaseFormat;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * Description
 * Date 2019/3/5 15:07
 */
@Slf4j
public class Test01 {
    @Test
    public void test01() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        Set<Integer> set = new HashSet<>();
        set.add(4);
        set.add(5);
        set.forEach((x) -> list.add(x));
        System.out.println(list);
    }

    @Test
    public void test02() {
        String s = "北京";
        String s1 = "bj";
        s = StringUtils.upperCase(s);
        s1 = StringUtils.upperCase(s1);
        System.out.println(s);
        System.out.println(s1);
        System.out.println(StringUtils.isAllUpperCase("北京"));
        System.out.println(StringUtils.isAllLowerCase("北京"));
        System.out.println(StringUtils.isAllLowerCase("@#、。，"));
        System.out.println(StringUtils.isAllUpperCase(".,/'~"));

    }

    @Test
    public void test03() {
        System.out.println("GET".equalsIgnoreCase("GeT"));
        Multimap<String, String> specialCityMap = LinkedHashMultimap.create();
        specialCityMap.put("1", "2");
        specialCityMap.put("1", "3");
        specialCityMap.put("1", "4");
        System.out.println(specialCityMap);
    }

    @Test
    public void test04() {
        Set<Integer> set = new LinkedHashSet<>();
        set.add(4);
        set.add(7);
        set.add(1);
        set.add(5);
        Iterator<Integer> iterator = set.iterator();
        //set.removeIf((x) -> x > 6);
        //System.out.println(set);
        while (iterator.hasNext()) {
            int val = iterator.next();
            if (val > 6) {
                iterator.remove();
            }
        }
        Iterator<Integer> i2 = set.iterator();
        System.out.println(i2.next());
        i2.remove();
        System.out.println(set);

        List<Integer> list = new ArrayList<>();
        list.addAll(set);
        System.out.println(list);
    }

    @Test
    public void test05() {
        BigDecimal a = new BigDecimal(100.00);
        BigDecimal b = new BigDecimal(300.00);
        BigDecimal c = a.add(b);
        System.out.println(c);
        System.out.println(c.compareTo(new BigDecimal(4001)));
    }

    @Test
    public void test06() {
        Map<Integer, String> map = new ImmutableMap.Builder<Integer, String>().put(1, "22").put(2, "33").build();
        System.out.println(map);
        ImmutableMap<Integer, Integer> of = ImmutableMap.of(1, 2, 4, 5);
        System.out.println(of);
    }

    @Test
    public void test07() {

    }

    @Test
    public void test08() {
        //String config1 = "-1:23434880,214324956:143440,1774384:1440,204:48430,24323243440";  // toMap的value为空，会报空指针

        String config = "-1:23,-1:23,214:143,177:1440,204:48430,204:4,24323:243440,24323:243441";
        /**
         * * @param mergeFunction a merge function, used to resolve collisions between
         *      *                      values associated with the same key, as supplied
         *      *                      to {@link Map#merge(Object, Object, BiFunction)}
         */
        Map<Integer, Integer> map = Arrays.stream(StringUtils.split(config, ","))
                .map(v -> StringUtils.split(v, ":"))
                .collect(Collectors.toMap(x -> Integer.valueOf(x[0]), y -> Integer.valueOf(y[1]), (o1, o2) -> o1, LinkedHashMap::new));
        System.out.println(map);

        System.out.println("======================");
        // 分割为Map<Integer, List<Integer>> 类型
        // 建议使用Multimap

    }

    @Test
    public void test09() {
        System.out.println(1 << 30);
        System.out.println((1 << 31) - 1);
        System.out.println(Integer.MAX_VALUE);
        System.out.println(-1 << 31);

        System.out.println('c' - 1);
    }

    /**
     * java.sql带的时间戳的测试
     */
    @Test
    public void test10() {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String tsString = ts.toString().substring(0, 19);
        String tsDate = ts.toString().substring(0, 10);
        System.out.println(tsDate);


        try {
            int i = 1/0;
        } catch (Exception e) {
            log.error("ss : ", e);
            System.out.println("null");
        }
        // 抛出受检异常，程序可以恢复，没有gg
        System.out.println("抛出异常的后的语句...");

        Queue<Integer> q = new LinkedList<>();
        q.offer(1);
        System.out.println(q.poll());
        System.out.println(q.poll());

        Stack<Integer> s = new Stack<>();
        s.push(1);
        System.out.println(s.pop());
        System.out.println(s.pop());
    }

    /**
     * 统计列表中单词的个数
     */
    @Test
    public void test11() {
        int nums[] = {1,2,3,2,2,1,5,6,6,5};
        Map<Integer, Integer> map = Maps.newHashMap();
        Arrays.stream(nums).forEach((x) -> map.merge(x, 1, (count, incre) -> count + incre));
        System.out.println(map);
    }
    /**
     * 统计列表中单词的个数与Top Ten
     */
    @Test
    public void test12() {
        List<Integer> list = Lists.newArrayList(1,2,3,2,2,1,5,6,6,5);
        Map<Integer, Long> freq = Maps.newHashMap();
        freq = list.stream().collect(groupingBy(Integer::intValue, counting()));
        System.out.println(freq);

        // Top 10
        List<Integer> topTen = freq.keySet().stream()
                .sorted(Comparator.comparing(freq::get).reversed())
                .limit(10)
                .collect(Collectors.toList());
        System.out.println(topTen);
    }

    /**
     * list视图与数组的clone
     */
    @Test
    public void test13() {
        List<Integer> list = Lists.newArrayList(1,2,3,4);
        list.subList(0,3).set(0, 100);
        System.out.println(list);

        int[] a = {1,2,3};
        int[] b = a.clone();
        b[0] = 1000; // 数组是深度clone
        System.out.println(Arrays.toString(a));
    }

   // 把毫秒转化为时间戳
    @Test
    public void test15() {
        long time = new Date().getTime();
        System.out.println(time);
        DateTimeFormatter ymdhmslinkedfmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        final String print = ymdhmslinkedfmt.print(1568649600000L);
        System.out.println(print);
        final Timestamp timestamp = new Timestamp(ymdhmslinkedfmt.parseMillis(print));
        System.out.println(timestamp);

        System.out.println("==================================");

        // 插入pgsql有时差,采用下面方式写法
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withZoneUTC();
        String format = formatter.print(time);
        System.out.println(DateUtil.getPGDetailImeStampFromString(format));
    }

    @Test
    public void test14() {
        long a = 1564209306730L;
        // 插入pgsql有时差,采用下面方式写法
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withZoneUTC();
        String format = formatter.print(a);
        System.out.println(DateUtil.getPGDetailImeStampFromString(format));

        System.out.println("asd".split(",").length);
    }

    @Test
    public void test16() {
        Set<String> set1 = new HashSet<String>() {
            {
                add("aa");
                add("bb");
                add("cc");
                add("dd");
            }
        };

        Set<String> set2 = new HashSet<String>() {
            {
                add("aa");
                add("ss");
                add("dd");
            }
        };
        // 在set1不在set2中
        final Sets.SetView<String> difference = Sets.difference(set1, set2);
        System.out.println(difference);
    }

    @Test
    public void test17() {
        Date date = DateUtil.convertDate("yyyy-MM-dd HH:mm:ss", "2019-09-17 00:00:00");
        System.out.println(Long.compare(1568649600000L, date.getTime()));
        System.out.println(date);
        System.out.println(date.getTime());
    }

    /**
     * 去除字符串的空格与多余的引号
     */
    @Test
    public void test18() {
        Map<String, Integer> soldCountWithDay = Maps.newHashMap();
        String realSoldCountByDays = "\"7\"=>\"3\", \"15\"=>\"3\", \"30\"=>\"3\", \"75\"=>\"8\", \"90\"=>\"8\", \"180\"=>\"16\", \"365\"=>\"16\"";
        List<String> parts = Splitter.on(",").trimResults().splitToList(realSoldCountByDays);
        for (String p : parts) {
            List<String> dayAndCount = Splitter.on("=>").trimResults().trimResults(CharMatcher.is('"')).splitToList(p);
            if (dayAndCount.size() < 2) {
                soldCountWithDay.put(dayAndCount.get(0), 0);
            } else {
                soldCountWithDay.put(dayAndCount.get(0), Integer.parseInt(dayAndCount.get(1)));
            }
        }
        System.out.println(soldCountWithDay);
    }
}