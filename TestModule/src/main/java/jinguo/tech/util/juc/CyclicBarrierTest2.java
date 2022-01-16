package jinguo.tech.util.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 使用含有Runnable参数的构造函数；和之前 CountDownLatch 的案例相似，
 * 公司组织出游，这时候肯定有很多大巴在等待接送，大巴不会等所有的 人都到才出发，而是每坐满一辆车就出发一辆，
 * 这种场景我们就可以使用 CyclicBarrier 来实现，实现如下：
 *
 * @author jinguo
 */
public class CyclicBarrierTest2 {
    public static void main(String[] args) {
        //公司人数
        int peopleNum = 2000;
        //每二十五个人一辆车，凑够二十五则发车~
        CyclicBarrier cyclicBarrier = new CyclicBarrier(25, () -> {
            //达到25人出发
            System.out.println("------------25人数凑齐出发------------");
        });

        for (int j = 1; j <= peopleNum; j++) {
            new Thread(new PeopleTask("People-" + j, cyclicBarrier)).start();
        }
    }

    static class PeopleTask implements Runnable {
        private String name;
        private CyclicBarrier cyclicBarrier;

        public PeopleTask(String name, CyclicBarrier cyclicBarrier) {
            this.name = name;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            System.out.println(name + "从家里出发，正在前往聚合地....");
            try {
                TimeUnit.MILLISECONDS.sleep(((int) (Math.random() * 1000)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + "到达集合地点，等待其他人..");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
