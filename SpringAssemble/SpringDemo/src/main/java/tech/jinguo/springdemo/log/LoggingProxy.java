package tech.jinguo.springdemo.log;

import java.lang.reflect.Proxy;
import java.util.Arrays;


/**
 * @author jinguo
 */
public class LoggingProxy {
    private Object target;

    public LoggingProxy(Object target) {
        this.target = target;
    }

    public Object getProxy() {
        //获取被代理对象的类加载器
        ClassLoader classLoader = target.getClass().getClassLoader();
        //获取被代理对象实现的接口们
        Class<?>[] interfaces = target.getClass().getInterfaces();
        return Proxy.newProxyInstance(classLoader, interfaces, (proxy, method, args) -> {
            /**
             * invoke方法中就是要执行的拓展功能
             * 该方法中的参数说明
             *  proxy:传入的代理对象
             *  method:要调用的方法
             *  args:调用方法时传入的参数
             */
            System.out.println(proxy.getClass().getName());
            //获取方法名
            String methodName = method.getName();
            System.out.println("Logging:The method " + methodName + " begin with " + Arrays.toString(args));
            //执行目标方法，将方法的调用转到被代理的真实对象上
            Object result = method.invoke(target, args);
            System.out.println("Logging:The method " + methodName + " return " + result);
            return result;
        });
    }

}
