package 多线程.day01;

/**
 * @author wxi.wang
 * 19-1-22
 */
public class RunnableTask implements Runnable{
    @Override
    public void run() {
        System.out.println(";;;;;;;;;;;;;;;;;");
    }

    public static void main(String[] args) {
        RunnableTask task = new RunnableTask();
        new Thread(task).start();
    }
}
