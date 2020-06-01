```java
@Test
public void test01() {
    LoadingCache<Integer, String> loadingCache = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.SECONDS)
            .refreshAfterWrite(500, TimeUnit.MILLISECONDS)  // 异步刷新
            .maximumSize(10) // 缓存最大数量
            .executor(Executors.newSingleThreadExecutor())  // 添加异步操作的异步线程池
            .removalListener((RemovalListener<Integer, String>) (integer, s, removalCause) -> {
                // 异步执行移除方法
                System.out.println("key:" + integer + " value:" + s + " cause:"+removalCause);
            })
            .build(new CacheLoader<Integer, String>() {
                @Nullable
                @Override
                public String load(@NonNull Integer i) throws Exception {
                    return i.toString();
                }

                @Nullable
                @Override
                public Map<Integer, String> loadAll(Iterable<? extends Integer> keys) {
                    Map<Integer, String> map = new HashMap<>();
                    for (Integer i : keys) {
                        map.put(i, i.toString());
                    }
                    return map;
                }
            });

}

```
### 参数解析：
- expireAfterWrite：失效策略，类似参数还有expireAfterAccess，key的缓存时间到期以后并不会被立即删除，caffeine使用惰性删除的策略，在LoadingCache被修改，如添加，更新等，或者该失效的key被访问的时候才会删除。
- refreshAfterWrite：刷新策略，设置为比写入时间小可以保证缓存永不失效，对于某些场景，比如请求频率低但是耗时长的业务来说，自动刷新能够显著提升效率和体验
- maximumSize：缓存的item的最大数目，如果超过这个数， caffeine将根据Window TinyLfu策略淘汰一些key，类似的参数还有maximumWeight，示例代码如下，设置maximumWeight的同时，要设置weigher参数，根据key生成对应的weight，如果累计weight达到了maximumWeight，将会进行key的淘汰，淘汰策略与maximumSize一样，与weight无关。另外，maximumSize与maximumWeight不能同时使用，否则会报IllegalStateException。

```java
LoadingCache<Integer, String> weightLoadingCache = Caffeine.newBuilder()
            .maximumWeight(1000)
            .weigher((Weigher<Integer, String>) (integer, s) -> integer)
            .build(new CacheLoader<Integer, String>() {
                @Nullable
                @Override
                public String load(@NonNull Integer integer) throws Exception {
                    return null;
                }
            });

```
- removalListener：当缓存被移除的时候执行的策略，例如打日志等.源码中是异步执行的onRemoval()，默认使用的是ForkJoinPool.commonPool,可以使用executor()添加自定义线程池
- build参数CacheLoader：用于refresh时load缓存的策略，
根据具体业务而定，**建议在实现load方法的同时实现loadAll方法loadAll方法适用于批量查缓存的需求，或者刷新缓存涉及到网络交互等耗时操作。
比如你的缓存数据需要从redis里获取，如果不实现loadAll，则需要多次load操作，也就需要多次redis交互，非常耗时，而实现loadAll，则可以在loadAll里向redis发送一条批量请求，显著降低网络交互次数和时间，显著提升效率。**

### 注意事项：
- expireAfterWrite和expireAfterAccess同时存在时，以expireAfterWrite为准
- maximumSize和maximumWeight不可以同时使用
- caffeine是不缓存null值的，如果在load的时候返回null，caffeine将会把对应的key从缓存中删除，同时，loadAll返回的map里是不可以包含value为null的数据，否则将会报NullPointerException
- Caffeine的刷新是异步执行的，默认的刷新线程池是ForkJoinPool.commonPool()，也可以通过executor方法指定为其它线程池。刷新操作的触发时机是在数据读取之后，通过判断当前时间减去数据的创建时间是否大于refreshAfterWrite指定的时间，如果大于则进行刷新操作。一般refreshAfterWrite常和expireAfterWrite结合使用，需要注意的是：refreshAfterWrite设置的时间要小于expireAfterWrite，因为在读取数据的时候首先通过expireAfterWrite来判断数据有没有失效，数据失效后会同步更新数据，如果refreshAfterWrite时间大于expireAfterWrite，那么refresh操作永远不会执行到，设置了refreshAfterWrite也没有任何意义。
- Caffeine的缓存清除是惰性的，可能发生在读请求后或者写请求后，比如说有一条数据过期后，不会立即删除，可能在下一次读/写操作后触发删除。如果读请求和写请求比较少，但想要尽快的删掉cache中过期的数据的话，可以通过增加定时器的方法，**定时执行cache.cleanUp()方法**，触发缓存清除操作。


### expireAfter 和 refreshAfter 之间的区别
- expireAfter 条件触发后，新的值更新完成前，所有请求都会被阻塞，更新完成后其他请求才能访问这个值。这样能确保获取到的都是最新的值，但是有性能损失。
- refreshAfter 条件触发后，新的值更新完成前也可以访问，不会被阻塞，只是获取的是旧的数据。更新结束后，获取的才是新的数据。有可能获取到脏数据。




