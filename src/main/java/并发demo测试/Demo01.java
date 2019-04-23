package 并发demo测试;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wxi.wang
 * 19-1-23
 */
@Slf4j
public class Demo01 {
    private int count = 0;

    /**
     * 方法里面的变量全部都是私有的(线程安全的) 不管方法签名是static还是非static
     */
    private static void test01() {
        int i = 0;
        i++;
        System.out.print(i);
    }

    /**
     * 成员变量是对象私有的.线程安全的
     */
    private void test02() {
        count++;
        System.out.print(count);
    }

    public static void main(String[] args) {
        // test01()方法测试
        for (int i = 0; i < 100; i++) {
            new Thread(Demo01::test01).start();
        }

         //test02()方法测试
        for (int j = 0; j < 200; j++) {
            new Thread(new Demo01()::test02).start();
        }


    }
}
