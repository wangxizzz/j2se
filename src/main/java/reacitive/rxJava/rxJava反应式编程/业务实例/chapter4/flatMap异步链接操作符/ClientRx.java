package reacitive.rxJava.rxJava反应式编程.业务实例.chapter4.flatMap异步链接操作符;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import reacitive.rxJava.rxJava反应式编程.业务实例.chapter4.命令式并发与RxJava.Ticket;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangxi created on 2020/6/13 16:29
 * @version v1.0
 */
@Slf4j
public class ClientRx {

    private SmtpResponse sendMail(Ticket ticket) throws Exception {
        Thread.sleep(500);
        return new SmtpResponse();
    }

    public Observable<SmtpResponse> rxSendMail(Ticket ticket) {

        return Observable.fromCallable(() -> sendMail(ticket));
    }

    public void rxFun() {
        List<Ticket> tickets = new ArrayList<>();
        Observable.fromIterable(tickets)
                .map(ticket -> rxSendMail(ticket)
                        // 忽略正常的行为
                        .ignoreElements()
                        .doOnError(e -> log.error("fail to send ticket = {}", ticket, e))
                        // 应该是 onErrorReturn
                        .onErrorResumeNext(e -> Completable.complete())
                        .subscribeOn(Schedulers.io())
                );

    }

}
