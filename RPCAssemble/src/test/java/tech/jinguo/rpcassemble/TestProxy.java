package tech.jinguo.rpcassemble;

import java.lang.reflect.Proxy;

public class TestProxy {
    public static void main(String[] args) {
        JDKProxy proxy = new JDKProxy(new RealHello());
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        Hello hello = (Hello) Proxy.newProxyInstance(classLoader,new Class[]{Hello.class},proxy);
        System.out.println(hello.say());
    }
}
