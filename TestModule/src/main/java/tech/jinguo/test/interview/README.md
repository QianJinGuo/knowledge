# 面试准备

本目录整理了 Java 后端面试中的高频知识点，包含算法、数据结构、并发编程、设计模式等核心内容，所有示例均有详细注释。

## 目录结构

```
interview/
├── algorithm/          # 高频算法题
│   ├── TwoSum.java          - 两数之和（哈希表）
│   ├── ReverseLinkedList.java - 反转链表（迭代 & 递归）
│   ├── BinarySearch.java    - 二分查找及其变体
│   └── LRUCache.java        - LRU 缓存（哈希表 + 双向链表）
├── datastructure/      # 数据结构实现
│   ├── MinStack.java        - 最小栈（辅助栈）
│   └── MyLinkedList.java    - 双向链表
├── concurrency/        # 并发编程
│   ├── ProducerConsumer.java - 生产者-消费者（BlockingQueue）
│   └── Singleton.java       - 单例模式（4种线程安全写法）
└── pattern/            # 设计模式
    ├── ObserverPattern.java  - 观察者模式
    └── StrategyPattern.java  - 策略模式
```

## 高频算法题

| 题目 | 核心思路 | 时间复杂度 | 空间复杂度 |
|------|----------|-----------|-----------|
| 两数之和 | 哈希表 | O(n) | O(n) |
| 反转链表 | 双指针迭代 / 递归 | O(n) | O(1) / O(n) |
| 二分查找 | 左闭右闭区间 | O(log n) | O(1) |
| LRU 缓存 | 哈希表 + 双向链表 | O(1) | O(capacity) |

> 已有算法：`TestModule/src/main/java/tech/jinguo/test/util/sort/SortUtil.java`（快排、堆排序等）  
> 已有算法：`TestModule/src/main/java/tech/jinguo/test/util/BinarySearchUtil.java`（二分查找基础版）

## 并发编程要点

### 线程池参数（ThreadPoolExecutor）

```java
new ThreadPoolExecutor(
    corePoolSize,       // 核心线程数
    maximumPoolSize,    // 最大线程数
    keepAliveTime,      // 空闲线程存活时间
    unit,               // 时间单位
    workQueue,          // 任务队列
    threadFactory,      // 线程工厂
    handler             // 拒绝策略
);
```

拒绝策略：
- `AbortPolicy`（默认）：抛出 `RejectedExecutionException`
- `CallerRunsPolicy`：调用者线程执行任务
- `DiscardPolicy`：直接丢弃任务
- `DiscardOldestPolicy`：丢弃队列最老的任务

### 单例模式对比

| 方式 | 线程安全 | 懒加载 | 防反射 | 推荐度 |
|------|---------|--------|--------|--------|
| 饿汉式 | ✅ | ❌ | ❌ | ⭐⭐⭐ |
| 双重检查锁（DCL） | ✅（volatile） | ✅ | ❌ | ⭐⭐⭐⭐ |
| 静态内部类 | ✅ | ✅ | ❌ | ⭐⭐⭐⭐⭐ |
| 枚举 | ✅ | ❌ | ✅ | ⭐⭐⭐⭐⭐ |

## 设计模式速记

- **观察者模式**：发布-订阅，一对多通知（Spring 事件、MQ 消息）
- **策略模式**：封装算法族，消除 if-else，满足开闭原则
- **代理模式**：见 `TestModule/jdkproxy`（JDK 动态代理）、`cglib`（CGLIB 代理）、`staticproxy`（静态代理）

## 相关模块

- **并发工具**：`TestModule/src/main/java/tech/jinguo/test/util/juc/`（CountDownLatch、CyclicBarrier）
- **线程操作**：`TestModule/src/main/java/tech/jinguo/test/util/threads/`（死锁、ThreadLocal、线程池）
- **集合工具**：`TestModule/src/main/java/tech/jinguo/test/util/collections/`
- **Spring AOP/IoC**：`SpringAssemble/`
- **Kafka 流处理**：`KafkaAssemble/`
- **MyBatis**：`MybatisAssemble/`
