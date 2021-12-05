package tech.jinguo.springdemo.jdbc.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;

/**
 * @author jinguo
 */
@Configuration
@ComponentScan(basePackages = "tech.jinguo.springdemo.jdbc.config")
//@PropertySource("classpath:druid.properties")
public class JDBCConfig {
/*    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.driverClassName}")
    private String driverClassName;

    @Value("${jdbc.maxActive}")
    private Integer maxActive;

    @Value("${jdbc.initialSize}")
    private Integer initialSize;*/

    @Resource(name = "myPropertyConfig")
    MyPropertyConfig propertyConfig;

  /*  @Bean
    public DruidDataSource annotationDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setUrl(url);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setMaxActive(maxActive);
        dataSource.setInitialSize(initialSize);
        return dataSource;
    }*/


      @Bean
      public DruidDataSource annotationDataSource(){
          DruidDataSource dataSource = new DruidDataSource();
          dataSource.setUsername(propertyConfig.getUsername());
          dataSource.setPassword(propertyConfig.getPassword());
          dataSource.setUrl(propertyConfig.getUrl());
          dataSource.setDriverClassName(propertyConfig.getDriverClassName());
          dataSource.setMaxActive(propertyConfig.getMaxActive());
          dataSource.setInitialSize(propertyConfig.getInitialSize());
          return dataSource;
      }

    @Bean
    public JdbcTemplate annotationJdbcTemplate(){
        return new JdbcTemplate(annotationDataSource());
    }
}
