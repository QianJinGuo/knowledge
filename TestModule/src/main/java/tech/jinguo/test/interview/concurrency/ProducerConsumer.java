package tech.jinguo.test.interview.concurrency;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 生产者-消费者模式
 * 使用 BlockingQueue 实现线程安全的生产者-消费者模型。
 * BlockingQueue 内部已处理好同步，无需手动使用 synchronized/wait/notify。
 *
 * 面试考点：
 * 1. BlockingQueue 的 put/take 是阻塞操作，offer/poll 是非阻塞操作
 * 2. 常见实现：ArrayBlockingQueue（有界）、LinkedBlockingQueue（可选有界）
 * 3. 与 synchronized + wait/notify 方式相比，代码更简洁，性能更高
 * 4. 毒丸（Poison Pill）模式：生产者完成后向队列放入特殊标记，消费者收到后退出
 */
public class ProducerConsumer {

    private static final int QUEUE_CAPACITY = 5;
    /** 毒丸标记：消费者收到该值后退出 */
    private static final int POISON_PILL = -1;
    /** 消费者数量，决定需要放入多少个毒丸 */
    private static final int CONSUMER_COUNT = 2;

    static class Producer implements Runnable {
        private final BlockingQueue<Integer> queue;
        private final int consumerCount;

        Producer(BlockingQueue<Integer> queue, int consumerCount) {
            this.queue = queue;
            this.consumerCount = consumerCount;
        }

        @Override
        public void run() {
            try {
                for (int i = 1; i <= 10; i++) {
                    queue.put(i);
                    System.out.println("生产者生产: " + i + " | 队列大小: " + queue.size());
                    Thread.sleep(100);
                }
                // 生产完毕，向每个消费者发送一个毒丸信号
                for (int i = 0; i < consumerCount; i++) {
                    queue.put(POISON_PILL);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    static class Consumer implements Runnable {
        private final BlockingQueue<Integer> queue;
        private final String name;

        Consumer(BlockingQueue<Integer> queue, String name) {
            this.queue = queue;
            this.name = name;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    Integer item = queue.take();
                    if (item == POISON_PILL) {
                        // 收到毒丸，退出循环
                        System.out.println(name + " 收到结束信号，退出");
                        break;
                    }
                    System.out.println(name + " 消费: " + item);
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);

        Thread producer = new Thread(new Producer(queue, CONSUMER_COUNT), "生产者");
        Thread consumer1 = new Thread(new Consumer(queue, "消费者1"), "消费者1");
        Thread consumer2 = new Thread(new Consumer(queue, "消费者2"), "消费者2");

        producer.start();
        consumer1.start();
        consumer2.start();

        producer.join();
        consumer1.join();
        consumer2.join();
        System.out.println("所有任务完成");
    }
}
