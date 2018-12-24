package 多线程.异步.guava的异步;

import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import 多线程.异步.User;

import java.util.concurrent.TimeUnit;


public class Consumer {
    public static void main(String[] args) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Provider1 provider = new Provider1();
        /**
         * 假设这里在调用远程接口1，返回一个user,创建一个user需要5s
         */
        ListenableFuture<User> listenableFuture1 = provider.getUser();
        listenableFuture1.addListener(() -> {
            try {
                System.out.println("sss" + listenableFuture1.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, MoreExecutors.directExecutor());
        /**
         * 这里又调用另一个远程接口2,返回一个user，创建一个user需要5s
         */
        Provider2 provider2 = new Provider2();
        ListenableFuture<User> listenableFuture2 = provider2.getUser();
        listenableFuture2.addListener(() -> {
            try {
                System.out.println("sss" + listenableFuture2.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, MoreExecutors.directExecutor());
        System.out.println("aaaaaaaaaaaaaa");

        long end = stopwatch.elapsed(TimeUnit.SECONDS);
        System.out.println(end);
        /**
         * 程序结束，其实只用了5s，而不是10s..这就是异步调用的好处。
         * 可以不必阻塞在哪个具体的调用在，主线程依然在往下跑。这就比如加载某个网页一般，
         * 如果某个图片加载很耗时，那么html页面，就在那里卡住吗？显然不是，它会依然的往下加载html标签，
         * 只是说在图片那里加了一个类似监听一样(addListener),只要图片返回就立马加载，
         * 这是用了ListenableFuture或者CompletableFuture,
         * 返回ListenableFuture或CompletableFuture才会有类似的监听回调的功能。
         * 或者在图片加载那里写一段不断轮询的代码（可以使用可调度线程池，做定时任务）。
         *
         * 异步调用的不适用场景：
         * 如果你调用远程接口，想需要立马返回数据，就是不等，那么就不适用于用异步调用。
         * 注意：异步调用并不能减少单个远程接口调用所耗的时间
         * (比如创建user，需要5秒，不可能用了异步就变为1s创建好了，哪有这么好的事啊)，
         * 只是相对于一次性调用多个远程接口的线程并行执行，这样相对串行就会只用了一个时间。
         * 就比如上面的程序只用了5s，就创建好了两个user（如果串行，没有使用异步，就会耗时10s）
         *
         * 还举个异步调用的例子：
         * Service从消息队列中拿数据消费，就可以采用异步调用。
         * 就是我拿到一个消息，我不管他是正确的错误的，就接着拿第二个消息，
         * 到在service用的时候再判断消息的正确与错误。这样就大大的提高了系统的吞吐量，不是吗？
         */
    }
}
