package tech.jinguo.test.util.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 假设你要和你女朋友约会，约定了一个时间地点（餐厅），那么不管你们谁先到都会等待另一个到才会出发去约会（开动）~
 * 那么这时候我们通过CyclicBarrier的来实现，这里我们需要来拦截的线程就是两个。具体实现 如下：
 * @author jinguo
 */
public class CyclicBarrierTest {
    /*
    CyclicBarrier 与countDownLatch 比较相似，也是等待线程完成，
    不过countDownLatch 是await等待其他的线程通过countDown的数量，达到一定数则执行，
    而 CyclicBarrier 则是直接看await的数量，达到一定数量直接全部执行，
    */
    public static void main(String[] args) {
        //好比情侣约会，不管谁先到都的等另一个，这里就是两个线程，
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        new Thread(() -> {
            System.out.println("快速收拾，出门~~~");
            try {
                TimeUnit.MILLISECONDS.sleep(500);
                System.out.println("到了约会地点等待女朋友前来~~");
                cyclicBarrier.await();
                System.out.println("女朋友到来嗨皮出发~~约会");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }, "男朋友").start();
        new Thread(() -> {
            System.out.println("慢慢收拾，出门~~~");
            try {
                TimeUnit.MILLISECONDS.sleep(5000);
                System.out.println("到了约会地点等待男朋友前来~~");
                cyclicBarrier.await();
                System.out.println("男朋友到来嗨皮出发~~约会");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }, "女朋友").start();
    }
}
