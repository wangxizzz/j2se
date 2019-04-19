package 多线程.Java并发编程.day17多线程异步调用之Future模式.JDK中的Future模式实现;

import java.util.concurrent.Callable;

/**
 * @Author:王喜
 * @Description : 获取真实的数据
 * @Date: 2018/5/1 0001 15:45
 */
public class RealData implements Callable<String>{
    private String result;

    public RealData(String result) {
        this.result = result;
    }

    @Override
    public String call() throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(result);
        //模拟耗时的构造数据过程
        Thread.sleep(5000);
        return sb.toString();
    }
}
