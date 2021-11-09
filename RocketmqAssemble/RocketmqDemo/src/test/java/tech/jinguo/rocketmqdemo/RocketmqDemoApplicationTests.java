package tech.jinguo.rocketmqdemo;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
class RocketmqDemoApplicationTests {
    @Value("${rocketmq.topic}")
    private String topic;
    @Autowired
    private Producer producer;
    @Test
    void contextLoads() {
    }

    @Test
    public void testRocketMQ(){
        List<String> msgList = new ArrayList<>();
        msgList.add("zhiwei");
        msgList.add("xiaoxiong");
        msgList.add("jinguo");
        //总共发送五次消息
        for (String s : msgList) {
            //创建生产信息
            Message message = new Message(topic, "rocketmq_demo_tag", ("msg:" + s).getBytes());
            try {
                producer.getProducer().send(message);
            } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
                log.error(e.getMessage());
            }
        }
    }
}
