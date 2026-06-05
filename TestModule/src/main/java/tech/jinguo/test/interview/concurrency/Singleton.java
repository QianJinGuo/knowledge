package tech.jinguo.test.interview.concurrency;

/**
 * 单例模式的线程安全实现
 * 面试中常考的几种单例写法，重点掌握懒汉式（双重检查锁）和枚举方式。
 *
 * 1. 饿汉式：类加载时即初始化，线程安全，但可能造成资源浪费
 * 2. 懒汉式（双重检查锁DCL）：延迟初始化，volatile 防止指令重排序
 * 3. 静态内部类：利用类加载机制保证线程安全，推荐使用
 * 4. 枚举：最简洁，天然线程安全，防反射和序列化攻击，Effective Java 推荐
 */
public class Singleton {

    // ==================== 1. 饿汉式 ====================
    static class HungrySingleton {
        private static final HungrySingleton INSTANCE = new HungrySingleton();

        private HungrySingleton() {}

        public static HungrySingleton getInstance() {
            return INSTANCE;
        }
    }

    // ==================== 2. 懒汉式（双重检查锁 DCL）====================
    static class LazySingleton {
        // volatile 防止 JVM 指令重排序，确保对象完全初始化后才赋值给 instance
        private static volatile LazySingleton instance;

        private LazySingleton() {}

        public static LazySingleton getInstance() {
            if (instance == null) {
                synchronized (LazySingleton.class) {
                    if (instance == null) {
                        instance = new LazySingleton();
                    }
                }
            }
            return instance;
        }
    }

    // ==================== 3. 静态内部类 ====================
    static class StaticInnerSingleton {
        private StaticInnerSingleton() {}

        /** 内部类在第一次调用 getInstance() 时才加载，实现懒加载且线程安全 */
        private static class Holder {
            private static final StaticInnerSingleton INSTANCE = new StaticInnerSingleton();
        }

        public static StaticInnerSingleton getInstance() {
            return Holder.INSTANCE;
        }
    }

    // ==================== 4. 枚举（推荐）====================
    enum EnumSingleton {
        INSTANCE;

        public void doSomething() {
            System.out.println("EnumSingleton doSomething");
        }
    }

    public static void main(String[] args) {
        // 验证单例（同一对象）
        System.out.println(HungrySingleton.getInstance() == HungrySingleton.getInstance()); // true
        System.out.println(LazySingleton.getInstance() == LazySingleton.getInstance());     // true
        System.out.println(StaticInnerSingleton.getInstance() == StaticInnerSingleton.getInstance()); // true
        System.out.println(EnumSingleton.INSTANCE == EnumSingleton.INSTANCE);               // true

        EnumSingleton.INSTANCE.doSomething();
    }
}
