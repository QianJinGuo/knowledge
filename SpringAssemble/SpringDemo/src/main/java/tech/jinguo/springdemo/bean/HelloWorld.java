package tech.jinguo.springdemo.bean;

public class HelloWorld {
    private String name;

    public HelloWorld(){
        System.out.println("create");
    }

    public HelloWorld(String name) {
        this.name = name;
    }

    public void setName(String name) {
        System.out.println("set used"+name);
        this.name = name;
    }

    public void test(){
        System.out.println("Hello:"+name);
    }
}
