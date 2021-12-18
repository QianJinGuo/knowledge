package tech.jinguo.springdemo.transaction.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tech.jinguo.springdemo.transaction.dao.BookShopDao;
@Repository
public class BookShopDaoImpl implements BookShopDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Double getBookPrice(String isbn) {
        //写sql语句
        String sql ="select price from book where isbn =?";
        Double bookPrice = jdbcTemplate.queryForObject(sql,Double.class,isbn);
        return bookPrice;
    }

    @Override
    public void updateBookStock(String isbn) {
        //写sql语句
        String sql = "update book set stock = stock - 1 where isbn = ?";
        jdbcTemplate.update(sql,isbn);
    }

    @Override
    public void updateUserAccount(int userId, Double bookPrice) {
        //写sql语句
        String sql = "update account set balance = balance - ? where id = ?";
        jdbcTemplate.update(sql,bookPrice,userId);
    }
}
