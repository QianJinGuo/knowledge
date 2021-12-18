package tech.jinguo.springdemo.transaction.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tech.jinguo.springdemo.transaction.dao.BookShopDao;
import tech.jinguo.springdemo.transaction.service.BookShopService;

import java.sql.Connection;
import java.sql.SQLException;

//@Transactional 加到类上，则类中所有方法都添加上事务
@Service("bookShopService")
public class BookShopServiceImpl implements BookShopService {
    @Autowired
    private BookShopDao bookShopDao;
    @Autowired
    DataSourceTransactionManager transactionManager;

    /**
     * @Transactional注解中的属性 propagation属性：用来设置事务的传播行为
     * 事务的传播行为：一个事务方法（开启了的事务）运行在另一个事务方法中时，当前方法是使用原来的事务还是开启一个新的事务
     * 1 propgation属性值说明：
     * Propagation.REQUIRED： 默认值。使用原来的事务（沿用checkout事务）
     * Propagation.REQUIRES_NEW：将原来的事务(checkout)挂起，开启一个新的事务(purchase)
     * Propagation.SUPPORTS：如果有事务在运行，当前的方法就在这个事务内运行（checkout有事务，purchase也支持），否则可以不运行在事务中(checkout没有事务，则purchase也没有)。
     * Propagation.NOT_SUPPORTED：当前的方法不应该运行在事务中，如果有运行的事务将它挂起。（不管checkout有没有事务，purchase都没有事务）
     * Propagation.MANDATORY：当前的方法必须运行在事务中，如果没有正在运行的事务就抛出异常。（如果checkout没有事务，则抛异常）
     * Propagation.NEVER：当前的方法不应该运行在事务中，如果有正在运行的事务就抛出异常。（如果checkout有事务，则抛异常）
     * Propagation.NESTED：如果有事务正在运行，当前的方法就应该在这个事务的嵌套事务内运行，否则就启动一个新的事务，并在它自己的事务内运行。
     * (https://stackoverflow.com/questions/12390888/differences-between-requires-new-and-nested-propagation-in-spring-transactions)
     * https://segmentfault.com/a/1190000015794446
     */
    /**
     * 2 isolation属性：用来设置事务的隔离级别
     * isolation属性值说明：
     *      isolation = Isolation.READ_UNCOMMITTED   读未提交  ->会引起脏读、不可重复读、幻读
     *      isolation = Isolation.READ_COMMITTED     读已提交（Oracle默认） ，会引起不可重复读，幻读
     *      isolation = Isolation.REPEATABLE_READ    可重复读（Mysql默认）  ，会引起幻读
     *      isolation = Isolation.SERIALIZABLE       串行化
     */
    /**
     * 3 设置异常回滚或不会滚相关属性
     *     rollbackFor: 设置哪些异常回滚，值是异常的类型   默认情况下，捕获到RuntimeException或Error时回滚，而捕获到编译时异常不回滚。
     *     rollbackForClassName: 设置哪些异常回滚，值是异常名字
     *     noRollbackFor: 设置哪些异常不回滚，，值是异常的类型
     *     noRollbackForClassName: 设置哪些异常不回滚，值是异常的名字
     */
    /**
     * 4 timeout属性： 用来设置事务超时的时间，单位是秒，超过该时间将自动回滚
     * 5 readonly属性：readOnly = true 只读属性可以设置这个事务只读取数据但不更新数据，这样可以帮助数据库引擎优化事务 （方法用于查询）
     */

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, noRollbackFor = ArithmeticException.class, timeout = 3, readOnly = true)
    @Override
    public void purchase(int userId, String isbn) {
        //获取图书的价格
        Double bookPrice = bookShopDao.getBookPrice(isbn);
        System.out.println("第一次查询图书的价格：" + bookPrice);
        Double bookPrice2 = bookShopDao.getBookPrice(isbn);
        System.out.println("第二次查询图书的价格：" + bookPrice2);
        //更新图书的库存
        bookShopDao.updateBookStock(isbn);
        //设置异常
        //int i = 10/0;
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        //更新用户账户的余额
        bookShopDao.updateUserAccount(userId, bookPrice);

        //编程式事务
       // Connection connection = transactionManager.getDataSource().getConnection()

    }
}
