package java8.并行流.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * 继承RecursiveTask有返回值
 */
public class AccumulatorRecursiveTask extends RecursiveTask<Integer> {

    private final int start;

    private final int end;

    private final int[] data;

    private final int LIMIT = 3;   // 最小划分限制为3个

    public AccumulatorRecursiveTask(int start, int end, int[] data) {
        this.start = start;
        this.end = end;
        this.data = data;
    }


    @Override
    protected Integer compute() {
        if ((end - start) <= LIMIT) {
            int result = 0;
            for (int i = start; i < end; i++) {
                result += data[i];
            }
            return result;
        }

        int mid = (start + end) / 2;
        // 拆分任务
        AccumulatorRecursiveTask left = new AccumulatorRecursiveTask(start, mid, data);
        AccumulatorRecursiveTask right = new AccumulatorRecursiveTask(mid, end, data);
        /**
         * 继承RecursiveTask固定的任务执行代码。
         */
        // 把左边计算,fork()表示异步执行
        left.fork();
        // 把右边计算
        Integer rightResult = right.compute();
        // 左边先fork,后join
        Integer leftResult = left.join();

        return rightResult + leftResult;
    }
}
