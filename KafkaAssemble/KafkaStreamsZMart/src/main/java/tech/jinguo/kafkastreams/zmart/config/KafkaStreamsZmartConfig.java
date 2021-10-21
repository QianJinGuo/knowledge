package tech.jinguo.kafkastreams.zmart.config;/*
 *
 * @Project: knowledge
 * @File: tech.jinguo.kafkastreams.boot.KafkaStreamsConfiguration
 * @Package: PACKAGE_NAME
 * @Date: 2021/10/15 15:20
 * @Author: by jinguo.qian
 *
 */

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import tech.jinguo.kafkastreams.zmart.clients.MockDataProducer;
import tech.jinguo.kafkastreams.zmart.model.Purchase;
import tech.jinguo.kafkastreams.zmart.model.PurchasePattern;
import tech.jinguo.kafkastreams.zmart.model.RewardAccumulator;
import tech.jinguo.kafkastreams.zmart.partitioner.RewardsStreamPartitioner;
import tech.jinguo.kafkastreams.zmart.service.SecurityDBService;
import tech.jinguo.kafkastreams.zmart.transformer.PurchaseRewardTransformer;
import tech.jinguo.kafkastreams.zmart.util.serde.StreamsSerdes;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jinguo.qian
 * @title: tech.jinguo.kafkastreams.boot.KafkaStreamsConfiguration
 * @projectName knowledge
 * @description: 装配Kafka stream
 * @date 2021/10/15 15:20
 */
@Configuration
@EnableKafkaStreams
public class KafkaStreamsZmartConfig {
    private KStream<String, Purchase> purchaseKStream;

    public KafkaStreamsZmartConfig() {
        MockDataProducer.producePurchaseData();
    }

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfigs() {
        Map<String, Object> props = new HashMap<>(2 << 3);
        props.put(StreamsConfig.CLIENT_ID_CONFIG, "Example-Kafka-Streams-Job");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "streams-purchases");
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "testing-streams-api");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(StreamsConfig.REPLICATION_FACTOR_CONFIG, 1);
        props.put(StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class);
        return new KafkaStreamsConfiguration(props);
    }

    @Bean
    public KStream<String, Purchase> kStream4Purchase(StreamsBuilder builder) {
        Serde<String> stringSerde = Serdes.String();
        Serde<Purchase> purchaseSerde = StreamsSerdes.PurchaseSerde();
        purchaseKStream = builder.stream("transactions", Consumed.with(stringSerde, purchaseSerde))
                .mapValues(p -> Purchase.builder(p).maskCreditCard().build());
        return purchaseKStream;
    }

    //@Bean
    public KStream<String, PurchasePattern> kStream4Pattern() {
        Serde<String> stringSerde = Serdes.String();
        Serde<PurchasePattern> purchasePatternSerde = StreamsSerdes.PurchasePatternSerde();
        KStream<String, PurchasePattern> patternKStream = purchaseKStream.mapValues(purchase -> PurchasePattern.builder(purchase).build());
        //patternKStream.print(Printed.<String, PurchasePattern>toSysOut().withLabel("patterns"));
        patternKStream.to("patterns", Produced.with(stringSerde, purchasePatternSerde));
        return patternKStream;
    }

    @Bean
    public KStream<String, RewardAccumulator> kStream4Rewards() {
        Serde<String> stringSerde = Serdes.String();
        Serde<RewardAccumulator> rewardAccumulatorSerde = StreamsSerdes.RewardAccumulatorSerde();
        KStream<String, RewardAccumulator> rewardsKStream = purchaseKStream.mapValues(purchase -> RewardAccumulator.builder(purchase).build());
        //rewardsKStream.print(Printed.<String, RewardAccumulator>toSysOut().withLabel("rewards"));
        rewardsKStream.to("rewards", Produced.with(stringSerde, rewardAccumulatorSerde));
        return rewardsKStream;
    }

    @Bean
    public KStream<Long, Purchase> kStream4FilterPurchase() {
        KeyValueMapper<String, Purchase, Long> purchaseDateAsKey = (key, purchase) -> purchase.getPurchaseDate().getTime();
        KStream<Long, Purchase> filteredKStream = purchaseKStream.filter((key, purchase) -> purchase.getPrice() > 5.00).selectKey(purchaseDateAsKey);
        //filteredKStream.print(Printed.<Long, Purchase>toSysOut().withLabel("purchases"));
        filteredKStream.to("purchases", Produced.with(Serdes.Long(), StreamsSerdes.PurchaseSerde()));
        return filteredKStream;
    }

    @Bean
    public KStream<String, Purchase>[] kStream4BranchPurchase() {
        Serde<String> stringSerde = Serdes.String();
        Serde<Purchase> purchaseSerde = StreamsSerdes.PurchaseSerde();

        Predicate<String, Purchase> isCoffee = (key, purchase) -> purchase.getDepartment().equalsIgnoreCase("coffee");
        Predicate<String, Purchase> isElectronics = (key, purchase) -> purchase.getDepartment().equalsIgnoreCase("electronics");

        int coffee = 0;
        int electronics = 1;
        KStream<String, Purchase>[] kstreamByDept = purchaseKStream.branch(isCoffee, isElectronics);
        kstreamByDept[coffee].to("coffee", Produced.with(stringSerde, purchaseSerde));
        //kstreamByDept[coffee].print(Printed.<String, Purchase>toSysOut().withLabel("coffee"));

        kstreamByDept[electronics].to("electronics", Produced.with(stringSerde, purchaseSerde));
        //kstreamByDept[electronics].print(Printed.<String, Purchase>toSysOut().withLabel("electronics"));
        return kstreamByDept;
    }

    @Bean
    public KStream<String, Purchase> kStream4ForEachPurchase() {
        // security Requirements to record transactions for certain employee
        ForeachAction<String, Purchase> purchaseForeachAction = (key, purchase) ->
                SecurityDBService.saveRecord(purchase.getPurchaseDate(), purchase.getEmployeeId(), purchase.getItemPurchased());
        purchaseKStream.filter((key, purchase) -> purchase.getEmployeeId().equals("000000")).foreach(purchaseForeachAction);
        return purchaseKStream;
    }

    @Bean
    public KStream<String, Purchase> kStream4PeekPurchase() {
        ForeachAction<String, Purchase> purchaseForeachAction = (key, purchase) ->
                SecurityDBService.saveRecord(purchase.getPurchaseDate(), purchase.getEmployeeId(), purchase.getItemPurchased());
        purchaseKStream.filter((key, purchase) -> purchase.getEmployeeId().equals("000000")).peek(purchaseForeachAction);
        return purchaseKStream;
    }

    @Bean
    public KStream<String, RewardAccumulator> kStream4RewardsStateStore(StreamsBuilder builder) {
        Serde<RewardAccumulator> rewardAccumulatorSerde = StreamsSerdes.RewardAccumulatorSerde();
        Serde<Purchase> purchaseSerde = StreamsSerdes.PurchaseSerde();
        Serde<String> stringSerde = Serdes.String();
        // adding State to processor
        String rewardsStateStoreName = "rewardsPointsStore";
        //分区策略
        RewardsStreamPartitioner streamPartitioner = new RewardsStreamPartitioner();
        //转换器类
        KeyValueBytesStoreSupplier storeSupplier = Stores.inMemoryKeyValueStore(rewardsStateStoreName);
        StoreBuilder<KeyValueStore<String, Integer>> storeBuilder = Stores.keyValueStoreBuilder(storeSupplier, Serdes.String(), Serdes.Integer());
        builder.addStateStore(storeBuilder);
        //使用KStream.through()方法实现重新分区,当前的KStream实例开始将记录写入这个主题中。
        // 调用through()方法返回一个新KStream实例，该实例使用同一个中间主题作为其数据源。通过这种方式，数据就可以被无缝地重新分区。
        //topic需要预先创建，bin\windows\kafka-topics.bat --create --zookeeper localhost:2181,localhost:2182,localhost:2183 --replication-factor 1 --partitions 1 --topic customerTransactions
        KStream<String, Purchase> transByCustomerStream = purchaseKStream.through("customerTransactions", Produced.with(stringSerde, purchaseSerde, streamPartitioner));
        transByCustomerStream.print(Printed.<String, Purchase>toSysOut().withLabel("transByCustomerStream"));
        //值转换器，和mapValues区别是 ，这两种方法操作方式相同，即仍然是将Purchase对象映射为RewardAccumulator对象。不同之处在于使用本地状态来执行转换的能力。
        KStream<String, RewardAccumulator> statefulRewardAccumulator = transByCustomerStream.transformValues(() -> new PurchaseRewardTransformer(rewardsStateStoreName),
                rewardsStateStoreName);
        statefulRewardAccumulator.print(Printed.<String, RewardAccumulator>toSysOut().withLabel("rewards"));
        statefulRewardAccumulator.to("rewards", Produced.with(stringSerde, rewardAccumulatorSerde));
        return statefulRewardAccumulator;
    }
}
    