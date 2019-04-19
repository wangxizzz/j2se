package string测试;

/**
 * @Author:王喜
 * @Description : 关于String的地址，引用，常量池，intern()方法的测试
 * @Date: 2018/5/13 0013 21:02
 */
public class StringDemo1 {
    public static void main(String[] args) {
        String s1 = "aa";
        String s2 = "aa";
        System.out.println(s1 == s2);
        String s3 = new String("aa");
        String s4 = new String("aa");
        System.out.println(s3 == s4);
        System.out.println(s1 == s3);
        String s5 = "aaaa";
        String s6 = s1 + s2;
        String s7 = s1 + "aa";
        String s8 = "aa" + "aa"; //就相当于s5
        System.out.println(s5 == s6);
        System.out.println(s5 == s7);
        System.out.println(s6 == s7);
        System.out.println("===========================================");
        System.out.println(s5 == s8);
        System.out.println(s8 == s7);
        /**
         *这里我将分析一下String对象的intern()方法的应用：
         intern()方法将返回一个字符串对象的规范表示法，即一个同该字符串内容相同的字符串，
         但是来自于唯一字符串的String缓冲池。这听起来有点拗口，其实它的机制有如以下代码段：

         String s = new String("Hello");
         s = s.intern();

         以上代码段的功能实现可以简单的看成如下代码段：

         String s = "Hello";
         */
        s3.intern();
        System.out.println(s1 == s3);
        /**
         * 注意：返回一个常量池的引用，需要用返回值接收
         */
        s3 = s3.intern();
        System.out.println(s1 == s3);

        System.out.println(s1.hashCode());
    }
}
