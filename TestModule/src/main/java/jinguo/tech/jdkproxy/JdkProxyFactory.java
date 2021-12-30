package jinguo.tech.jdkproxy;

import java.lang.reflect.Proxy;

/**
 * @author jinguo
 */
public class JdkProxyFactory {
    public static Object getPorxy(Object target){
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),new MyJdkProxy(target));
    }
}
