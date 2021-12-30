package jinguo.tech.staticproxy;

public class MyStaticProxy implements SmsService {
    private final SmsServiceImpl target;

    public MyStaticProxy(SmsServiceImpl target) {
        this.target = target;
    }

    @Override
    public String sendMessage(String message) {
        System.out.println("before send");
        String result = target.sendMessage(message);
        System.out.println("after send");
        return result;
    }
}
