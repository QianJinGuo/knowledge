package tech.jinguo.springdemo.bean2;

public class BeanLife {
    private String name;

    public BeanLife() {
        System.out.println("一:BeanLife被创建了");
    }

    public void setName(String name) {
        System.out.println("二:name属性被赋值");
        this.name = name;
    }

    public void init(){
        System.out.println("三:BeanLife对象被初始化");
    }

    public void destory(){
        System.out.println("BeanLife对象被销毁");
    }


}
