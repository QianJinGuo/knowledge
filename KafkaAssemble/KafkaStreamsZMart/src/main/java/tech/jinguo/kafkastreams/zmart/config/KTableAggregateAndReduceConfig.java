/*
 *
 * @Project: knowledge
 * @File: KTableAggregationsAndReducingConfig
 * @Package: tech.jinguo.kafkastreams.zmart.config
 * @Date: 2021/10/21 17:17
 * @Author: by jinguo.qian
 *
 */
package tech.jinguo.kafkastreams.zmart.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import tech.jinguo.kafkastreams.zmart.clients.MockDataProducer;
import tech.jinguo.kafkastreams.zmart.model.ShareVolume;
import tech.jinguo.kafkastreams.zmart.model.StockTransaction;
import tech.jinguo.kafkastreams.zmart.util.collectors.FixedSizePriorityQueue;
import tech.jinguo.kafkastreams.zmart.util.serde.StreamsSerdes;

import java.text.NumberFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.apache.kafka.streams.Topology.AutoOffsetReset.EARLIEST;

/**
 * @author jinguo.qian
 * @title: KTableAggregationsAndReducingConfig
 * @projectName knowledge
 * @description: 聚合及规约
 * @date 2021/10/21 17:17
 */
@Configuration
@EnableKafkaStreams
@Slf4j
public class KTableAggregateAndReduceConfig {

    public KTableAggregateAndReduceConfig() {
        MockDataProducer.produceStockTransactions(15, 50, 25, false);
    }

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfigs() {
        Map<String, Object> props = new HashMap<>(2 << 3);
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "KTable-aggregations");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "KTable-aggregations-id");
        props.put(StreamsConfig.CLIENT_ID_CONFIG, "KTable-aggregations-client");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        //当处理器的状态被保存（提交）时，它会强制刷新缓存，将最新更新、已删除重复更新的记录发送到下游
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "30000");
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, "15000");
        //内存大小将被线程平均分配
        props.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, "1");
        props.put(ConsumerConfig.METADATA_MAX_AGE_CONFIG, "10000");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.REPLICATION_FACTOR_CONFIG, 1);
        props.put(StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class);
        return new KafkaStreamsConfiguration(props);
    }

    /**
     * @Description: 聚合及规约
     * @Param: [builder]
     * @Return: org.apache.kafka.streams.kstream.KTable<java.lang.String, tech.jinguo.kafkastreams.zmart.model.ShareVolume>
     * @Author: jinguo.qian
     * @Date: 2021/10/21 17:07
     */
    @Bean
    public KTable<String, ShareVolume> kStreamAggregationsAndReducing(StreamsBuilder builder) {
        Serde<String> stringSerde = Serdes.String();
        Serde<StockTransaction> stockTransactionSerde = StreamsSerdes.StockTransactionSerde();
        Serde<ShareVolume> shareVolumeSerde = StreamsSerdes.ShareVolumeSerde();
        Serde<FixedSizePriorityQueue> fixedSizePriorityQueueSerde = StreamsSerdes.FixedSizePriorityQueueSerde();
        NumberFormat numberFormat = NumberFormat.getInstance();

        Comparator<ShareVolume> comparator = (sv1, sv2) -> sv2.getShares() - sv1.getShares();
        FixedSizePriorityQueue<ShareVolume> fixedQueue = new FixedSizePriorityQueue<>(comparator, 5);

        ValueMapper<FixedSizePriorityQueue, String> valueMapper = fpq -> {
            StringBuilder stringBuilder = new StringBuilder();
            Iterator<ShareVolume> iterator = fpq.iterator();
            int counter = 1;
            while (iterator.hasNext()) {
                ShareVolume stockVolume = iterator.next();
                if (stockVolume != null) {
                    stringBuilder.append(counter++).append(")").append(stockVolume.getSymbol())
                            .append(":").append(numberFormat.format(stockVolume.getShares())).append(" ");
                }
            }
            return stringBuilder.toString();
        };

        KTable<String, ShareVolume> shareVolume = builder.stream(MockDataProducer.STOCK_TRANSACTIONS_TOPIC,
                Consumed.with(stringSerde, stockTransactionSerde)
                        .withOffsetResetPolicy(EARLIEST))
                .mapValues(st -> ShareVolume.newBuilder(st).build())
                .groupBy((k, v) -> v.getSymbol(), Serialized.with(stringSerde, shareVolumeSerde))
                .reduce(ShareVolume::sum);

        shareVolume.groupBy((k, v) -> KeyValue.pair(v.getIndustry(), v), Serialized.with(stringSerde, shareVolumeSerde))
                .aggregate(() -> fixedQueue,
                        (k, v, agg) -> agg.add(v),
                        (k, v, agg) -> agg.remove(v),
                        Materialized.with(stringSerde, fixedSizePriorityQueueSerde))
                .mapValues(valueMapper)
                .toStream().peek((k, v) -> log.info("Stock volume by industry {} {}", k, v))
                .to("stock-volume-by-company", Produced.with(stringSerde, stringSerde));
        return shareVolume;
    }
}
    