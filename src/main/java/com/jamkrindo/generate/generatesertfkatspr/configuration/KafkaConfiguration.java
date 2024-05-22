package com.jamkrindo.generate.generatesertfkatspr.configuration;


import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {
    @Value(value = "172.27.1.67:9092")
    private String bootstrapAddress;
//
    @Bean
    public ProducerFactory<String,Object> producerFactory(){

        Map<String,Object> configKafka = new HashMap<>();
        configKafka.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"172.27.1.67:9092");
        configKafka.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configKafka.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configKafka.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, "20971520");

        return new DefaultKafkaProducerFactory<>(configKafka);

    }

    @Bean
    public KafkaTemplate<String,Object> kafkaTemplate(){

        return new KafkaTemplate<String,Object>(producerFactory());

    }

}
