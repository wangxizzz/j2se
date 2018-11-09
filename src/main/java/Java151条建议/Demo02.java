package Java151条建议;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Demo02 {
    /**
     * substring()是返回一个新的字符串，在新的字符串操作不会影响原字符串
     */
    @Test
    public void test01() {
        String name = "wangx";
        String s1 = name.substring(0);
        System.out.println(s1 == name);   // 返回true,利用了常量池
        s1 = s1 + "AA";
        System.out.println(s1 == name);  // 返回false
    }

    /**
     * subList()是在原list上进行操作数据的
     */
    @Test
    public void test02() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        List<Integer> list1 = list.subList(0, list.size());
        list1.add(222);
        System.out.println(list); // 输出[1, 2, 222]
        System.out.println(list1);
    }

    /**
     * 集合的各种运算的方式
     */
    @Test
    public void test03() {
        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);
        List<Integer> list2 = new ArrayList<>();
        list2.add(3);
        list2.add(4);
        // 并集
        list1.addAll(list2);
        // 交集
        list1.retainAll(list2);
        // 差集
        list1.removeAll(list2);
    }

    @Test
    public void test04() {

    }
}
