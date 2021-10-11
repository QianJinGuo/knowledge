package tech.jinguo.kafka.demo2;

import org.apache.kafka.clients.producer.*;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Future;

/**
 * Example of a simple producer, not meant to run as a stand alone example.
 *
 * If desired to run this example change the ProducerRecord below to
 * use a real topic name and comment out line below.
 */
public class SimpleProducer {
    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        //properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("key.serializer", "tech.jinguo.kafka.demo2.KafkaMsgSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("acks", "1");
        properties.put("retries", "3");
        properties.put("compression.type", "snappy");
        //This line in for demonstration purposes
        properties.put("partitioner.class", PurchaseKeyPartitioner.class.getName());

        /**
         * Kafka传输自定义的DTO对象时，不能像平时一样使用StringSerializer和StringDeserializer。
         * 这种情况需要自己实现对应DTO的序列化器和反序列化器
         */
        PurchaseKey key = new PurchaseKey("123456", new Date());

        try(Producer<PurchaseKey, String> producer = new KafkaProducer<>(properties)) {
            ProducerRecord<PurchaseKey, String> record = new ProducerRecord<>("second-topic", key, "value");

            Callback callback = (metadata, exception) -> {
                if (exception != null) {
                    exception.printStackTrace();
                }
            };

            Future<RecordMetadata> sendFuture = producer.send(record, callback);
        }

    }


}
