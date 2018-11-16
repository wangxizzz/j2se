package java8.并行流.forkjoin;

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 继承RecursiveAction没有返回值
 */
public class AccumulatorRecursiveAction extends RecursiveAction {
    private final int start;

    private final int end;

    private final int[] data;

    private final int LIMIT = 3;

    public AccumulatorRecursiveAction(int start, int end, int[] data) {
        this.start = start;
        this.end = end;
        this.data = data;
    }

    @Override
    protected void compute() {

        if ((end - start) <= LIMIT) {
            for (int i = start; i < end; i++) {
                AccumulatorHelper.accumulate(data[i]);
            }
        } else {
            int mid = (start + end) / 2;
            AccumulatorRecursiveAction left = new AccumulatorRecursiveAction(start, mid, data);
            AccumulatorRecursiveAction right = new AccumulatorRecursiveAction(mid, end, data);
            /**
             * 继承RecursiveAction固定的任务执行代码。
             */
            left.fork();
            right.fork();
            left.join();
            right.join();
        }
    }

    /**
     * 因为继承RecursiveAction没有返回值，得到返回值的类。
     */
    static class AccumulatorHelper {

        private static final AtomicInteger result = new AtomicInteger(0);

        // 相加
        static void accumulate(int value) {
            result.getAndAdd(value);
        }

        // 返回相加后的结果
        public static int getResult() {
            return result.get();
        }

        static void rest() {
            result.set(0);
        }
    }
}
