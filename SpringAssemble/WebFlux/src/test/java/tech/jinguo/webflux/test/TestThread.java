package tech.jinguo.webflux.test;

public class TestThread {
    public static void main(String[] args) {
        new Thread(() -> System.out.println("ok")).start();
        Object a = (Runnable) () -> System.out.println("ok");
        Runnable b = () -> System.out.println("ok");
        System.out.println(a==b);

    }
}
