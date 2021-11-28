package tech.jinguo.springdemo.log;

public class CalculatorImpl implements Calculator {

    @Override
    public int add(int a, int b) {
        //System.out.printf("Logging:The add method add begins with [%d,%d] \n", a, b);
        int result = a + b;
        //System.out.printf("Logging:The add method add returns %d \n", result);
        return result;
    }

    @Override
    public int sub(int a, int b) {
        //System.out.printf("Logging:The sub method add begins with [%d,%d] \n", a, b);
        int result = a - b;
        //System.out.printf("Logging:The sub method add returns %d \n", result);
        return result;
    }

    @Override
    public int mul(int a, int b) {
        //System.out.printf("Logging:The mul method add begins with [%d,%d] \n", a, b);
        int result = a * b;
        //System.out.printf("Logging:The mul method add returns %d \n", result);
        return result;
    }

    @Override
    public int div(int a, int b) {
        //System.out.printf("Logging:The div method add begins with [%d,%d] \n", a, b);
        int result = a / b;
        //System.out.printf("Logging:The div method add returns %d \n", result);
        return result;
    }
}
