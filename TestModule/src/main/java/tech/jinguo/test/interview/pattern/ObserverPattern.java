package tech.jinguo.test.interview.pattern;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 观察者模式（Observer Pattern）
 * 定义对象间的一种一对多依赖关系，当一个对象（Subject）状态改变时，
 * 所有依赖它的对象（Observer）都会自动收到通知并更新。
 *
 * 面试考点：
 * 1. 解耦发布者和订阅者
 * 2. Java 内置 java.util.Observable（已过时）和 java.util.Observer（已过时），
 *    实际项目推荐使用事件驱动（EventBus、Spring ApplicationEvent 等）
 * 3. 应用场景：消息通知、事件监听、MVC 模型中 View 监听 Model 变化
 */
public class ObserverPattern {

    /** 观察者接口 */
    interface Observer {
        void update(String event);
    }

    /** 被观察者接口 */
    interface Subject {
        void addObserver(Observer observer);
        void removeObserver(Observer observer);
        void notifyObservers(String event);
    }

    /** 具体被观察者：消息发布者 */
    static class EventPublisher implements Subject {
        // CopyOnWriteArrayList 保证在并发添加/删除观察者时迭代通知不抛 ConcurrentModificationException
        private final List<Observer> observers = new CopyOnWriteArrayList<>();

        @Override
        public void addObserver(Observer observer) {
            observers.add(observer);
        }

        @Override
        public void removeObserver(Observer observer) {
            observers.remove(observer);
        }

        @Override
        public void notifyObservers(String event) {
            for (Observer observer : observers) {
                observer.update(event);
            }
        }

        /** 发布事件 */
        public void publish(String event) {
            System.out.println("发布事件: " + event);
            notifyObservers(event);
        }
    }

    /** 具体观察者：消息订阅者 */
    static class EmailSubscriber implements Observer {
        private final String email;

        EmailSubscriber(String email) {
            this.email = email;
        }

        @Override
        public void update(String event) {
            System.out.println("邮件通知 [" + email + "]: 收到事件 -> " + event);
        }
    }

    /** 具体观察者：短信订阅者 */
    static class SmsSubscriber implements Observer {
        private final String phone;

        SmsSubscriber(String phone) {
            this.phone = phone;
        }

        @Override
        public void update(String event) {
            System.out.println("短信通知 [" + phone + "]: 收到事件 -> " + event);
        }
    }

    public static void main(String[] args) {
        EventPublisher publisher = new EventPublisher();

        Observer emailObserver = new EmailSubscriber("user@example.com");
        Observer smsObserver = new SmsSubscriber("13800138000");

        publisher.addObserver(emailObserver);
        publisher.addObserver(smsObserver);

        publisher.publish("订单创建成功");
        System.out.println("---");

        // 取消邮件订阅
        publisher.removeObserver(emailObserver);
        publisher.publish("订单发货");
    }
}
