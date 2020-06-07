package 多线程.异步.业务case测试.completableFuture异常相关;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author wangxi created on 2020/6/7 22:13
 * @version v1.0
 */
@Slf4j
public class CompletableFutureSupplement {
    public static <T> T getResult(CompletableFuture<T> f, int timeOut, TimeUnit timeUnit, T defaultValue, String desc) {
        try {
            return f.exceptionally(e -> {
                log.error("任务:{} 执行异常", desc, e);
                return defaultValue;
            }).get(timeOut, timeUnit);
        } catch (InterruptedException | ExecutionException e) {
            log.error("任务:{} 获取结果异常", desc, e);
            return defaultValue;
        } catch (TimeoutException e) {
            log.warn("任务:{} 获取结果超时 timeout:{} timeUnit:{}", desc, timeOut, timeUnit, e);
            return defaultValue;
        } catch (Exception e) {
            log.error("任务:{} 获取结果异常", desc, timeOut, timeUnit, e);
            return defaultValue;
        }
    }

    public static <T> T getResult(CompletableFuture<T> f, T defaultValue, String desc) {
        try {
            return f.exceptionally(e -> {
                log.error("任务:{} 执行异常", desc, e);
                return defaultValue;
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("任务:{} 获取结果异常", desc, e);
            return defaultValue;
        } catch (Exception e) {
            log.error("任务:{} 获取结果异常", desc, e);
            return defaultValue;
        }
    }

    public static <T> T getResult(CompletableFuture<T> f, int timeOut, TimeUnit timeUnit, T defaultValue) {
        return getResult(f, timeOut, timeUnit, defaultValue, StringUtils.EMPTY);
    }

    public static <T> T getResult(CompletableFuture<T> f, int timeOut, T defaultValue) {
        return getResult(f, timeOut, TimeUnit.SECONDS, defaultValue);
    }

    public static <T> T getResult(CompletableFuture<T> f, int timeOut, T defaultValue, String desc) {
        return getResult(f, timeOut, TimeUnit.SECONDS, defaultValue, desc);
    }
}
