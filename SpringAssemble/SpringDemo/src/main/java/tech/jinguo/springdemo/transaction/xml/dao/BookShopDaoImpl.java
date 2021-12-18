package tech.jinguo.springdemo.transaction.xml.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import tech.jinguo.springdemo.transaction.dao.BookShopDao;

public class BookShopDaoImpl implements BookShopDao {
    JdbcTemplate jdbcTemplate;
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

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
