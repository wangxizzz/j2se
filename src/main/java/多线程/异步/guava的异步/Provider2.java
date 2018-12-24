package 多线程.异步.guava的异步;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import 多线程.异步.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Provider2 {
    private ExecutorService service = Executors.newFixedThreadPool(4);
    private ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(service);

    public ListenableFuture<User> getUser() {
        ListenableFuture<User> listenableFuture = listeningExecutorService.submit(() -> {
            Thread.sleep(5000);  // 假设创建对象需要很长时间
            User user = new User();
            return user;
        });
        service.shutdown();
        listeningExecutorService.shutdown();
        return listenableFuture;
    }
}
