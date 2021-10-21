/*
 *
 * @Project: knowledge
 * @File: KTableZmartConfig
 * @Package: tech.jinguo.kafkastreams.zmart.config
 * @Date: 2021/10/21 13:43
 * @Author: by jinguo.qian
 *
 */
package tech.jinguo.kafkastreams.zmart.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import tech.jinguo.kafkastreams.zmart.clients.MockDataProducer;
import tech.jinguo.kafkastreams.zmart.model.StockTickerData;
import tech.jinguo.kafkastreams.zmart.util.serde.StreamsSerdes;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jinguo.qian
 * @title: KTableZmartConfig
 * @projectName knowledge
 * @description: 流和表
 * @date 2021/10/21 13:43
 */
//@Configuration
//@EnableKafkaStreams
public class KTableZmartConfig {
    public KTableZmartConfig() {
        int numberCompanies = 3;
        int iterations = 3;
        MockDataProducer.produceStockTickerData(numberCompanies, iterations);
    }

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfigs() {
        Map<String, Object> props = new HashMap<>(2 << 3);
        props.put(StreamsConfig.CLIENT_ID_CONFIG, "KTable_client");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "KTable_group");
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "KTable_app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        //当处理器的状态被保存（提交）时，它会强制刷新缓存，将最新更新、已删除重复更新的记录发送到下游
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "30000");
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, "15000");
        //关闭缓存
        //props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG,"0");
        //内存大小将被线程平均分配
        props.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, "1");
        props.put(ConsumerConfig.METADATA_MAX_AGE_CONFIG, "10000");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, StreamsSerdes.StockTickerSerde().getClass().getName());
        props.put(StreamsConfig.REPLICATION_FACTOR_CONFIG, 1);
        props.put(StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class);
        return new KafkaStreamsConfiguration(props);
    }

    /**
     * 流和表对比
     * @param builder
     * @return
     */
    @Bean
    public KTable<String, StockTickerData> kStreamVSKTable(StreamsBuilder builder) {
        KTable<String, StockTickerData> stockTickerTable = builder.table(MockDataProducer.STOCK_TICKER_TABLE_TOPIC,Materialized.as("stock"));
        KStream<String, StockTickerData> stockTickerStream = builder.stream(MockDataProducer.STOCK_TICKER_STREAM_TOPIC);
        stockTickerTable.toStream().print(Printed.<String, StockTickerData>toSysOut().withLabel("Stocks-KTable"));
        stockTickerStream.print(Printed.<String, StockTickerData>toSysOut().withLabel("Stocks-KStream"));
        return stockTickerTable;
    }
}
    