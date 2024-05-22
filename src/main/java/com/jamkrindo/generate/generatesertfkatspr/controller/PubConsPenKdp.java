package com.jamkrindo.generate.generatesertfkatspr.controller;


import com.jamkrindo.generate.generatesertfkatspr.constant.ResponseConstant;
import com.jamkrindo.generate.generatesertfkatspr.response.Response;
import com.jamkrindo.generate.generatesertfkatspr.services.kafka.ProducerService;
import com.jamkrindo.generate.generatesertfkatspr.services.transaksi.GenerateTransaksiPenKdp;
import com.jamkrindo.generate.generatesertfkatspr.daos.producer.PengajuanKlaimPenDaos;
import com.jamkrindo.generate.generatesertfkatspr.dtos.KlaimPenDto;
import com.jamkrindo.generate.generatesertfkatspr.request.GenerateTransaksiRequest;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/pen/v1")
public class PubConsPenKdp {


    @Autowired
    GenerateTransaksiPenKdp generateTransaksiPenKdp;

    @Autowired
    ProducerService producerService;
    final Logger LOG = LoggerFactory.getLogger(PubConsPenKdp.class);


    @GetMapping("fndPenKdp")
    public ResponseEntity<Object> fndDebPenKdpToReadySync()throws Exception{

        LOG.info("========START TO FIND DATA KLAIM PEN=============");
        List<Map<String,Object>> response = new ArrayList<>();

        try{

            PengajuanKlaimPenDaos pengajuanKlaimPenDaos = new PengajuanKlaimPenDaos();
            List<KlaimPenDto> listData = pengajuanKlaimPenDaos.fnKdpPen();
            LOG.info("listData" + listData);
            for (KlaimPenDto reqBody : listData){
                Map<String,Object> tamp = new HashMap<>();
                tamp.put("idPengajuanKlaim", reqBody.getId_pengajuan_klaim());
                tamp.put("idOpmtSertifikat", reqBody.getId_opmt_sertifikat());
                response.add(tamp);
            }
        }catch (Exception e){
            producerService.publishMessage(Response.failedResponse(true, e.getMessage(),01,response),"/testKdpPenKafka","testKafkaPubPen");
            return ResponseEntity.status(400).body(Response.failedResponse(true,e.getMessage(),01,response));
        }
        producerService.publishMessage(Response.successResponse(true,ResponseConstant.SUCCESS_SYNC,00),"/testKdpPenKafka","testKafkaPubPen");
        return ResponseEntity.ok(Response.successResponse(true,ResponseConstant.SUCCESS_SYNC,00));
    }

    @KafkaListener(topics = "testKafkaPubPen", groupId = "groupId", containerFactory = "concurrentKafkaListenerContainerFactory")
    public void consumePubPenKdp(ConsumerRecord<String,Object> param)throws Exception{
        LOG.info("param" + param.value().toString());
//        Map<String,Object> listData = (Map<String, Object>) param.get("responsePayload");
        try{
            generateTransaksiPenKdp.insertToTabelTransPen();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
