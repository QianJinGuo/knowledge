package tech.jinguo.springdemo.bean2;

public class BeanScope {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public BeanScope(){
        System.out.println("四:BeanScope对象被创建");
    }
}
