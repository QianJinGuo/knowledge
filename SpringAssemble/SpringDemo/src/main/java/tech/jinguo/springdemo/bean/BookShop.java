package tech.jinguo.springdemo.bean;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BookShop {
    private List<Book> books;

    private Map<Integer,Book> bookMap;

    private Book[] bookArray;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Map<Integer, Book> getBookMap() {
        return bookMap;
    }

    public void setBookMap(Map<Integer, Book> bookMap) {
        this.bookMap = bookMap;
    }

    public Book[] getBookArray() {
        return bookArray;
    }

    public void setBookArray(Book[] bookArray) {
        this.bookArray = bookArray;
    }

    @Override
    public String toString() {
        return "BookShop{" +
                "books=" + books +
                ", bookMap=" + bookMap +
                ", bookArray=" + Arrays.toString(bookArray) +
                '}';
    }
}
