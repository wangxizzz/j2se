package 多线程.业务实例.多线程处理List;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 * <Description>
 *
 * @author wangxi
 */
public class AddData implements Runnable {
    private List<Integer> list;
    private int start;
    public AddData(List<Integer> list, int start) {
        this.list = list;
        this.start = start;
    }
    @Override
    public void run() {
        Random random = new Random();
        for (int i = 10000 * start; i < 10000 * (start + 1); i++) {
            list.add(random.nextInt(10000000));
        }
    }
}

