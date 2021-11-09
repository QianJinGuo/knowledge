package tech.jinguo.rocketmqdemo;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class Consumer implements InitializingBean {

    @Value("${rocketmq.topic}")
    private String topic;
    @Value("${rocketmq.nameserv}")
    private String nameServ;
    @Value("${rocketmq.consumer.group}")
    private String consumerGroup;

    @Override
    public void afterPropertiesSet() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(nameServ);
        //消费模式:一个新的订阅组第一次启动从队列的最后位置开始消费 后续再启动接着上次消费的进度开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        //订阅主题和 标签（ * 代表所有标签)下信息
        consumer.subscribe(topic, "*");
        // //注册消费的监听 并在此监听中消费信息，并返回消费的状态信息
        consumer.registerMessageListener((MessageListenerConcurrently) (msgList, context) -> {
            // msgs中只收集同一个topic，同一个tag，并且key相同的message
            // 会把不同的消息分别放置到不同的队列中
            for (Message msg : msgList) {
                //消费者获取消息 这里只输出 不做后面逻辑处理
                String body = new String(msg.getBody(), StandardCharsets.UTF_8);
                log.info("Thread:{},Consumer-获取消息-主题topic为={}, 消费消息为={}",Thread.currentThread().getName(), msg.getTopic(), body);
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();
    }
}
