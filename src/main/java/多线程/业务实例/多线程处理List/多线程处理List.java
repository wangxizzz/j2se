package 多线程.业务实例.多线程处理List;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <Description>
 *  面对大数据量的List集合的数据处理
 *  在大数据量的集合中查找
 * @author wangxi
 */
public class 多线程处理List {
    public static final int MAX_THREAD = 1000;

    public static void main(String[] args) {

        // 首先先得到大数据量的集合
        List<Integer> list = buildList();
        System.out.println("=======");
        System.out.println(list);
    }

    public static List<Integer> buildList() {
        List<Integer> list = new ArrayList<>(10000000);
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREAD);
        for (int i = 0; i < MAX_THREAD; i++) {



//            executorService.submit(new AddData(list, i));
        }
        return list;
    }
}

