package tech.jinguo.webflux.test;

import java.text.DecimalFormat;
import java.util.function.Function;

//interface MoneyFormat{
//    String format(int i);
//}
class Money{
    private final int money;
    Money(int money) {
        this.money = money;
    }
    public void formatMoney(Function<Integer,String> moneyFormat){
        System.out.println(moneyFormat.apply(this.money));
    }
}
public class Test2 {
    public static void main(String[] args) {
        Money myMoney = new Money(8888);
        myMoney.formatMoney(i-> new DecimalFormat("#,###").format(i));

        Function<Integer,String> moneyFormat = i-> new DecimalFormat("#,###").format(i);
        myMoney.formatMoney(moneyFormat.andThen(s->"money:" + s));
    }

}
