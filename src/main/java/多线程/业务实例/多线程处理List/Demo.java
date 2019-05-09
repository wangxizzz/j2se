package 多线程.业务实例.多线程处理List;

import java.util.Arrays;

/**
 * <Description>
 *
 * @author wangxi
 */
public class Demo {


    public static void main(String[] args) {
        String[] nums = {"public static void", "public class Demo", "static void main"};
        Arrays.stream(nums).map(x -> x.split(" "))
                    .flatMap((nums1) -> Arrays.stream(nums1))
                    .forEach(System.out::println);
    }
}

