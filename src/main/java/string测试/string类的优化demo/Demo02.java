package string测试.string类的优化demo;

/**
 * @Author:王喜
 * @Description :
 * @Date: 2018/5/19 0019 13:02
 */
public class Demo02 {
    public static void main(String[] args) {
//        new StringBuilder().toString();  toString()里面new 了一个String对象

        String sentence = "";
        for (int i = 0; i < 100; i++) {
            sentence += "Hello" + "world" + String.valueOf(i) + "\n";
        }
        System.out.println(sentence);
    }
}
