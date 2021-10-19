/*
 *
 * @Project: knowledge
 * @File: FilterConsumer
 * @Package: tech.jinguo.kafkastreams.zmart.consumer
 * @Date: 2021/10/19 15:54
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
 * @title: FilterConsumer
 * @projectName knowledge
 * @description: 过滤消费金额消费者
 * @date 2021/10/19 15:54
 */
@Component
@Slf4j
@KafkaListener(topics = {"purchases"},groupId = "purchases-consumer-group")
public class FilterPurchaseConsumer {
    @KafkaHandler
    public void receive(String message){
        log.info("我是消费者，我接收到的消息是："+message);
    }
}
    