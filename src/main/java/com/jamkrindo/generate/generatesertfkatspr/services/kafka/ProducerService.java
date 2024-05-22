package com.jamkrindo.generate.generatesertfkatspr.services.kafka;


import com.jamkrindo.generate.generatesertfkatspr.request.GenerateTransaksiRequest;
import com.jamkrindo.generate.generatesertfkatspr.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class ProducerService {

    @Autowired
    KafkaTemplate kafkaTemplate;

    @Autowired
    DateTimeUtils dateTimeUtils;

    public void publishMessage(Map<String,Object> response, String path, String topic) {

        Map<String,Object> par = new HashMap<>();
        System.out.println("===publish to topic====" + topic+"path=>"+path);
//        par.put("idDdBank", kafkaReq.getId_dd_bank());
//        par.put("requestPayload", request);
        par.put("responsePayload", response);
        par.put("topic", topic);
        par.put("path", path);
        this.kafkaTemplate.send(topic,par);

    }

    public void publishActivityMessage(Map<String,Object> response, Object request, String path, String topic, Date requestTime, Date responseTime){
        Map<String,Object> tamp = new LinkedHashMap<>();
        tamp.put("request" , request);
        tamp.put("path",path);
        tamp.put("topic",topic);
        tamp.put("response",response);
        tamp.put("requestTime",requestTime);
        tamp.put("responseTime",responseTime);
        this.kafkaTemplate.send(topic,tamp);

    }

    public void publishGenerateTransaksi(GenerateTransaksiRequest request, String topic, String message, Map<String,Object> response) {
        Map<String,Object> tamp = new LinkedHashMap<>();
        tamp.put("requestPayload",request);
        tamp.put("responsePayload", response);
//        tamp.put("apiProxy","service/consumer-klaim");
        tamp.put("message",message);
        tamp.put("topic",topic);
        this.kafkaTemplate.send(topic, tamp);
    }

    public void testPublish(String message,String topic){
        this.kafkaTemplate.send(topic,message);

    }

}
