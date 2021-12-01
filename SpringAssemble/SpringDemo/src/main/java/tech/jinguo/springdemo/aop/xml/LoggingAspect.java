package tech.jinguo.springdemo.aop.xml;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author jinguo
 */
@Component //将当前类交给IOC管理
@Aspect //标识当前类是一个切面
@Order(2)
public class LoggingAspect {
    //前置通知，在方法执行之前通知
    public void beforeAdvise(JoinPoint joinPoint) {
        //获取方法名
        String methodName = joinPoint.getSignature().getName();
        //获取传入的参数
        Object[] args = joinPoint.getArgs();
        System.out.println("XML Logging: The method " + methodName + " begins with " + Arrays.toString(args));
    }

    //后置通知，在方法执行之后通知，不管是否发生异常
    public void afterAdvise(JoinPoint joinPoint) {
        //获取方法名
        String methodName = joinPoint.getSignature().getName();
        System.out.println("XML Logging: The method " + methodName + " ends ");
    }

    //返回通知，在返回结果之后执行
    //value,pointcut都可以
    //设置的returing的值要和传入的形参的名字一致
    public void afterReturning(JoinPoint joinPoint, Object result) {
        //获取方法名
        String methodName = joinPoint.getSignature().getName();
        System.out.println("XML Logging: The method " + methodName + " returns " + result);
    }

    //异常通知，当方法执行出现异常时执行
    //设置的throwing的值要和传入的形参的名字一致
    public void afterThrowing(JoinPoint joinPoint, Exception e) {
        //获取方法名
        String methodName = joinPoint.getSignature().getName();
        System.out.println("XML Logging: The method " + methodName + " throwing " + e);
    }

    //环绕通知，相当于动态代理的全过程
    public Object around(ProceedingJoinPoint pjp){
        //获取方法名
        String methodName = pjp.getSignature().getName();
        //获取参数
        Object[] args = pjp.getArgs();
        Object result = null;
        try{
            //前置通知
            System.out.println("XML Around Logging: The method " + methodName + " begins with " + Arrays.toString(args));
            //执行目标方法
            result = pjp.proceed();
            //返回通知
            System.out.println("XML Around Logging: The method " + methodName + " returns " + result);
        } catch (Throwable throwable){
            //异常通知
            System.out.println("XML Around Logging: The method " + methodName + " throwing " + throwable);
            throwable.printStackTrace();
        } finally {
            //后置通知
            System.out.println("XML Around Logging: The method " + methodName + " ends ");
        }
        return result;
    }

}
