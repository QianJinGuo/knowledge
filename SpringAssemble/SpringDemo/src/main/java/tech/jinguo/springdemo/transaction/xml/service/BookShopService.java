package tech.jinguo.springdemo.transaction.xml.service;

public interface BookShopService {
    /**
     * 购买图书
     * @param userId
     * @param isbn
     */
    void purchase(int userId,String isbn);
}
