package tech.jinguo.springdemo.transaction;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tech.jinguo.springdemo.transaction.xml.service.BookShopService;

public class TranscationXmlTest {
    //创建IoC容器
    ApplicationContext ioc = new ClassPathXmlApplicationContext("classpath:beans-tx-xml.xml");

    @Test
    public void testBookShopService(){
        BookShopService bookShopService = (BookShopService) ioc.getBean("bookShopService-xml");
        bookShopService.purchase(1,"1001");
    }
}
