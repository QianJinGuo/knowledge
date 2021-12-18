package tech.jinguo.springdemo.transaction.xml.dao;

public interface BookShopDao {
    //根据图书的编号获取图书的价格
    Double getBookPrice(String isbn);
    //根据图书的编号更新图书的库存，默认一次只能买一本图书
    void updateBookStock(String isbn);
    //根据用户的id和图书的价格更新用户账户余额
    void updateUserAccount(int userId,Double bookPrice);
}
