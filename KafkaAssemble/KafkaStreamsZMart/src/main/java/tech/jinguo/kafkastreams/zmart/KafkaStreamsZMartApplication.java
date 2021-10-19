/*
 *
 * @Project: knowledge
 * @File: KafkaStreamsZMartApplication
 * @Package: tech.jinguo.kafkastreams.zmart
 * @Date: 2021/10/18 15:02
 * @Author: by jinguo.qian
 *
 */
package tech.jinguo.kafkastreams.zmart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author jinguo.qian
 * @title: KafkaStreamsZMartApplication
 * @projectName knowledge
 * @description: Zmart
 * @date 2021/10/18 15:02
 */
@SpringBootApplication
@ComponentScan({"tech.jinguo.**.config","tech.jinguo.**.consumer"})
public class KafkaStreamsZMartApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaStreamsZMartApplication.class,args);
    }
}
    