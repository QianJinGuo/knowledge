package tech.jinguo.springdemo.async;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = CommonAsyncJobs.class)
public class SpringTestConfiguration {
}
