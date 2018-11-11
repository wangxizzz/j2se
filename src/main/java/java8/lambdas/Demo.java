package java8.lambdas;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Function;

/**
 * 测试lambda的小demo
 */
public class Demo {
    public static void main(String[] args) throws IOException {
        // 传入一个函数式接口(也就是一段代码)
        String reslut = processFile((BufferedReader br) -> {
            try {
                return br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        });
        System.out.println(reslut);
    }

    public static String processFile(Function<BufferedReader, String> f) throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader("README.md"))) {
           return f.apply(br);
        }
    }


   @Test
    public void test01() {
        // 直接写，返回函数接口，直接调用其中的方法
        Function<String, Integer> f = (String s) -> Integer.parseInt(s);
       System.out.println(f.apply("1"));
        // 通过传入函数代码进行调用
       int result = test01("1", (s) -> Integer.parseInt(s));
       System.out.println(result);
   }
   private int test01(String s, Function<String, Integer> f) {
        return f.apply(s);
   }
}
