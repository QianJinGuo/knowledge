package jinguo.tech.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author jinguo
 */
public class MyJdkProxy implements InvocationHandler {
    /**
     * 代理类中的真实对象
     */
    private final Object target;

    public MyJdkProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before invoke " + method.getName());
        Object obj = method.invoke(target, args);
        System.out.println("after invoke " + method.getName());
        return obj;
    }
}
