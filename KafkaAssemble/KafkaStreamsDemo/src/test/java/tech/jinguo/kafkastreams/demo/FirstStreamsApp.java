/*
 * Copyright: 2021 jinguo.tech All rights reserved.
 * 注意：本内容仅限于jinguo.tech内部传阅，禁止外泄以及用于其他的商业目的
 *
 * @Project: knowledge
 * @File: FirstStreamsApp
 * @Package: tech.jinguo.kafkastreams.demo
 * @Date: 2021/10/14 11:01
 * @Author: by jinguo.qian
 *
 */
package tech.jinguo.kafkastreams.demo;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

/**
 * @author jinguo.qian
 * @title: FirstStreamsApp
 * @projectName knowledge
 * @description: Streams Demo
 * @date 2021/10/14 11:01
 */
public class FirstStreamsApp {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-app-id");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        final StreamsBuilder builder = new StreamsBuilder();
        builder.stream("streams-input-topic").to("streams-app-output");
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
    