package 多线程.异步.jdk的异步;

import 多线程.异步.User;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Provider1 {
    private ExecutorService service = Executors.newFixedThreadPool(10);

    public CompletableFuture<User> getUer() {
        CompletableFuture<User> userCompletableFuture = CompletableFuture.supplyAsync(() -> {
            User user = null;
            try {
                Thread.sleep(10000);
                user = new User(1, "Provider1");
                throw new RuntimeException();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return user;
        }, service);
        return userCompletableFuture;
    }
}
