import com.google.common.collect.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
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

        String config = "-1:23434880,214324956:143440,1774384:1440,204:48430,204:4,24323:243440";
        /**
         * * @param mergeFunction a merge function, used to resolve collisions between
         *      *                      values associated with the same key, as supplied
         *      *                      to {@link Map#merge(Object, Object, BiFunction)}
         */
        Map<Integer, Integer> map = Arrays.stream(StringUtils.split(config, ",")).map(v -> StringUtils.split(v, ":"))
                .collect(Collectors.toMap(v -> Integer.valueOf(v[0]), v -> Integer.valueOf(v[1]), (o1, o2) -> o1, LinkedHashMap::new));
        System.out.println(map);
    }

    @Test
    public void test09() {
        int a = -123;
        System.out.println(a % 10);
        System.out.println("9223372036854775808".length());
        System.out.println(Long.MAX_VALUE + "");
        String s = "";
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

}