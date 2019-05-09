package java8.java8补充;

public class ArraySort implements Runnable{

    private String num;

    public ArraySort(int num) {
        this.num = num + "";
    }

    public static void main(String[] args) {
        int[] nums = {11,3,998,5455,1,152,990};
        for (int i = 0, length = nums.length; i < length; i++) {
            new Thread(new ArraySort(nums[i])).start();
        }
    }

    @Override
    public void run() {
        try {
            // 按照线程休眠的时间进行输出对应的数字
            Thread.sleep(Integer.parseInt(num));
            System.out.println(num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
