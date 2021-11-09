package tech.jinguo.webflux.test;

/**
 * 单一责任制
 */
@FunctionalInterface
interface Interface1 {
    int doubleNum(int i);

    default int add(int x, int y){
        this.doubleNum(x);
        return x+y;
    }
}

@FunctionalInterface
interface Interface2 {
    int doubleNum(int i);

    default int add(int x, int y){
        this.doubleNum(x);
        return x+y;
    }
}

interface Interface3 extends Interface1,Interface2{

    @Override
    default int doubleNum(int i) {
        return 0;
    }

    @Override
    default int add(int x, int y) {
        return Interface1.super.add(x, y);
    }
}

public class LambdaDemo {
    public static void main(String[] args) {
        Interface1 i1 = (i) -> i * 2;
        System.out.println(i1.add(3,5));
        System.out.println(i1.doubleNum(3));

        Interface1 i2 = i -> i * 2;
        Interface1 i3 = (int i) -> i * 2;
        Interface1 i4 = (int i) ->{
            System.out.println("ok");
            return (i * 2);
        };


    }
}

