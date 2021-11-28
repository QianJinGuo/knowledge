package tech.jinguo.springdemo.annotation.config;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import tech.jinguo.springdemo.annotation.beans.User;

@ComponentScan(basePackages = {"tech.jinguo.springdemo.annotation"})
@Configuration //标识当前类是配置类，用来替代xml文件格式
public class SpringConfiguration {
    @Bean("user2")
    //直接@Scope(“prototype”)，这里测试是不生效的，再加上proxyMode
    //@Scope("prototype")
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public User getUser() {
        return new User();
    }
}
