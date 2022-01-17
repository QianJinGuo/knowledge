package tech.jinguo.test.staticproxy;

public class StaticProxyTest {
    public static void main(String[] args) {
        SmsService smsService = new MyStaticProxy(new SmsServiceImpl());
        System.out.println(smsService.sendMessage("static proxy"));
    }
}
