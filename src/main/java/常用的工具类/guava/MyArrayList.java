package 常用的工具类.guava;

import com.google.common.collect.ForwardingList;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author wxi.wang
 * 18-12-22
 * 测试ForwardingList方法,, 对应测试test11
 */
@Slf4j
public class MyArrayList<E> extends ForwardingList<E> {
    List<E> delegate;

    /**
     * 只能使用构造函数来初始化代理容器,这样每次返回的都是同一个容器
     */
    public MyArrayList(List<E> list) {
        this.delegate = list;
    }

    @Override
    protected List<E> delegate() {
        /**
         * 需要返回当前的list容器,不能在这里new ArrayList<>(),,因为你每次调用ForwardingList里面的
         *  size(),add()方法时,都是调用delegate().add(element);delegate().size().
         *  注意上面的这个delegate().size()方法,每次都需要调用delegate()这个方法,
         *  如果在此方法里面new ArrayList的话,这样每次都创建一个新的容器.. 就不会有size()了...添加一个元素,它的size()并没有变
         */
        return delegate;
    }

    @Override
    public boolean add(E element) {
        log.info("ssssssssssssssssss11");
        return super.add(element);
    }

}
