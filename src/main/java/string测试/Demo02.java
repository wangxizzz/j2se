package string测试;

/**
 * @Author: wangxi
 * @Description :
 * @Date: 2018/6/4 0004 21:56
 */
public class Demo02 {

    public static void main(String[] args) {
//        String str01 = "str01";
        String str02 = new String("str") + new String("01");
        str02.intern();
        String str01 = "str01";
        System.out.println(str01 == str02);
    }
}
