package 多线程.业务实例.无锁栈;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 无锁栈，利用链表实现先入后出。不加锁，使用CAS 实现并发安全
 * @param <E>
 */
public class ConcurrentStack<E> {
    AtomicReference<Node<E>> top = new AtomicReference<Node<E>>();

    public void push(E item) {
        Node<E> newHead = new Node<E>(item);
        Node<E> oldHead;
        while (true) {
            oldHead = top.get();
            newHead.next = oldHead;
            if (top.compareAndSet(oldHead, newHead)) {
                return;
            }
        }
    }

    public E pop() {
        while (true) {
            Node<E> oldHead = top.get();
            if (oldHead == null) {
                return null;
            }
            Node<E> newHead = oldHead.next;
            if (top.compareAndSet(oldHead, newHead)) {
                return oldHead.item;
            }
        }
    }

    private static class Node<E> {
        public final E item;
        public Node<E> next;

        public Node(E item) {
            this.item = item;
        }
    }
}
