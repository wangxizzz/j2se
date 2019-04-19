package 测试demos;

import org.junit.Test;


public class Demo01 {
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

    @Test
    public void test03() {
        String s = "  s  ";
        System.out.println(s.split(" ").length);
    }
}
