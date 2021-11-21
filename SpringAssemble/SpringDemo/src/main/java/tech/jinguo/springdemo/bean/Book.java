package tech.jinguo.springdemo.bean;

public class Book {
    private Integer id;
    private String title;
    private String author;
    private Double price;
    private Integer sales;

    public Book() {
    }

    public Book(Integer id, String title, String author, Double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public Book(Integer id, String title, String author, Integer sales) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.sales = sales;
    }

    public Book(Integer id, String title, String author, Double price, Integer sales) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.sales = sales;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", sales=" + sales +
                '}';
    }
}
