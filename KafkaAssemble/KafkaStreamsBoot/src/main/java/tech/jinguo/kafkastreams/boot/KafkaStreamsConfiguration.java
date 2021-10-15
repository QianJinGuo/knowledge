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
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;

import java.util.Arrays;
import java.util.Locale;

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
    /**
     * 将消息内容转小写
     *
     * @param streamsBuilder
     * @return
     */
    //@Bean
    public KStream<String, String> kStream4Low(StreamsBuilder streamsBuilder) {
        KStream<String, String> stream = streamsBuilder.stream("streams-input-topic");
        stream.mapValues((ValueMapper<String, String>) String::toLowerCase).to("streams-app-output", Produced.with(Serdes.String(), Serdes.String()));
        return stream;
    }

    /**
     * 计数,相同的message,count+1
     *
     * @param streamsBuilder
     * @return
     */
    @Bean
    public KStream<String, String> kStream4Count(StreamsBuilder streamsBuilder) {
        KStream<String, String> stream = streamsBuilder.stream("streams-input-topic");
        stream.flatMapValues(textLine ->
                //把数据按空格拆分成单个单词
                Arrays.asList(textLine.toLowerCase(Locale.getDefault()).split("\\W+"))
        );
        //过滤掉the这个单词，不统计这个单词
        stream.filter((key, value) -> (!"the".equals(value)));
        //打印
        stream.print(Printed.<String, String>toSysOut().withLabel("Streams-Boot-App"));
        //分组
        stream.groupBy((key, value) -> value)
                // //计数，其中'countsStore'是状态存储的名字
                .count(Materialized.as("countsStore"))
                .toStream()
                .map((key, value) -> KeyValue.pair(key, value + ""))
                //将stream写回到Kafka的topic
                .to("streams-app-output");
        return stream;
    }


}
    