package tech.jinguo.springdemo;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tech.jinguo.springdemo.bean.Book;
import tech.jinguo.springdemo.bean2.BeanLife;
import tech.jinguo.springdemo.bean2.BeanScope;
import tech.jinguo.springdemo.bean2.MyFactoryBean;

public class SpringTest2 {
    //创建IoC容器对象
    ApplicationContext ioc = new ClassPathXmlApplicationContext("beans2.xml");

    /**
     * 测试工厂Bean
     */
    @Test
    public void testFactoryBean() {
        Book book = (Book) ioc.getBean("myFactoryBean");
        System.out.println(book);
        MyFactoryBean factoryBean = (MyFactoryBean) ioc.getBean("&myFactoryBean");
        System.out.println(factoryBean);
    }

    /**
     *  测试Bean的作用域
     */
    @Test
    public void  testBeanScope(){
        BeanScope beanScope = ioc.getBean("beanScope",BeanScope.class);
        System.out.println(beanScope);
        BeanScope beanScope2 = ioc.getBean("beanScope",BeanScope.class);
        System.out.println(beanScope2);
        System.out.println(beanScope.equals(beanScope2));
    }

    /**
     * 测试bean的生命周期
     */
    @Test
    public void testBeanLife(){
        BeanLife beanLife = (BeanLife) ioc.getBean("beanLife");
        System.out.println(beanLife+"三:可以使用了");
        //关闭spring容器
        ConfigurableApplicationContext cioc = (ConfigurableApplicationContext) ioc;
        cioc.close();
    }


}
