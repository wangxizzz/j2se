package reacitive.reactor;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

/**
 * 参考：https://github.com/reactor/reactor-core
 * 代码demo: https://www.infoq.cn/article/reactor-by-example/?itm_source=infoq_en&itm_medium=link_on_en_item&itm_campaign=item_in_other_langs
 *
 * 1、Reactor 指南中文版2.0: http://projectreactor.mydoc.io/?t=44474
 * 2、http://ifeve.com/reactive%ef%bc%88%e5%93%8d%e5%ba%94%e5%bc%8f%ef%bc%89%e7%bc%96%e7%a8%8b-reactor/
 *
 * https://tech.io/playgrounds/929/reactive-programming-with-reactor-3/Intro
 */
public class ReactorDemo {
    private static List<String> words = Arrays.asList(
            "the",
            "quick",
            "brown",
            "fox",
            "jumped",
            "over",
            "the",
            "lazy",
            "dog"
    );

    @Test
    public void simpleCreation() {
        Flux<String> fewWords = Flux.just("Hello", "World");
        Flux<String> manyWords = Flux.fromIterable(words);

        fewWords.subscribe(System.out::println);
        System.out.println();
        manyWords.subscribe(System.out::println);
    }


    @Test
    public void findingMissingLetter() {
        Flux<String> manyLetters = Flux
                .fromIterable(words)
                .flatMap(word -> Flux.fromArray(word.split(""))) // 汇总多个流
                .distinct()   // 去重
                .sort()  // 排序
                .zipWith(Flux.range(1, Integer.MAX_VALUE),
                        (string, count) -> String.format("%2d. %s", count, string));

        manyLetters.subscribe(System.out::println);
    }

    @Test
    public void restoringMissingLetter() {
        Mono<String> missing = Mono.just("s");
        Flux<String> allLetters = Flux
                .fromIterable(words)
                .flatMap(word -> Flux.fromArray(word.split("")))
                .concatWith(missing)
                .distinct()
                .sort()
                // 把元素按照对应索引位置匹配
                .zipWith(Flux.range(1, Integer.MAX_VALUE),
                        (string, count) -> String.format("%2d. %s", count, string));

        allLetters.subscribe(System.out::println);
    }

    @Test
    public void shortCircuit() {
        Flux<String> helloPauseWorld =
                Mono.just("Hello")
                        .concatWith(Mono.just("world")
                                .delaySubscription(Duration.ofMillis(500)));

        helloPauseWorld.subscribe(System.out::println);
    }
}
