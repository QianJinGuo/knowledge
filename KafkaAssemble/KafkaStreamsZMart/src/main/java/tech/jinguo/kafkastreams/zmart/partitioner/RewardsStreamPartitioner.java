package tech.jinguo.kafkastreams.zmart.partitioner;

import org.apache.kafka.streams.processor.StreamPartitioner;
import tech.jinguo.kafkastreams.zmart.model.Purchase;


public class RewardsStreamPartitioner implements StreamPartitioner<String, Purchase> {
    @Override
    public Integer partition(String s, String s2, Purchase purchase, int numPartitions) {
        //将相同客户ID的交易信息放在同一个分区很重要，因为需要根据客户ID从状态存储中查找记录
        //否则，会有相同客户ID的客户信息分布在不同的分区上，这样查找相同客户的信息时就需要从多个状态存储中查询。
        // （这句话可理解为每个分区都有自己的状态存储，但事实并非如此。分区被分配给一个StreamTask，每个StreamTask都有自己的状态存储。
        return purchase.getCustomerId().hashCode() % numPartitions;
    }
}
