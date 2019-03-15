package algorithm.链表.day01;

import algorithm.util.Node;

import java.util.Objects;

/**
 * @ClassName Test01
 * @Description
 * @Author wxi.wang
 * @Date 2019/2/22 16:31
 */
public class Test01 {    // 166
    /**
     * 在链表的头部插入节点. 具体的增加思路与图文教程描述，参照《算法》P104.
     *
     * @param first 链表的第一个节点
     * @return
     */
    public static Node insertOnHead(Node first) {
        // 判空处理
        Objects.requireNonNull(first);
        Node oldFirst = first;
        // 构造新的节点
        first = new Node("not");
        first.next = oldFirst;
        return first;
    }

    /**
     * 从表头删除节点 -> 删除第一个节点
     *
     * @param first 第一个节点
     * @return
     */
    public static Node deleteOnHead(Node first) {
        first = first.next;
        return first;
    }

    /**
     * 在链表尾部插入节点
     *
     * @param tail 链表尾节点
     */
    public static void insertOnTail(Node tail) {
        Node newTail = new Node("tail");
        tail.next = newTail;
        newTail.next = null;
    }


    public static void display(Node first) {
        Node temp = first;
        while (temp != null) {
            System.out.print(temp.val +"  ");
            temp = temp.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node first = new Node("to");
        Node second = new Node("be");
        Node third = new Node("or");
        first.next = second;
        second.next = third;
        // 在表头插入节点
        first = insertOnHead(first);
        display(first);
        // 从表头删除节点
        display(deleteOnHead(first));
        insertOnTail(third);
        display(first);
    }
}