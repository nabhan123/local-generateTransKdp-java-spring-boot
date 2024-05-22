package com.jamkrindo.generate.generatesertfkatspr.services.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

public class TestKafkaConsumer {

    private final Logger LOG = LoggerFactory.getLogger(TestKafkaConsumer.class);


//    @KafkaListener(topics = "test-topic-kdp", groupId = "test-group-id", containerFactory = "concurrentKafkaListenerContainerFactory")
    public void testConsume(String message, String topic){
        LOG.info(String.format("Message received -> %s", message,topic));

    }
}
