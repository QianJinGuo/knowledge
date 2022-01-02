package jinguo.tech.util.threads;

/**
 * @author jinguo
 */
public class ThreadJoinTimeTest {
    public static void main(String[] args) {
        ThreadBoy boy = new ThreadBoy();
        boy.start();
    }

    static class ThreadBoy extends Thread {
        @Override
        public void run() {
            System.out.println("男孩和女孩准备出去逛街");
            ThreadGirl girl = new ThreadGirl();
            girl.start();
            int time = 2000;
            try {
                girl.join(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("男孩等了" + time + ", 不想再等了，去打游戏了");
        }
    }

    static class ThreadGirl extends Thread {
        @Override
        public void run() {
            int time = 5000;
            System.out.println("女孩开始化妆,男孩在等待。。。");
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("女孩终于化妆完成了！，耗时" + time + "很生气");
        }
    }
}
