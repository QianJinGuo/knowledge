package jinguo.tech.jdkproxy;

public class JdkProxyTest {
    public static void main(String[] args) {
        SmsService smsService = (SmsService) JdkProxyFactory.getPorxy(new SmsServiceImpl());
        System.out.println(smsService.sendMessage("jdk proxy"));
    }
}
