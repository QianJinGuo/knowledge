package tech.jinguo.kafkastreams.boot;/*
 *
 * @Project: knowledge
 * @File: tech.jinguo.kafkastreams.boot.KafkaStreamsConfiguration
 * @Package: PACKAGE_NAME
 * @Date: 2021/10/15 15:20
 * @Author: by jinguo.qian
 *
 */

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;

/**
 * @author jinguo.qian
 * @title: tech.jinguo.kafkastreams.boot.KafkaStreamsConfiguration
 * @projectName knowledge
 * @description: 装配Kafka stream
 * @date 2021/10/15 15:20
 */
@Configuration
@EnableKafkaStreams
public class KafkaStreamsConfiguration {
    @Bean
    public KStream<String, String> kStream(StreamsBuilder streamsBuilder) {
        KStream<String, String> stream = streamsBuilder.stream("streams-input-topic");
        stream.mapValues((ValueMapper<String, String>) String::toLowerCase).to("streams-app-output", Produced.with(Serdes.String(), Serdes.String()));
        return stream;
    }
}
    