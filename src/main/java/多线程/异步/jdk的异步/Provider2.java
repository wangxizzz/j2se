package 多线程.异步.jdk的异步;

import 多线程.异步.User;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Provider2 {
    private ExecutorService service = Executors.newFixedThreadPool(10);

    public CompletableFuture<User> getUer() {
        CompletableFuture<User> userCompletableFuture = CompletableFuture.supplyAsync(() -> {
            User user = null;
            try {
                Thread.sleep(10000);
                user = new User(2,"Provider2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return user;
        }, service);
        service.shutdown();
        return userCompletableFuture;
    }
}
