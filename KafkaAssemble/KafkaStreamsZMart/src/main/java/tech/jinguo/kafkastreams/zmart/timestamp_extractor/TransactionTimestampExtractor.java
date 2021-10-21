package tech.jinguo.kafkastreams.zmart.timestamp_extractor;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;
import tech.jinguo.kafkastreams.zmart.model.Purchase;

/**
 * 自定义时间戳器
 */
public class TransactionTimestampExtractor implements TimestampExtractor {

    @Override
    public long extract(ConsumerRecord<Object, Object> record, long previousTimestamp) {
        Purchase purchasePurchaseTransaction = (Purchase) record.value();
        return purchasePurchaseTransaction.getPurchaseDate().getTime();
    }
}
