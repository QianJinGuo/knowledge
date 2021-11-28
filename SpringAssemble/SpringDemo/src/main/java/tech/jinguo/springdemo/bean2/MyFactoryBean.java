package tech.jinguo.springdemo.bean2;

import org.springframework.beans.factory.FactoryBean;
import tech.jinguo.springdemo.bean.Book;

public class MyFactoryBean implements FactoryBean<Book> {


    @Override
    public Book getObject() throws Exception {
        return new Book(8, "SpringDemo2", "jinguo", 8.88, 888);
    }

    /**
     * 设置返回的MYbean类型
     *
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return Book.class;
    }

    /**
     * 是否单例
     *
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
}
