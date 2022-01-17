package tech.jinguo.test.util.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 一个/多个线程等待多个线程
 * 同样从我们生活中的场景来抽象，假设公司要组织团建，大巴车接送，当凑够五个人大巴车则发车出发，这里就是大巴车需要等待这五个人全部到齐才能继续执行，
 * 我们抽象之后用CountDownLatch来实现，那么的计数个数N则为5，因为要等待这五个，通过代码实现如下
 * @author jinguo
 */

public class CountDownLatchTest2 {
    public static void main(String[] args) throws InterruptedException {
        //等待的个数
        CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "从住所出发...");
                try {
                    TimeUnit.SECONDS.sleep((long) (Math.random() * 10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " 到达目的地-----");
                countDownLatch.countDown();
            }, "人员-" + i).start();
        }
        System.out.println("大巴正在等待人员中.....");
        countDownLatch.await();
        System.out.println("-----所有人到齐，出发-----");
    }
}
