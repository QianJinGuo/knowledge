/*
 * Copyright: 2021 jinguo.tech Inc. All rights reserved.
 * 注意：本内容仅限于jinguo.tech内部传阅，禁止外泄以及用于其他的商业目的
 *
 * @Project: kafka
 * @File: Consumer
 * @Package: tech.jinguo.kafka
 * @Date: 2021/9/2 09:52
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
 * @title: Consumer
 * @projectName kafka
 * @description: 奖励消费者
 * @date 2021/9/2 9:52
 */
@Component
@Slf4j
@KafkaListener(topics = {"rewards"}, groupId = "rewards-consumer-group")
public class RewardsConsumer {
    @KafkaHandler
    public void receive(String message) {
        log.info("我是消费者，我接收到的消息是：" + message);
    }
}
    