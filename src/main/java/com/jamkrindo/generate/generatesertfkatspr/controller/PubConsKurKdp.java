package com.jamkrindo.generate.generatesertfkatspr.controller;


import com.jamkrindo.generate.generatesertfkatspr.constant.ResponseConstant;
import com.jamkrindo.generate.generatesertfkatspr.daos.consumer.GenerateTransNew;
import com.jamkrindo.generate.generatesertfkatspr.daos.producer.PengajuanKlaimKurDaos;
import com.jamkrindo.generate.generatesertfkatspr.dtos.PengajuanKlaimDto;
import com.jamkrindo.generate.generatesertfkatspr.request.GenerateTransaksiRequest;
import com.jamkrindo.generate.generatesertfkatspr.response.Response;
import com.jamkrindo.generate.generatesertfkatspr.services.kafka.ProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/kur/v1")
public class PubConsKurKdp {

    @Autowired
    ProducerService producerService;

//    inject
    @Autowired
    GenerateTransNew generateTransNew;

    private final Logger LOG = LoggerFactory.getLogger(PubConsKurKdp.class);

    @GetMapping("/fn-kur-kdp")
    public ResponseEntity<Object> fndDebturKlaim()throws Exception{
        PengajuanKlaimKurDaos pengajuanKlaimKurDaos = new PengajuanKlaimKurDaos();
        List<Map<String,Object>> response = new ArrayList<>();
        List<PengajuanKlaimDto> listData = pengajuanKlaimKurDaos.findDebiturKlaimtoSync();
        try{
            for (PengajuanKlaimDto reqBody : listData){
                Map<String,Object> tamp = new HashMap<>();
                tamp.put("idPengajuanKlam", reqBody.getId_pengajuan_klaim());
                tamp.put("idOpmtSertifikat", reqBody.getId_opmt_sertifikat());
                tamp.put("idTransaksi", reqBody.getId_opmt_transaksi_penjaminan());
                response.add(tamp);
            }
        }catch (Exception e){
            producerService.publishMessage(Response.failedResponse(true,e.getMessage(),01,response),"/generate-kur-kdp","generate-kur-kdp");
            return ResponseEntity.status(400).body(Response.failedResponse(true,e.getMessage(),01,null));
        }
        producerService.publishMessage(Response.successResponse(true,ResponseConstant.SUCCESS_SYNC,00),"/generate-kur-kdp","generate-kur-kdp");
        return ResponseEntity.ok(Response.successResponse(true,ResponseConstant.SUCCESS_SYNC,00));
    }

    @KafkaListener(topics = "generate-kur-kdp",groupId = "groupId",containerFactory = "concurrentKafkaListenerContainerFactory")
    public void testConsumePub(Map<String,Object> param)throws Exception{
        Map<String,Object> reqPayload = (Map<String, Object>) param.get("requestPayload");
        LOG.info("masuk", reqPayload);
        try{
            generateTransNew.insertToTransaction();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
