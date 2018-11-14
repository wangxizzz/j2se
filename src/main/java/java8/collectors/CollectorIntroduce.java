package java8.collectors;

import java8.lambdas.Apple;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class CollectorIntroduce {

    public static void main(String[] args) {
        List<Apple> list = Arrays.asList(new Apple("green", 150)
                , new Apple("yellow", 120)
                , new Apple("green", 170)
                , new Apple("green", 150)
                , new Apple("yellow", 120)
                , new Apple("green", 170));

        List<Apple> greenList = list.stream().filter(a -> a.getColor().equals("green")).collect(Collectors.toList());
        // 利用Optional.ofNullable不再需要使用空指针的判断了
        Optional.ofNullable(greenList).ifPresent(l -> System.out.println(l));
        Optional.ofNullable(groupByNormal(list)).ifPresent(System.out::println);
        System.out.println("===================================================");
        Optional.ofNullable(groupByFunction(list)).ifPresent(System.out::println);
        System.out.println("===================================================");
        Optional.ofNullable(groupByCollector(list)).ifPresent(System.out::println);
    }

    /**
     * 通过苹果颜色对苹果分类
     * @param apples
     * @return
     */
    private static Map<String, List<Apple>> groupByNormal(List<Apple> apples) {
        Map<String, List<Apple>> map = new HashMap<>();
        for (Apple a : apples) {
            List<Apple> list = map.get(a.getColor());
            if (null == list) {
                list = new ArrayList<>();
                map.put(a.getColor(), list);
            }
            list.add(a);
        }
        return map;
    }

    private static Map<String, List<Apple>> groupByFunction(List<Apple> apples) {
        Map<String, List<Apple>> map = new HashMap<>();
        apples.parallelStream().forEach(a -> {
            List<Apple> colorList = Optional.ofNullable(map.get(a.getColor())).orElseGet(() -> {
                List<Apple> list = new ArrayList<>();
                map.put(a.getColor(), list);
                return list;
            });
            colorList.add(a);
        });
        return map;
    }

    private static Map<String, List<Apple>> groupByCollector(List<Apple> apples) {
        return apples.parallelStream().collect(groupingBy(apple -> apple.getColor()));
    }
}