package tech.jinguo.springdemo;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tech.jinguo.springdemo.bean.Book;
import tech.jinguo.springdemo.bean.BookShop;
import tech.jinguo.springdemo.bean.CartItem;

import javax.sql.DataSource;
import java.sql.SQLException;

public class SpringTest {
    //创建IoC容器对象
    ApplicationContext ioc = new ClassPathXmlApplicationContext("beans.xml");

    @Test
    public void testBook() {
        Book book = (Book) ioc.getBean("book");
        System.out.println(book);
    }

    @Test
    public void testBook2() {
        Book book = (Book) ioc.getBean("book2");
        System.out.println(book);
    }

    @Test
    public void testBook3() {
        Book book = (Book) ioc.getBean("book3");
        System.out.println(book);
    }

    @Test
    public void testBook4() {
        Book book = (Book) ioc.getBean("book4");
        System.out.println(book);
    }

    @Test
    public void testBook5() {
        Book book = (Book) ioc.getBean("book5");
        System.out.println(book);
    }

    @Test
    public void testCarItem() {
        CartItem cartItem = (CartItem) ioc.getBean("cartItem");
        System.out.println(cartItem);
    }

    @Test
    public void testCarItem2() {
        CartItem cartItem = (CartItem) ioc.getBean("cartItem2");
        System.out.println(cartItem);
    }

    @Test
    public void testCarItem3() {
        Book book1 = (Book) ioc.getBean("book5");
        System.out.println(book1);
        CartItem cartItem = (CartItem) ioc.getBean("cartItem3");
        System.out.println(cartItem);
        Book book2 = (Book) ioc.getBean("book5");
        System.out.println(book2);
    }

    @Test
    public void testBookShop() {
        BookShop bookShop = ioc.getBean("bookShop", BookShop.class);
        System.out.println(bookShop);
    }

    @Test
    public void testBookShop2() {
        BookShop bookShop2 = ioc.getBean("bookShop2", BookShop.class);
        System.out.println(bookShop2);
    }

    @Test
    public void testCarItem4(){
        CartItem cartItem = ioc.getBean("cartItem4",CartItem.class);
        System.out.println(cartItem);
    }

    @Test
    public void testDruidSource(){
        DataSource dataSource = ioc.getBean("datasource", DruidDataSource.class);
        try {
            System.out.println(dataSource.getConnection());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void testDruidSource2(){
        DataSource dataSource = ioc.getBean("datasource2", DruidDataSource.class);
        try {
            System.out.println(dataSource.getConnection());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
