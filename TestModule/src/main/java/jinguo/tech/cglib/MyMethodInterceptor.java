package jinguo.tech.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 方法拦截器
 * @author jinguo
 */
public class MyMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("before intercept " + method.getName());
        Object obj = methodProxy.invokeSuper(o,args);
        System.out.println("after intercept " + method.getName());
        return obj;
    }
}
