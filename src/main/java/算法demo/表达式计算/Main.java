package 算法demo.表达式计算;

import java.util.Scanner;

/**
 * @Author wangxi
 * @Time 2020/5/14 19:08
 *
 * 3-4*5
 *
 * 345*-
 *
 * ((2+1)*3)
 */
public class Main {
    public static void main(String[] args) {
        String input;
        System.out.println("Enter infix:");
        Scanner scanner = new Scanner(System.in);
        input = scanner.nextLine();
        InfixToSuffix in = new InfixToSuffix(input);
        MyCharStack my = in.doTrans();
        StringBuilder buffer = new StringBuilder();
        for (char c : my.getArray()) {
            buffer.append(c);
        }
        String result = buffer.toString();
        System.out.println(result);
//        my.displayStack();
    }
}
