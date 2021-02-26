package 常用的工具类.guava.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalListeners;
import com.google.common.cache.RemovalNotification;
import com.google.common.cache.Weigher;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by wxi.wang
 * <p>
 *  参考网址：
 *       https://www.jianshu.com/p/5299f5a11bd5
 *       https://www.jianshu.com/p/b51fc9ed0e71
 * 2019/10/8 17:30
 * Decription:
 */
public class Demo01 {
    /**
     * 简单入门
     */
    @Test
    public void test01() {
        Cache<Integer, String> cache = CacheBuilder.newBuilder().build();
        cache.put(1, "a");
        System.out.println(cache.getIfPresent(1));
        System.out.println(cache.getIfPresent(2));
    }

    /**
     * 方式1：缓存没命中
     * 没有缓存，那么计算，然后再缓存，并且返回计算的值
     * 此方式是key和value存在关系
     */
    @Test
    public void test02() {
        LoadingCache<Integer, String> cache = CacheBuilder.newBuilder().build(
                new CacheLoader<Integer, String>() {
                    @Override
                    public String load(Integer key) throws Exception {
                        return "key-" + key;
                    }

                    @Override
                    public Map<Integer, String> loadAll(Iterable<? extends Integer> keys) throws Exception {
                        /**
                         * removalListener：当缓存被移除的时候执行的策略，例如打日志等
                         * build参数CacheLoader：用于refresh时load缓存的策略，根据具体业务而定，
                         * 建议在实现load方法的同时实现loadAll方法loadAll方法适用于批量查缓存的需求，
                         * 或者刷新缓存涉及到网络交互等耗时操作。比如你的缓存数据需要从redis里获取，
                         * 如果不实现loadAll，则需要多次load操作，也就需要多次redis交互，非常耗时，
                         * 而实现loadAll，则可以在loadAll里向redis发送一条批量请求，显著降低网络交互次数和时间，
                         * 显著提升效率
                         *
                         */
                        return super.loadAll(keys);
                    }
                }
        );
        cache.put(1, "a");
        System.out.println(cache.getIfPresent(1));
        try {
            // 没有缓存，那么计算，然后再缓存，并且返回计算的值
            System.out.println(cache.get(2));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 方式2：缓存没命中
     * 没有缓存，那么计算，然后再缓存，并且返回计算的值
     */
    @Test
    public void test03() {
        Cache<Integer, String> cache = CacheBuilder.newBuilder().build();
        cache.put(1, "a");
        System.out.println(cache.getIfPresent(1));
        try {
            // 没有缓存，那么计算，然后再缓存，并且返回计算的值
            String value = cache.get(2, () -> {
                return "hello world";
            });
            System.out.println(value);
            System.out.println(cache.getIfPresent(2));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 缓存回收方式1：基于数量回收
     * 规定缓存项的数目不超过固定值，只需使用CacheBuilder.maximumSize(long)。
     * 缓存将尝试回收最近没有使用或总体上很少使用的缓存项。——警告：在缓存项的数目达到限定值之前，
     * 即缓存项的数目逼近限定值时缓存就可能进行回收操作。这个size指的是cache中的条目数，不是内存大小或是其他.
     */
    @Test
    public void test04() {
        Cache<Integer, String> cache = CacheBuilder.newBuilder().maximumSize(2).build();
        cache.put(1, "a");
        cache.put(2, "b");
        cache.put(3, "c");
        System.out.println(cache.asMap());
        System.out.println(cache.getIfPresent(2));
        cache.put(4, "d");
        // 返回当前缓存的快照
        System.out.println(cache.asMap());
    }

    /**
     * 基于重量回收，设置容量回收策略函数
     * 基于此 可以控制缓存占用内存的总大小
     */
    @Test
    public void test05() {
        // 设置缓存最大重量为maximumWeight 为100，如果缓存的重量快要到达100，那么就开始回收
        Cache<Integer, Integer> cache = CacheBuilder.newBuilder().maximumWeight(100)
                .weigher(new Weigher<Integer, Integer>() {
                    // 注意需要考虑计算缓存重量的复杂度，重量是在缓存创建时计算
                    @Override
                    public int weigh(Integer key, Integer value) {
                        if (value % 2 == 0) {
                            return 20;
                        } else {
                            return 5;
                        }
                    }
                }).build();
        //放偶数
        for (int i = 0; i <= 20; i += 2) {
            cache.put(i, i);
        }
        System.out.println(cache.asMap());
        // 直接丢弃搜索entry
        cache.invalidateAll();
        for (int i = 1; i < 10; i += 1) {
            cache.put(i, i);
        }
        System.out.println(cache.asMap());
    }

    /**
     * 缓存回收方式1
     * expireAfterAccess(long, TimeUnit):缓存项在给定时间内没有被读/写访问，则回收。
     * 请注意这种缓存的回收顺序和基于大小回收一样。
     */
    @Test
    public void test06() {
        Cache<Integer, Integer> cache = CacheBuilder.newBuilder().expireAfterAccess(3, TimeUnit.SECONDS).build();
        cache.put(1, 1);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //cache.getIfPresent(1);
        System.out.println(cache.asMap());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(cache.asMap());
    }

    /**
     * expireAfterWrite(long, TimeUnit):缓存项在给定时间内没有被写访问（创建或覆盖），则回收。
     * 如果认为缓存数据总是在固定时候后变得陈旧不可用，这种回收方式是可取的。
     */
    @Test
    public void test07() {
        Cache<Integer, Integer> cache = CacheBuilder.newBuilder().expireAfterWrite(3, TimeUnit.SECONDS).build();
        cache.put(1, 1);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cache.getIfPresent(1);
        System.out.println(cache.asMap());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(cache.asMap());
    }

    /**
     * 综合使用缓存的回收方式
     * 基于时间的缓存回收可以和基于容量的缓存回收一起使用，这样可以避免：
     * 当缓存创建速度，远远大于过期速度的时候出现OOM的问题。
     */
    @Test
    public void test08() {
        Cache<Integer, Integer> cache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterAccess(3, TimeUnit.SECONDS).build();
    }

    /**
     * 手动清除缓存
     */
    @Test
    public void test09() {
        Cache<Integer, Integer> cache = CacheBuilder.newBuilder()
                .maximumSize(100).expireAfterAccess(3, TimeUnit.SECONDS).build();
        cache.put(1, 1);
        cache.put(2, 2);
        // 手动清除单个或多个key
        cache.invalidateAll(Lists.newArrayList(1));
        System.out.println(cache.asMap());
        cache.put(3, 3);
        System.out.println(cache.asMap());
        // 手动清除所有
        cache.invalidateAll();
        System.out.println(cache.asMap());
    }

    /**
     * 添加移除监听器
     * 默认情况下，监听器方法是在移除缓存时同步调用的。因为缓存的维护和请求响应通常是同时进行的，
     * 代价高昂的监听器方法在同步模式下会拖慢正常的缓存请求。
     * 在这种情况下，你可以使用RemovalListeners.asynchronous(RemovalListener, Executor)把监听器装饰为异步操作。
     */
    @Test
    public void test10() {
        LoadingCache<Integer, Integer> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(3, TimeUnit.SECONDS)
                .removalListener(RemovalListeners.asynchronous(new RemovalListener<Object, Object>() {
                    // 当缓存被移除时执行
                    @Override
                    public void onRemoval(RemovalNotification<Object, Object> notification) {
                        System.out.println("remove key[" + notification.getKey() +
                                "],value[" + notification.getValue() + "],remove reason[" +
                                notification.getCause() + "]");
                    }
                }, Executors.newSingleThreadExecutor()))
                .recordStats()  // 记载统计信息
                .build(
                        // 缓存key不存在时，默认返回的值
                        new CacheLoader<Integer, Integer>() {
                            @Override
                            public Integer load(Integer key) {
                                return 2;
                            }
                        }
                );


        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.asMap());
        cache.invalidateAll();
        System.out.println(cache.asMap());
        cache.put(3, 3);
        try {
            //System.out.println(cache.getUnchecked(3));
            Thread.sleep(4000);
            System.out.println(cache.getUnchecked(3));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * https://blog.csdn.net/ofeihongye/article/details/88666477
     * 惰性删除，监听器惰性触发
     * @throws InterruptedException
     */
    @Test
    public void test101() throws InterruptedException {
        Cache<Object, Object> cache = CacheBuilder.newBuilder()
                .maximumSize(10000)
                .expireAfterWrite(3, TimeUnit.SECONDS)
                .removalListener(RemovalListeners.asynchronous(new RemovalListener<Object, Object>() {
                    // 当缓存被移除时执行
                    @Override
                    public void onRemoval(RemovalNotification<Object, Object> notification) {
                        try {
//                            int i = 1/0;
                            System.out.println("remove key[" + notification.getKey() +
                                    "],value[" + notification.getValue() + "],remove reason[" +
                                    notification.getCause() + "]");
                        } catch (Exception e) {

                        }
                    }
                }, Executors.newSingleThreadExecutor())).build();


        cache.put(1, 1);
        cache.put(1, 2);
        cache.put(3, 3);

        Thread.sleep(5000);

        cache.cleanUp();
    }

    /**
     * 刷新表示为键加载新值，这个过程可以是异步的。在刷新操作进行时，缓存仍然可以向其他线程返回旧值.
     *
     * 而不像回收操作，读缓存的线程必须等待新值加载(移除)完成。
     *
     * 如果刷新过程抛出异常，缓存将保留旧值，
     */
    @Test
    public void test11() {
        LoadingCache<Integer, Integer> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(3, TimeUnit.SECONDS).removalListener(new RemovalListener<Object, Object>() {
            @Override
            public void onRemoval(RemovalNotification<Object, Object> notification) {
                System.out.println("remove key[" + notification.getKey() + "],value[" + notification.getValue() + "],remove reason[" + notification.getCause() + "]");
            }
        }).recordStats().build(
                new CacheLoader<Integer, Integer>() {
                    @Override
                    public Integer load(Integer key) throws Exception {
                        return 2;
                    }
                }
        );
        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.asMap());
        // 刷新，会重新加载这个key(load)
        cache.refresh(1);
        System.out.println(cache.asMap());
    }

    // 一些统计信息

    /**
     * CacheBuilder.recordStats()用来开启Guava Cache的统计功能。统计打开后，Cache.stats()方法会返回对象以提供如下统计信息：
     * hitRate()：缓存命中率；
     * averageLoadPenalty()：加载新值的平均时间，单位为纳秒；
     * evictionCount()：缓存项被回收的总数，不包括显式清除
     * 统计信息对于调整缓存设置是至关重要的，在性能要求高的应用中我们建议密切关注这些数据
     */

}
