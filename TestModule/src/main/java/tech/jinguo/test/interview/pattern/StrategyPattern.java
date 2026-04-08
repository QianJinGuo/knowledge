package tech.jinguo.test.interview.pattern;

/**
 * 策略模式（Strategy Pattern）
 * 定义一系列算法，将每个算法封装起来，使它们可以互换。
 * 策略模式使得算法可以独立于使用它的客户端而变化。
 *
 * 面试考点：
 * 1. 消除 if-else / switch 分支，提高代码可扩展性（对扩展开放，对修改关闭）
 * 2. 与工厂模式结合使用，通过工厂根据条件选择策略
 * 3. 应用场景：支付方式、排序算法选择、促销策略等
 */
public class StrategyPattern {

    /** 策略接口：支付方式 */
    interface PayStrategy {
        boolean pay(int amount);
    }

    /** 具体策略：支付宝支付 */
    static class AlipayStrategy implements PayStrategy {
        private final String account;

        AlipayStrategy(String account) {
            this.account = account;
        }

        @Override
        public boolean pay(int amount) {
            System.out.println("使用支付宝账户 [" + account + "] 支付 " + amount + " 元");
            return true;
        }
    }

    /** 具体策略：微信支付 */
    static class WechatPayStrategy implements PayStrategy {
        private final String openId;

        WechatPayStrategy(String openId) {
            this.openId = openId;
        }

        @Override
        public boolean pay(int amount) {
            System.out.println("使用微信 [" + openId + "] 支付 " + amount + " 元");
            return true;
        }
    }

    /** 具体策略：银行卡支付 */
    static class BankCardStrategy implements PayStrategy {
        private final String cardNumber;

        BankCardStrategy(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        @Override
        public boolean pay(int amount) {
            System.out.println("使用银行卡 [" + cardNumber + "] 支付 " + amount + " 元");
            return true;
        }
    }

    /** 环境类：购物车，持有支付策略 */
    static class ShoppingCart {
        private PayStrategy payStrategy;

        /** 运行时设置策略（策略可以动态切换） */
        public void setPayStrategy(PayStrategy payStrategy) {
            this.payStrategy = payStrategy;
        }

        public void checkout(int amount) {
            if (payStrategy == null) {
                throw new IllegalStateException("未选择支付方式");
            }
            boolean success = payStrategy.pay(amount);
            System.out.println("支付" + (success ? "成功" : "失败"));
        }
    }

    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();

        // 选择支付宝支付
        cart.setPayStrategy(new AlipayStrategy("user@alipay.com"));
        cart.checkout(100);

        // 切换为微信支付
        cart.setPayStrategy(new WechatPayStrategy("wx_openid_123"));
        cart.checkout(200);

        // 切换为银行卡支付
        cart.setPayStrategy(new BankCardStrategy("6222 **** **** 1234"));
        cart.checkout(300);
    }
}
