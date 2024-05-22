package com.jamkrindo.generate.generatesertfkatspr.controller.testing;

import com.jamkrindo.generate.generatesertfkatspr.services.kafka.ProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.time.YearMonth;


@RestController
@RequestMapping("api/v1")
public class TestSendMsg {


    @Autowired
    ProducerService producerService;

    private final Logger LOG = LoggerFactory.getLogger(TestSendMsg.class);

    @GetMapping("/publish")
    public ResponseEntity<String> publish(@RequestParam("message") String message, String topic){
        producerService.testPublish(topic,message);
        return ResponseEntity.ok("Message sent to kafka topic");
    }


    @PostMapping("kafka-test")
    public ResponseEntity<String> pubTest(@RequestParam("message") String message,String topic){
        producerService.testPublish(topic,message);
//        get all days of a month


        return ResponseEntity.ok("Publisher message was sent");
    }

//    consumer
//    @KafkaListener(topics = "test-topic-kdp", groupId = "test-group-id", containerFactory = "concurrentKafkaListenerContainerFactory")
    public void testConsume(String message){

        LOG.info("Pesannya adalah :" + message);

    }


//    get all day of this month
    @PostMapping("getDate")
    public void getDate(){
        YearMonth may = YearMonth.of(2024, Month.MAY);
        may.atDay(1)
                .datesUntil(may.atEndOfMonth())
                .forEach(System.out::println);
    }



}
