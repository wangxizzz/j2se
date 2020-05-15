//package 算法demo.表达式计算.模拟;
//
//import com.google.common.collect.Lists;
//import com.google.common.collect.Sets;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Set;
//import java.util.Stack;
//
///**
// * @Author wangxi
// * @Time 2020/5/15 10:16
// */
//public class Main02 {
//    public static void main(String[] args) {
//        String input = "1232132, 423432, +, 4234321, *";
//        String[] tokens = input.split(",");
//        System.out.println(Arrays.toString(tokens));
//
//        new Main02().solution(tokens);
//    }
//
//    public String solution(String[] tokens) {
//        Stack<String> stack = new Stack<>();
//        for(int i = 0; i < tokens.length; i++) {
//            if(isOperator(tokens[i])) {
//                String newPeopleId = eval(stack.pop(), stack.pop(), tokens[i].charAt(0));
//                stack.push(newPeopleId);
//            } else {
//                stack.push(tokens[i]);
//            }
//        }
//        return stack.pop();
//    }
//
//    boolean isOperator(String str) {
//        char operator = str.charAt(0);
//        return str.length() == 1 && (operator == '+' || operator == '-' || operator == '*');
//    }
//
//    public String eval(String peopleId1, String peopleId2, char operator) {
//        if(operator == '+') {
//            // 循环取数据
//            Set<String> userIds1 = getUserIdListByPeopleId(peopleId1);
//            Set<String> userIds2 = getUserIdListByPeopleId(peopleId2);
//            String s = processUnion(userIds1, userIds2);
//
//            return s;
//        }
//
//        if(operator == '*') {
//            // 处理交集
////            return n1 * n2;
//        }
//        // 处理差集
////        return n1 - n2;
//    }
//
//    public String processUnion(Set<String> userIds1, Set<String> userIds2) {
////
//
//        return "";
//    }
//
//
//
//    public Set<String> getUserIdListByPeopleId(String peopleId) {
//        if ("1232132".equals(peopleId)) {
//            return Sets.newHashSet("1", "2", "342", "55", "4");
//        } else {
//            return Sets.newHashSet("1", "2342", "342", "55", "4");
//        }
//    }
//}
