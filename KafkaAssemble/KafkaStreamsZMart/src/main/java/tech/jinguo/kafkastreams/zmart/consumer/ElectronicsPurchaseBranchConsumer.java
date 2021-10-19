/*
 *
 * @Project: knowledge
 * @File: CoffeePurchaseBranchConsumer
 * @Package: tech.jinguo.kafkastreams.zmart.consumer
 * @Date: 2021/10/19 16:02
 * @Author: by jinguo.qian
 *
 */
package tech.jinguo.kafkastreams.zmart.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author jinguo.qian
 * @title: CoffeePurchaseBranchConsumer
 * @projectName knowledge
 * @description: 购买电子产品类别的消费者
 * @date 2021/10/19 16:02
 */
@Component
@Slf4j
@KafkaListener(topics = {"electronics"},groupId = "electronics-purchases-consumer-group")
public class ElectronicsPurchaseBranchConsumer {
    @KafkaHandler
    public void receive(String message){
        log.info("我是消费者，我接收到的消息是："+message);
    }
}
    