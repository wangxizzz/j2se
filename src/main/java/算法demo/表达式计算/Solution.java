package 算法demo.表达式计算;

import java.util.Stack;

/**
 * @Author wangxi
 * @Time 2020/5/14 19:10
 */
public class Solution {
    public int solution(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        for(int i = 0; i < tokens.length; i++) {
            if(isOperator(tokens[i])) {
                Integer res = eval(stack.pop(), stack.pop(), tokens[i].charAt(0));
                stack.push(res);
            } else {
                stack.push(Integer.valueOf(tokens[i]));
            }
        }
        return stack.pop();
    }

    boolean isOperator(String str) {
        char operator = str.charAt(0);
        return str.length() == 1 && (operator == '+' || operator == '-' || operator == '*' || operator == '/');
    }

    Integer eval(Integer n2, Integer n1, char operator) {
        if(operator == '/') {
            return n1 / n2;
        }

        if(operator == '-') {
            return n1 - n2;
        }

        if(operator == '*') {
            return n1 * n2;
        }

        return n1 + n2;
    }

    public static void main(String[] args) {
        //String s = "345*-";
        String s = "123+*523+/-";
        String[] tokens = new String[s.length()];
        for (int i = 0; i < s.length(); i++) {
            tokens[i] = s.charAt(i) + "";
        }

        System.out.println(new Solution().solution(tokens));
    }
}
