package 测试demos;

import org.junit.Test;

import java.util.BitSet;


public class Test03 {
    /**
     * 测试字符串切割,利用-分割
     */
    @Test
    public void test01() {
        String s = "5F3Z-2e-9-w";
        String[] parts = s.split("-");
        System.out.println(parts.length);
        StringBuffer sb = new StringBuffer();
        sb.reverse();
        
    }

    /**
     * 测试阶乘的计算的long型溢出问题
     */
    @Test
    public void test02() {
        long result = 1L;
        for (int i = 1; i < 100; i++) {
            result *= i;
            if (result >= Long.MAX_VALUE / 10) {
                System.out.println("溢出了");
                System.out.println(i);
                break;
            }
        }
        System.out.println(result);
    }

    /**
     * 测试位图
     */
    @Test
    public void test03() {
        BitSet bitSet = new BitSet();
        bitSet.set(1);
        bitSet.set(65);
        System.out.println(bitSet.get(1));
        System.out.println(bitSet.get(3));

        System.out.println(1L << 65);

        System.out.println(1 << 32);

        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
