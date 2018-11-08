package Java151条建议;

import org.junit.Test;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Demo01 {
    /**
     * 判断一个数的奇偶性的坑
     * 奇数：因为凡是不能被2整除的数都是奇数。-1是整数,且不能被2整除,所以是奇数
     * 正奇数：1、3、5、7、9、.........
     * 负奇数：-1、-3、-5、-7、-9、.........
     */
    @Test
    public void test01() {
        String s = -1 % 2 == 1 ? "奇数" : "偶数";
        System.out.println(s);  // 输出为偶数,但是-1为奇数
        System.out.println(-2 % 2 == 1 ? "奇数" : "偶数");
        /**
         * 可以使用下面两种方法避免负奇数的情况
         */
        System.out.println((-1 & 1) == 1 ? "奇数" : "偶数");
        System.out.println(-1 % 2 == 0 ? "偶数" : "奇数");
    }

    /**
     * 在金融行业中，float计算的结果并不准确。推荐使用BigDecimal和扩大100，1000倍数。
     */
    @Test
    public void test02() {
        NumberFormat f = new DecimalFormat("#.##");
        System.out.println(f.format(10.00 - 9.60));
    }

    /**
     * int越界问题的坑（1）
     */
    @Test
    public void test03() {
        // 会产生溢出情况。因为Java是先计算，然后再进行类型转换的。30默认是int，乘出的结果也是Int，但是溢出了。
        long result = 30*10000*1000*60*8;
        System.out.println(result);
        // 正确写法，一开始就显示的转换成Long型
        result = 1L * 30*10000*1000*60*8;
        System.out.println(result);
    }

    /**
     * int越界问题的坑（2）
     */
    @Test
    public void test04() {
        int limit = 2000;  // 订单的最大数量
        int cur = 80;  // 当前的订单数量
        Scanner input = new Scanner(System.in); // 输入预订单数量
        System.out.println("输入数字：");
        int order = input.nextInt();  // order表示订单数量
        if (order > 0 && order + cur <= limit) {  // 这里会产生边界溢出
            System.out.println("订单成功");
        } else {
            System.out.println("error");
        }
        /**
         * 错误实例是：输入Integer.MAX_VALUE就会产生溢出。
         */
    }
    /**
     * NullPointerException坑
     */
    @Test
    public void test05() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(null);
        // 正确的写法
        System.out.println(1 + (list.get(1) == null ? 0 : list.get(1)));
        System.out.println("======================================================================");
        // 会报空指针异常
        System.out.println(1 + list.get(1));
        System.out.println(list);
    }

    /**
     * 整型缓冲池-128~127返回相同的Integer对象
     */
    @Test
    public void test06() {
        int a = 127;
        /**装箱。通过Integer.ValueOf(int i)方法.所以为了提高性能，申明包装类时采用ValueOf()，而不要采用构造函数的方式
         * 拆箱是通过Integer.intValue()方法。
        */
        Integer A = a;
        Integer B = 127;
        System.out.println(A == B);
    }
}
