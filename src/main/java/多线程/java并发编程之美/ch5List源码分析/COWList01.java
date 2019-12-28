package 多线程.java并发编程之美.ch5List源码分析;

import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author wangxi
 * @Time 2019/12/28 23:23
 */
public class COWList01 {
    public static void main(String[] args) throws Exception{
        fun01();
    }

    // 弱一致性分析
    public static void fun01() throws InterruptedException {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        Thread thread = new Thread(() -> {
           list.remove(1);
           list.add(100);
        });
        // 在线程启动之前获取迭代器
        Iterator<Integer> iterator = list.iterator();
        thread.start();

        thread.join();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    /**
     * 测试对象的引用与快照
     */
    @Test
    public void fun02() {
        // 内存原本的地址
        int[] arr1 = {1,2,3};
        System.out.println(arr1);
        // 返回一个新的数组对象
        int[] ints = Arrays.copyOf(arr1, 3);

        // 有一个snapshot数组
        int[] snapshot = arr1;
        arr1 = new int[]{200,22};
        // snapshot仍然指向之前的地址
        System.out.println(Arrays.toString(snapshot));
        System.out.println(snapshot);
        // 而arr1指向了新的地址空间
        System.out.println(Arrays.toString(arr1));
        System.out.println(arr1);
    }

    /**
     * CopyOnWriteArraySet底层利用CopyOnWriteArrayList进行元素的存储
     * 不过在add元素之前，利用list判断了元素是否存在于数组中(采用for循环遍历数组元素)。
     */
    @Test
    public void CopyOnWriteArraySetTest() {
        CopyOnWriteArraySet<Integer> set = new CopyOnWriteArraySet<>();
    }
}
