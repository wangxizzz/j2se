RxJava库成熟之后，如果有一个特殊的类型能够代表有且仅有一个结果，那会有很大的助益。Single类型的流要么有且仅有一个值并正常完成，要么出现错误。在某种程度上，Single与CompletableFuture非常类似，但Single是延迟执行的，这意味着在订阅之前，它不会开始计算

在如下的场景中，应该使用Single。
- 某种操作必须以某个特定值或异常的形式完成。例如，调用Web服务，要么会得到一个来自外部服务器的响应，要么得到某种形式的异常。
- 在你的问题域中，并没有流这样的存在，使用Observable会造成误导和大材小用。
- Observable过于重量级，你衡量后认为Single在某个特定的问题中更快。