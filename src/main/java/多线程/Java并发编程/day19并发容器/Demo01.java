package 多线程.Java并发编程.day19并发容器;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: wangxi
 * @Description :
 * @Date: 2018/8/2 0002 17:12
 */
public class Demo01 {
    public static void main(String[] args) {
        List<Integer> list = Collections.synchronizedList(new ArrayList<>());
        list.add(1);

    }
}
