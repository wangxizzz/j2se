package reacitive.rxJava.rxJava反应式编程.业务实例.chapter4.flatMap异步链接操作符;

import org.apache.commons.lang3.tuple.Pair;
import reacitive.rxJava.rxJava反应式编程.业务实例.chapter4.命令式并发与RxJava.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author wangxi created on 2020/6/13 16:07
 * @version v1.0
 */
public class Client {

    ExecutorService executorService = Executors.newFixedThreadPool(10);

    private SmtpResponse sendMail(Ticket ticket) throws Exception {
        Thread.sleep(500);
        return new SmtpResponse();
    }

    private CompletableFuture<SmtpResponse> asyncSendMail(Ticket ticket)  {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return sendMail(ticket);
            } catch (Exception e) {
                //e.printStackTrace();
            }
            throw new RuntimeException();
        }, executorService);
    }

    // 单线程串行，捕获异常版本
    public void fun01() {
        List<Ticket> tickets = new ArrayList<>();
        for (Ticket ticket : tickets) {
            try {
                sendMail(ticket);
            } catch (Exception e) {

            }
        }
    }

    // 多线程并发
    public void fun02() {
        long start = System.currentTimeMillis();
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(new Ticket());
        tickets.add(new Ticket());
        tickets.add(new Ticket());
        tickets.add(new Ticket());
        List<Pair<Ticket, CompletableFuture<SmtpResponse>>> taskList =
                tickets.stream()
                .map(ticket -> Pair.of(ticket, asyncSendMail(ticket)))
                .collect(Collectors.toList());

        // 收集执行错误的ticket，可以后续统一处理
        List<Ticket> failTickets = taskList.stream()
                .map(pair -> {
                    try {
                        CompletableFuture<SmtpResponse> future = pair.getRight();
                        future.get(1, TimeUnit.SECONDS);
                        return null;
                    } catch (Exception e) {
                        Ticket failTicket = pair.getLeft();

                        return failTicket;
                    }
                }).collect(Collectors.toList());

        System.out.println("时间：" + (System.currentTimeMillis() - start));
        executorService.shutdown();
    }

    public static void main(String[] args) {
        new Client().fun02();
    }
}
