package tech.jinguo.springdemo.transaction.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tech.jinguo.springdemo.transaction.service.BookShopService;
import tech.jinguo.springdemo.transaction.service.Cashier;

import java.util.List;

@Service(value="cachier")
public class CashierImpl implements Cashier {
    @Autowired
    private BookShopService bookShopService;
    @Transactional
    @Override
    public void checkout(int userId, List<String> isbns) {
        //遍历集合得到每一个图书的编号
        isbns.forEach(isbn->bookShopService.purchase(userId,isbn));
    }
}
