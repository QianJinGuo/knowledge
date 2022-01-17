package tech.jinguo.test.cglib;

public class CglibTest {
    public static void main(String[] args) {
        SmsService smsService = (SmsService) CglibProxyFactory.getProxy(SmsService.class);
        String result = smsService.sendMessage("cglib test");
        System.out.println(result);
    }
}
