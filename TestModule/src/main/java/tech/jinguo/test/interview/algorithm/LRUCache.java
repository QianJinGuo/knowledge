package tech.jinguo.test.interview.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * LRU 缓存（LeetCode #146）
 * 请你设计并实现一个满足 LRU（最近最少使用）缓存约束的数据结构。
 * 实现 LRUCache 类：
 * - LRUCache(int capacity) 以正整数作为容量 capacity 初始化 LRU 缓存
 * - int get(int key) 如果关键字 key 存在于缓存中，则返回关键字的值，否则返回 -1
 * - void put(int key, int value) 如果关键字 key 已经存在，则变更其数据值；
 *   如果不存在，则向缓存中插入该组 key-value。
 *   如果插入操作导致关键字数量超过 capacity，则应该逐出最久未使用的关键字。
 *
 * 思路：哈希表 + 双向链表，get/put 操作均为 O(1)
 */
public class LRUCache {

    /** 双向链表节点 */
    private static class Node {
        int key;
        int value;
        Node prev;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final Map<Integer, Node> cache;
    /** 虚拟头节点（最近使用端） */
    private final Node head;
    /** 虚拟尾节点（最久未使用端） */
    private final Node tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        // 使用哨兵节点，避免边界判断
        this.head = new Node(0, 0);
        this.tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        Node node = cache.get(key);
        if (node == null) {
            return -1;
        }
        // 将访问的节点移到链表头部（最近使用）
        moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        Node node = cache.get(key);
        if (node != null) {
            // key 已存在：更新值并移到头部
            node.value = value;
            moveToHead(node);
        } else {
            // key 不存在：新建节点
            Node newNode = new Node(key, value);
            cache.put(key, newNode);
            addToHead(newNode);
            if (cache.size() > capacity) {
                // 超出容量，删除链表尾部节点（最久未使用）
                Node removed = removeTail();
                cache.remove(removed.key);
            }
        }
    }

    private void addToHead(Node node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void moveToHead(Node node) {
        removeNode(node);
        addToHead(node);
    }

    private Node removeTail() {
        Node node = tail.prev;
        removeNode(node);
        return node;
    }

    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(2);
        lruCache.put(1, 1);   // 缓存: {1=1}
        lruCache.put(2, 2);   // 缓存: {1=1, 2=2}
        System.out.println(lruCache.get(1));  // 返回 1
        lruCache.put(3, 3);   // 该操作会使关键字 2 作废，缓存: {1=1, 3=3}
        System.out.println(lruCache.get(2));  // 返回 -1 (未找到)
        lruCache.put(4, 4);   // 该操作会使关键字 1 作废，缓存: {4=4, 3=3}
        System.out.println(lruCache.get(1));  // 返回 -1 (未找到)
        System.out.println(lruCache.get(3));  // 返回 3
        System.out.println(lruCache.get(4));  // 返回 4
    }
}
