package java8.并行流.forkjoin;

import java.util.concurrent.ForkJoinPool;

public class ForkJoinPoolTest {

    private static int[] data = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    public static void main(String[] args) {
        System.out.println("result=> " + calc());

        // 采用继承RecursiveTask来计算，有返回值。
        AccumulatorRecursiveTask task = new AccumulatorRecursiveTask(0, data.length, data);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Integer result = forkJoinPool.invoke(task);
        System.out.println("AccumulatorRecursiveTask >>" + result);

        // 采用继承RecursiveAction来计算
        AccumulatorRecursiveAction action = new AccumulatorRecursiveAction(0, data.length, data);
        forkJoinPool.invoke(action);
        System.out.println("AccumulatorRecursiveAction >>" + AccumulatorRecursiveAction.AccumulatorHelper.getResult());
    }

    /**
     * 顺序计算数组元素的和
     * @return
     */
    private static int calc() {
        int result = 0;
        for (int i = 0; i < data.length; i++) {
            result += data[i];
        }
        return result;
    }

}