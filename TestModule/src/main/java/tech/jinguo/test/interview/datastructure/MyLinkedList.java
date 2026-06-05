package tech.jinguo.test.interview.datastructure;

/**
 * 手写双向链表
 * 双向链表支持 O(1) 时间的头尾插入/删除，常用于实现 LRU 缓存等场景。
 *
 * 操作：
 * - addFirst(val)：在头部插入
 * - addLast(val)：在尾部插入
 * - removeFirst()：删除并返回头部元素
 * - removeLast()：删除并返回尾部元素
 * - size()：返回链表长度
 */
public class MyLinkedList {

    private static class Node {
        int val;
        Node prev;
        Node next;

        Node(int val) {
            this.val = val;
        }
    }

    /** 哨兵头节点 */
    private final Node head;
    /** 哨兵尾节点 */
    private final Node tail;
    private int size;

    public MyLinkedList() {
        head = new Node(0);
        tail = new Node(0);
        head.next = tail;
        tail.prev = head;
        size = 0;
    }

    /** 在头部插入节点 */
    public void addFirst(int val) {
        Node node = new Node(val);
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
        size++;
    }

    /** 在尾部插入节点 */
    public void addLast(int val) {
        Node node = new Node(val);
        node.prev = tail.prev;
        node.next = tail;
        tail.prev.next = node;
        tail.prev = node;
        size++;
    }

    /** 删除并返回头部元素，链表为空则返回 -1 */
    public int removeFirst() {
        if (size == 0) return -1;
        Node node = head.next;
        head.next = node.next;
        node.next.prev = head;
        size--;
        return node.val;
    }

    /** 删除并返回尾部元素，链表为空则返回 -1 */
    public int removeLast() {
        if (size == 0) return -1;
        Node node = tail.prev;
        tail.prev = node.prev;
        node.prev.next = tail;
        size--;
        return node.val;
    }

    /** 返回链表中元素个数 */
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node curr = head.next;
        while (curr != tail) {
            sb.append(curr.val);
            if (curr.next != tail) sb.append(" <-> ");
            curr = curr.next;
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        MyLinkedList list = new MyLinkedList();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.addFirst(0);
        System.out.println(list);           // [0 <-> 1 <-> 2 <-> 3]
        System.out.println(list.size());    // 4
        System.out.println(list.removeFirst()); // 0
        System.out.println(list.removeLast());  // 3
        System.out.println(list);           // [1 <-> 2]
    }
}
