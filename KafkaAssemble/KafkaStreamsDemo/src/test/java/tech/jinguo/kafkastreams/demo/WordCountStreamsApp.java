/*
 * Copyright: 2021 jinguo.tech All rights reserved.
 * 注意：本内容仅限于车巴达公司内部传阅，禁止外泄以及用于其他的商业目的
 *
 * @Project: knowledge
 * @File: WordCountStreamsApp
 * @Package: tech.jinguo.kafkastreams.demo
 * @Date: 2021/10/14 14:54
 * @Author: by jinguo.qian
 *
 */
package tech.jinguo.kafkastreams.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

/**
 * @author jinguo.qian
 * @title: WordCountStreamsApp
 * @projectName knowledge
 * @description: 计数demo
 * @date 2021/10/14 14:54
 */
@Slf4j
public class WordCountStreamsApp {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-app-id-3");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        final StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> source = builder.stream("streams-input-topic");
        source.flatMapValues(textLine ->
                Arrays.asList(textLine.toLowerCase(Locale.getDefault()).split("\\W+"))
        ).groupBy((key, value) -> value)
                .count(Materialized.as("countsStore"))
                .toStream()
                .map((key, value) -> KeyValue.pair(key, value + ""))
                .to("streams-app-output");
        //.to("streams-app-output", Produced.with(Serdes.String(), Serdes.Long()));

        final Topology topology = builder.build();
        final KafkaStreams streams = new KafkaStreams(topology, props);
        final CountDownLatch latch = new CountDownLatch(1);
        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
            @Override
            public void run() {
                streams.close();
                latch.countDown();
            }
        });
        try {
            streams.start();
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }
        System.exit(0);
    }
}
    