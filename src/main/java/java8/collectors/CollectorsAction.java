package java8.collectors;

import java8.streams.Dish;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 测试collectors的普通api
 */
public class CollectorsAction {


    public final static List<Dish> menu = Arrays.asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 700, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("salmon", false, 450, Dish.Type.FISH));

//    public static void main(String[] args) {
//        //testAveragingDouble();
//        testAveragingInt();
//        testAveragingLong();
//        testCollectingAndThen();
//        testCounting();
//        testGroupingByFunction();
//        testGroupingByFunctionAndCollector();
//        testGroupingByFunctionAndSupplierAndCollector();
//        testSummarizingInt();
//    }

    @Test
    public void testAveragingDouble() {
        System.out.println("testAveragingDouble");
        // 会发生隐式转换，把getCalories()返回的值转换成double
        Optional.ofNullable(menu.stream().collect(Collectors.averagingDouble(Dish::getCalories)))
                .ifPresent(System.out::println);
    }
    @Test
    public void testAveragingInt() {
        System.out.println("testAveragingInt");
        // 把getCalories()返回的值转换成int
        Optional.ofNullable(menu.stream().collect(Collectors.averagingInt(Dish::getCalories)))
                .ifPresent(System.out::println);
    }

    private static void testAveragingLong() {
        System.out.println("testAveragingLong");
        Optional.ofNullable(menu.stream().collect(Collectors.averagingLong(Dish::getCalories)))
                .ifPresent(System.out::println);
    }

    private static void testCollectingAndThen() {
        System.out.println("testCollectingAndThen");
        Optional.ofNullable(menu.stream().collect(Collectors.collectingAndThen(Collectors.averagingInt(Dish::getCalories), a -> "The Average Calories is->" + a)))
                .ifPresent(System.out::println);
/*
        List<Dish> list = menu.stream().filter(d -> d.getType().equals(Dish.Type.MEAT))
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
        // 在修改就会报错
        list.add(new Dish("", false, 100, Dish.Type.OTHER));
        System.out.println(list);*/
    }

    private static void testCounting() {
        System.out.println("testCounting");
        Optional.of(menu.stream().collect(Collectors.counting())).ifPresent(System.out::println);
    }

    private static void testGroupingByFunction() {
        System.out.println("testGroupingByFunction");
        Optional.of(menu.stream().collect(Collectors.groupingBy(Dish::getType)))
                .ifPresent(System.out::println);
    }

    private static void testGroupingByFunctionAndCollector() {
        System.out.println("testGroupingByFunctionAndCollector");
        Map<Dish.Type, Double> collect = menu.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.averagingInt(Dish::getCalories)));
        // 输出map的类型。默认返回的是HashMap
        System.out.println(collect.getClass());
        Optional.of(menu.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.averagingInt(Dish::getCalories))))
                .ifPresent(System.out::println);
    }

    // 自定义返回TreeMap
    @Test
    public void testGroupingByFunctionAndSupplierAndCollector() {
        System.out.println("testGroupingByFunctionAndSupplierAndCollector");
        Map<Dish.Type, Double> map = menu.stream().collect(Collectors.groupingBy(Dish::getType, TreeMap::new, Collectors.averagingInt(Dish::getCalories)));

        Optional.of(map.getClass()).ifPresent(System.out::println);
        Optional.of(map).ifPresent(System.out::println);
    }

    private static void testSummarizingInt() {
        System.out.println("testSummarizingInt");
        IntSummaryStatistics result = menu.stream().collect(Collectors.summarizingInt(Dish::getCalories));
        Optional.of(result).ifPresent(System.out::println);
    }
}