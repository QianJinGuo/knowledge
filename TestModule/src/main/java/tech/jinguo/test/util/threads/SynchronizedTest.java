package tech.jinguo.test.util.threads;

/**
 * @author jinguo
 */
public class SynchronizedTest {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName()+"开始运行!");
        Person p0 = new Person();
        Person p1 = new Person();
        Thread t0 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+" 开始执行");
            //p0.eat();
            Person.sleep();
            System.out.println(Thread.currentThread().getName()+" 执行完成");
        });
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+" 开始执行");
            /**
             * t1执行的是p0.eat();
             */
            //t0执行完成后,t1才接着执行
            //p0.eat();
            //p0.work();

            //t0,t1乱序执行
            //p0.walk();
            //Person.sleep();
            //Person.travel();
            //p1.eat();
            //p1.work();
            //p1.walk();

            /**
             * t1执行的是Person.sleep(); 或 Person.travel()
             */
            //t0执行完成后,t1才接着执行
            //Person.sleep();
            Person.travel();

            //t0,t1乱序执行
            //p0.walk();
            //p1.walk();
            //p0.eat();
            //p1.eat();
            //p0.work();
            //p1.work();

            System.out.println(Thread.currentThread().getName()+" 执行完成");
        });
        t0.start();
        t1.start();

    }
}

class Person {
    public void walk() {
        for(int i=0;i<10;i++){
            System.out.println("person needs walking " + Thread.currentThread().getName());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void eat() {
        for(int i=0;i<10;i++){
            System.out.println("person needs eating " + Thread.currentThread().getName());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void work() {
        for(int i=0;i<10;i++){
            System.out.println("person needs working " + Thread.currentThread().getName());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized void sleep() {
        for(int i=0;i<10;i++){
            System.out.println("person needs sleeping " + Thread.currentThread().getName());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized void travel() {
        for(int i=0;i<10;i++){
            System.out.println("person needs travelling " + Thread.currentThread().getName());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
