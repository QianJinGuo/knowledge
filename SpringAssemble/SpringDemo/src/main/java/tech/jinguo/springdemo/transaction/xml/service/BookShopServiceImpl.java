package tech.jinguo.springdemo.transaction.xml.service;

import tech.jinguo.springdemo.transaction.xml.dao.BookShopDaoImpl;

public class BookShopServiceImpl implements BookShopService {
    private BookShopDaoImpl bookShopDao;
    public void setBookShopDao(BookShopDaoImpl bookShopDao) {
        this.bookShopDao = bookShopDao;
    }

    @Override
    public void purchase(int userId, String isbn) {
        //获取图书的价格
        Double bookPrice = bookShopDao.getBookPrice(isbn);
        //更新图书的库存
        bookShopDao.updateBookStock(isbn);
        //更新用户账户的余额
        bookShopDao.updateUserAccount(userId, bookPrice);
    }


}
