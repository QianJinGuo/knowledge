package tech.jinguo.test.util.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 多个线程等待一个线程
 * 在我们生活中最典型的案例就是体育中的跑步，假设现在我们要进行一场赛跑，那么所有的选手都需要等待裁判员的起跑命令，
 * 这时候，我们将其抽象化每个选手对应的是一个线程，而裁判员也是一个线程，那么就是多个选手的线程再等待裁判员线程的命令来执行
 * 我们通过CountDownLatch来实现这一案例，那么等待的个数N就是上面的裁判线程的个数，即为 1
 * @author jinguo
 */
public class CountDownLatchTest {
    public static void main(String[] args) throws Exception {
        //模拟跑步比赛，裁判说开始，所有选手开始跑，我们可以使用countDownlatch来实现
        //这里需要等待裁判说开始，所以时等着一个线程
        CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "已准备");
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "开始跑~~");

        }, "选手1").start();
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "已准备");
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "开始跑~~");

        }, "选手2").start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("裁判：预备~~~");
        countDownLatch.countDown();
        System.out.println("裁判：跑~~~");
    }
}
