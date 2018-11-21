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
}
