package java8.并行流;

import org.junit.Test;

import java.util.stream.LongStream;

public class Demo {
    @Test
    public void test01() {
        Accumulator a = new Accumulator();
        // 一百万359553623901
        long n = 1000000;
//        long result = LongStream.rangeClosed(1, n)
//                .reduce(0, (i, j) -> i + j);

        // 并行流导致线程不安全。有共享变量total。LongStream会产生原始的long数字，防止装箱拆箱，提高性能。
        LongStream.rangeClosed(1, n)
                .parallel()
                .forEach(x -> a.add(x));
        System.out.println(a.total);
    }
}
