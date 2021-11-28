package tech.jinguo.springdemo.log;

import org.junit.Test;

public class CalculatorTest {
    private Calculator calculator = new CalculatorImpl();
    @Test
    public void testCalculator(){
        System.out.println(calculator.add(10,2));
        System.out.println(calculator.sub(10,2));
        System.out.println(calculator.mul(10,2));
        System.out.println(calculator.div(10,2));
    }

    @Test
    public void testLoggingProxy(){
        //创建被代理对象
        CalculatorImpl calculator = new CalculatorImpl();
        //获取代理对象
        Calculator proxy = (Calculator) new LoggingProxy(calculator).getProxy();
        proxy.add(10,2);
        proxy.sub(10,2);
        proxy.mul(10,2);
        proxy.div(10,2);
    }
}
