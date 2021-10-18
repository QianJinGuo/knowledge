/*
 * Copyright 2016 Bill Bejeck
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tech.jinguo.kafkastreams.zmart;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jinguo.kafkastreams.zmart.clients.MockDataProducer;

import java.util.Properties;

public class KafkaStreamsYellingApp {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaStreamsYellingApp.class);

    public static void main(String[] args) throws Exception {


        //Used only to produce data for this application, not typical usage
        MockDataProducer.produceRandomTextData();

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "yelling_app_id");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");

        StreamsConfig streamsConfig = new StreamsConfig(props);

        Serde<String> stringSerde = Serdes.String();

        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, String> simpleFirstStream = builder.stream("src-topic", Consumed.with(stringSerde, stringSerde));


        KStream<String, String> upperCasedStream = simpleFirstStream.mapValues((ValueMapper<String, String>) String::toUpperCase);

        upperCasedStream.to("out-topic", Produced.with(stringSerde, stringSerde));
        upperCasedStream.print(Printed.<String, String>toSysOut().withLabel("Yelling App"));


        KafkaStreams kafkaStreams = new KafkaStreams(builder.build(), streamsConfig);
        LOG.info("Hello World Yelling App Started");
        kafkaStreams.start();
        Thread.sleep(3500000);
        LOG.info("Shutting down the Yelling APP now");
        kafkaStreams.close();
        MockDataProducer.shutdown();

    }
}