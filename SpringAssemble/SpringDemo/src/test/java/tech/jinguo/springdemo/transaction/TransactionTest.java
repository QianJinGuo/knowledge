package tech.jinguo.springdemo.transaction;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tech.jinguo.springdemo.transaction.dao.BookShopDao;
import tech.jinguo.springdemo.transaction.service.BookShopService;
import tech.jinguo.springdemo.transaction.service.Cashier;
import tech.jinguo.springdemo.transaction.service.impl.BookShopServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class TransactionTest {
    //创建IoC容器
    ApplicationContext ioc = new ClassPathXmlApplicationContext("classpath:beans-tx.xml");

    @Test
    public void testBookShopDao() {
        BookShopDao bookShopDao = ioc.getBean(BookShopDao.class);
        //测试获取图书价格的方法
        Double bookPrice = bookShopDao.getBookPrice("1001");
        System.out.println(bookPrice);
        //测试更新图书的库存的方法
        bookShopDao.updateBookStock("1001");
        //测试更新用户余额的方法
        bookShopDao.updateUserAccount(1,bookPrice);
    }

    @Test
    public void testBookShopService(){
        BookShopService bookShopService = (BookShopService) ioc.getBean("bookShopService");
        BookShopServiceImpl bookShopService2 = (BookShopServiceImpl) ioc.getBean("bookShopService");
        //bookShopService.purchase(1,"1001");
        //如果purchase加了事物，会出现ClassCastException异常,$Proxy15代理对象无法强转成实现类对象
        // 因为事务采用的是Spring Aop实现，Aop是基于动态代理机制,Spring中默认是基于JDK动态代理面向接口的，也可以通过配置改为CGLIB (SpringBoot自动装配修改了参数，默认是Cglib)
        //https://www.cnblogs.com/nightOfStreet/p/13185406.html
        //当我增加了配置  <aop:config proxy-target-class="true"/> 后，这里就通过了
        bookShopService2.purchase(1,"1001");
    }

    @Test
    public void testCashier(){
        Cashier cashier = (Cashier) ioc.getBean("cachier");
        //创建一个List
        List<String> isbns = new ArrayList<>();
        isbns.add("1001");
        isbns.add("1002");
        //买多本图书
        cashier.checkout(1,isbns);

    }
}
