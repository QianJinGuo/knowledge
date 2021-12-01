package tech.jinguo.springdemo.aop.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author jinguo
 */
@Aspect
@Component
//可以通过@Order设置切面的优先级，value属性默认int的最大值，值越小优先级越高
@Order(1)
public class ValidateAspect {
    @Pointcut(value = "execution(* tech.jinguo.springdemo.aop.beans.Calculator.*(..))")
    public void pointCut(){}

    @Before(value = "pointCut()")
    public void beforeAdvise(){
        System.out.println("验证参数是否合法");
    }
}
