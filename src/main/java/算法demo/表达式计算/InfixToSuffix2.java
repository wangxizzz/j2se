package 算法demo.表达式计算;

import lombok.Data;

import java.util.Stack;

/**
 * @Author wangxi
 * @Time 2020/5/14 20:17
 */
@Data
public class InfixToSuffix2 {
    private Stack<Character> s1;//定义运算符栈
    private Stack<Character> s2;//定义存储结果栈
    private String input;

    //默认构造方法，参数为输入的中缀表达式
    public InfixToSuffix2(String in) {
        input = in;
        s1 = new Stack<>();
        s2 = new Stack<>();
    }

    public static void main(String[] args) {
//        String s = "3-4*5";
//        String s = "((2+1)*3)";
        String s = "1*(2+3)-5/(2+3)";
        // 入参，利用括号匹配、两个标签之间需要有运算法。简单校验
        Stack<Character> characters = new InfixToSuffix2(s).doTrans();
        String res = "";
        for (char c : characters) {
            res += c;
        }

        System.out.println(res);
    }

    //中缀表达式转换为后缀表达式，将结果存储在栈中返回，逆序显示即后缀表达式
    public Stack<Character> doTrans() {
        for (int j = 0; j < input.length(); j++) {
            //System.out.print("s1栈元素为：");
            //s1.displayStack();
            //System.out.print("s2栈元素为：");
            //s2.displayStack();
            char ch = input.charAt(j);
            //System.out.println("当前解析的字符:"+ch);
            switch (ch) {
                case '+':
                    gotOper(ch, 1);
                    break;
                case '-':
                    gotOper(ch, 1);
                    break;
                case '*':
                    gotOper(ch, 2);
                    break;
                case '/':
                    gotOper(ch, 2);
                    break;
                case '(':
                    s1.push(ch);//如果当前字符是'(',则将其入栈
                    break;
                case ')':
                    gotParen(ch);
                    break;
                default:
                    //1、如果当前解析的字符是操作数，则直接压入s2
                    //2、
                    s2.push(ch);
                    break;
            }//end switch
        }//end for

        while (!s1.isEmpty()) {
            s2.push(s1.pop());
        }
        return s2;
    }

    public void gotOper(char opThis, int prec1) {
        while (!s1.isEmpty()) {
            char opTop = s1.pop();
            if (opTop == '(') {//如果栈顶是'(',直接将操作符压入s1
                s1.push(opTop);
                break;
            } else {
                int prec2;
                if (opTop == '+' || opTop == '-') {
                    prec2 = 1;
                } else {
                    prec2 = 2;
                }
                if (prec2 < prec1) {//如果当前运算符比s1栈顶运算符优先级高，则将运算符压入s1
                    s1.push(opTop);
                    break;
                } else {//如果当前运算符与栈顶运算符相同或者小于优先级别，那么将S1栈顶的运算符弹出并压入到S2中
                    //并且要再次再次转到while循环中与 s1 中新的栈顶运算符相比较；
                    s2.push(opTop);
                }
            }

        }//end while
        //如果s1为空，则直接将当前解析的运算符压入s1
        s1.push(opThis);
    }

    //当前字符是 ')' 时，如果栈顶是'(',则将这一对括号丢弃，否则依次弹出s1栈顶的字符，压入s2，直到遇到'('
    public void gotParen(char ch) {
        while (!s1.isEmpty()) {
            char chx = s1.pop();
            if (chx == '(') {
                break;
            } else {
                s2.push(chx);
            }
        }
    }


}
