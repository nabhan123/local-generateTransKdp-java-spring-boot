package com.jamkrindo.generate.generatesertfkatspr.services.kafka;

import com.jamkrindo.generate.generatesertfkatspr.services.transaksi.GenerateTransaksiPenKdp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;


@Service
public class ConsumerServicePen {

    @Autowired
    GenerateTransaksiPenKdp generateTransaksiPenKdp;

    private final Logger LOG = LoggerFactory.getLogger(ConsumerServicePen.class);

    final AtomicInteger counter = new AtomicInteger();

//    @KafkaListener(groupId = "kdp", topics = "test-pop", containerFactory = "concurrentKafkaListenerContainerFactory")
//    public void consumeFromTopic(GenerateTransaksiRequest request) throws Exception{
//
//        counter.getAndIncrement();
//        try {
//            generateTransaksiPenKdp.pubTransactionKdpPen(request);
//        }catch (Exception e){
//            LOG.error("Error Processing Message : " + e.getMessage());
//        }
//
//    }

    public AtomicInteger getCounter() {
        return counter;
    }
}
