package tech.jinguo.rocketmqdemo;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Producer implements InitializingBean, DisposableBean {
    @Value("${rocketmq.nameserv}")
    private String nameServ;
    @Value("${rocketmq.producer.group}")
    private String produceGroup;

    private DefaultMQProducer producer;

    @Override
    public void afterPropertiesSet() throws Exception {
        producer = new DefaultMQProducer(produceGroup);
        //不开启vip通道 开通口端口会减2
        producer.setVipChannelEnabled(false);
        //绑定name server
        producer.setNamesrvAddr(nameServ);
        producer.start();
    }

    public DefaultMQProducer getProducer(){
        if(producer == null){
            producer = new DefaultMQProducer(produceGroup);
        }
        return this.producer;
    }

    @Override
    public void destroy() throws Exception {
        producer.shutdown();
    }
}
