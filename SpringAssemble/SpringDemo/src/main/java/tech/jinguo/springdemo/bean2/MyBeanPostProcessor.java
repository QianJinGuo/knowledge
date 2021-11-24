package tech.jinguo.springdemo.bean2;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor {
    /**
     * 在初始化方法之前调用
     *
     * @param bean     传入的Bean对象
     * @param beanName Bean的名称，即bean的id属性值
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("在初始化方法之前执行");
        System.out.println("bean:" + bean + ",beanName：" + beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("在初始化方法之后执行：" + bean);
        System.out.println("bean:" + bean + ",beanName：" + beanName);
        return bean;
    }
}
