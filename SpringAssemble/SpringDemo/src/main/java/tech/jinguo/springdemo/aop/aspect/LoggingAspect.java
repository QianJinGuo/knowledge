package tech.jinguo.springdemo.aop.aspect;

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
    @Pointcut("execution(* tech.jinguo.springdemo.aop.beans.Calculator.*(..)))")
    public void pointCut() {}

    /**
     * 切入点表达式的表示方式
     * 1. execution (public int tech.jinguo.springdemo.aop.beans.Calculator.add(int,int))
     * -在tech.jinguo.springdemo.aop.beans.Calculator接口中add方法
     * 2. execution (public int tech.jinguo.springdemo.aop.beans.Calculator.*(int,int))
     * -在tech.jinguo.springdemo.aop.beans.Calculator接口中所有方法
     * 3. execution (public int Calculator.*(int,int))
     * -在同一个包下的Calculator接口中所有方法
     * 4. execution (* tech.jinguo.springdemo.aop.beans.Calculator.*(int,int))
     * -不按权限修饰符和返回值类型在同一个包下的Calculator接口中所有方法
     * 5. execution (* tech.jinguo.springdemo.aop.beans.Calculator.*(..))
     * -不按权限修饰符和返回值类型在同一个包下的Calculator接口中所有方法参数的类型和个数
     * 6. execution (* *.add(int,..))
     * -不按权限修饰符和返回值类型在任意一个包下的第一个参数为int类型的add方法
     * 6. execution (* *.*(..))
     * -不考虑权限修饰符和返回值的类型以及参数的类型和个数在所有接口方法中执行
     */
    //前置通知，在方法执行之前通知
    @Before(value = "execution(public int tech.jinguo.springdemo.aop.beans.Calculator.*(..))")
    public void beforeAdvise(JoinPoint joinPoint) {
        //获取方法名
        String methodName = joinPoint.getSignature().getName();
        //获取传入的参数
        Object[] args = joinPoint.getArgs();
        System.out.println("Logging: The method " + methodName + " begins with " + Arrays.toString(args));
    }

    //后置通知，在方法执行之后通知，不管是否发生异常
    @After("pointCut()")
    public void afterAdvise(JoinPoint joinPoint) {
        //获取方法名
        String methodName = joinPoint.getSignature().getName();
        System.out.println("Logging: The method " + methodName + " ends ");
    }

    //返回通知，在返回结果之后执行
    //value,pointcut都可以
    //设置的returing的值要和传入的形参的名字一致
    @AfterReturning(pointcut = "execution(* tech.jinguo.springdemo.aop.beans.Calculator.*(..)))", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        //获取方法名
        String methodName = joinPoint.getSignature().getName();
        System.out.println("Logging: The method " + methodName + " returns " + result);
    }

    //异常通知，当方法执行出现异常时执行
    //设置的throwing的值要和传入的形参的名字一致
    @AfterThrowing(pointcut = "execution(* tech.jinguo.springdemo.aop.beans.Calculator.*(..)))", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Exception e) {
        //获取方法名
        String methodName = joinPoint.getSignature().getName();
        System.out.println("Logging: The method " + methodName + " throwing " + e);
    }

    //环绕通知，相当于动态代理的全过程
    @Around("execution(* tech.jinguo.springdemo.aop.beans.Calculator.*(..)))")
    public Object around(ProceedingJoinPoint pjp){
        //获取方法名
        String methodName = pjp.getSignature().getName();
        //获取参数
        Object[] args = pjp.getArgs();
        Object result = null;
        try{
            //前置通知
            System.out.println("Around Logging: The method " + methodName + " begins with " + Arrays.toString(args));
            //执行目标方法
            result = pjp.proceed();
            //返回通知
            System.out.println("Around Logging: The method " + methodName + " returns " + result);
        } catch (Throwable throwable){
            //异常通知
            System.out.println("Around Logging: The method " + methodName + " throwing " + throwable);
            throwable.printStackTrace();
        } finally {
            //后置通知
            System.out.println("Around Logging: The method " + methodName + " ends ");
        }
        return result;
    }

}
