/*
 * Copyright: 2021 jinguo.tech All rights reserved.
 * 注意：本内容仅限于jinguo.tech内部传阅，禁止外泄以及用于其他的商业目的
 *
 * @Project: kafka
 * @File: Producer
 * @Package: tech.jinguo.kafka
 * @Date: 2021/9/2 09:52
 * @Author: by jinguo.qian
 *
 */
package tech.jinguo.kafkastreams.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jinguo.qian
 * @title: Producer
 * @projectName kafka
 * @description: 生产者
 * @date 2021/9/2 9:52
 */
@RestController
public class Producer {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @GetMapping("/send")
    public void send(@RequestParam String param){

        String message = param;
        // 第一个参数 topic
        // 第二个参数 消息
        kafkaTemplate.send("streams-input-topic",message);
    }
}
    