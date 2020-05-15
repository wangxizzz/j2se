package 算法demo.表达式计算.模拟;

import lombok.Data;
import 算法demo.表达式计算.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * @Author wangxi
 * @Time 2020/5/15 10:16
 */
@Data
public class Main01 {
    private Stack<String> s1 = new Stack<>();//定义运算符栈
    private Stack<String> s2 = new Stack<>();//定义存储结果栈


    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("性别", "1232132");
        map.put("人群2", "423432");
        map.put("人群3", "4234321");
        List<String> input = new ArrayList<>();
        input.add("(");
        input.add(map.get("性别"));
        input.add("+");
        input.add(map.get("人群2"));
        input.add(")");
        input.add("*");
        input.add(map.get("人群3"));

        System.out.println(input);

        Stack<String> result = new Main01().doTrans(input);
        System.out.println(result);
    }

    public Stack<String> doTrans(List<String> input) {
        for (int j = 0; j < input.size(); j++) {
            String s = input.get(j);
            switch (s) {
                case "+":
                    s1.push(s);
//                    gotOper(ch, 1);
                    break;
                case "-":
                    s1.push(s);
//                    gotOper(ch, 1);
                    break;
                case "*":
                    s1.push(s);
//                    gotOper(ch, 2);
                    break;
                case "(":
                    s1.push(s);//如果当前字符是'(',则将其入栈
                    break;
                case ")":
                    gotParen(s);
                    break;
                default:
                    s2.push(s);
                    break;
            }
        }

        while (!s1.isEmpty()) {
            s2.push(s1.pop());
        }
        return s2;
    }

//    public void gotOper(String opThis, int prec1) {
//        while (!s1.isEmpty()) {
//            String opTop = s1.pop();
//            if (opTop.equals("(")) {//如果栈顶是'(',直接将操作符压入s1
//                s1.push(opTop);
//                break;
//            } else {
//                int prec2;
//                if (opTop == '+' || opTop == '-') {
//                    prec2 = 1;
//                } else {
//                    prec2 = 2;
//                }
//                if (prec2 < prec1) {//如果当前运算符比s1栈顶运算符优先级高，则将运算符压入s1
//                    s1.push(opTop);
//                    break;
//                } else {//如果当前运算符与栈顶运算符相同或者小于优先级别，那么将S1栈顶的运算符弹出并压入到S2中
//                    //并且要再次再次转到while循环中与 s1 中新的栈顶运算符相比较；
//                    s2.push(opTop);
//                }
//            }
//
//        }
//        s1.push(opThis);
//    }

    public void gotParen(String ch) {
        while (!s1.isEmpty()) {
            String chx = s1.pop();
            if (chx.equals("(")) {
                break;
            } else {
                s2.push(chx);
            }
        }
    }
}
