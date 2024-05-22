package com.jamkrindo.generate.generatesertfkatspr.controller;


import com.jamkrindo.generate.generatesertfkatspr.connection.DbKupedes;
import com.jamkrindo.generate.generatesertfkatspr.constant.ResponseConstant;
import com.jamkrindo.generate.generatesertfkatspr.daos.producer.PengajuanKlaimKupedesDaos;
import com.jamkrindo.generate.generatesertfkatspr.dtos.KlaimKupedesDto;
import com.jamkrindo.generate.generatesertfkatspr.request.GenerateTransaksiRequest;
import com.jamkrindo.generate.generatesertfkatspr.response.Response;
import com.jamkrindo.generate.generatesertfkatspr.services.kafka.ProducerService;
import com.jamkrindo.generate.generatesertfkatspr.services.transaksi.GenerateTransaksiKupedesKdp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/kupedes")
public class PubConsKupedesKdp {

    @Autowired
    ProducerService producerService;

    @Autowired
    GenerateTransaksiKupedesKdp generateTransaksiKupedesKdp;


    final Logger LOG = LoggerFactory.getLogger(PubConsKupedesKdp.class);


    @PostMapping("generate-kdp-kupedes")
    public ResponseEntity<Object> findDebiturKupedesKdp(@RequestBody GenerateTransaksiRequest request)throws Exception{
        DbKupedes dbKupedes = new DbKupedes();
        Connection connection = dbKupedes.getConnection();
        List<Map<String,Object>> response = new ArrayList<>();

        try{

            PengajuanKlaimKupedesDaos pengajuanKlaimKupedesDaos = new PengajuanKlaimKupedesDaos();
            List<KlaimKupedesDto> listData = pengajuanKlaimKupedesDaos.fnDebiturKupedesToSync(request);
            LOG.info("lisData" + listData);
            if (listData.size() == 0){
                throw new Exception(ResponseConstant.DATA_NOT_FOUND);
            }
            for (KlaimKupedesDto reqBody : listData){
                Map<String,Object> tamp = new HashMap<>();
                tamp.put("idPengajuanKlaim", reqBody.getId_pengajuan_klaim());
                tamp.put("idOpmtSertifikat", reqBody.getId_opmt_sertifikat());
                tamp.put("idTransaksi", reqBody.getId_opmt_transaksi_penjaminan());
                response.add(tamp);
            }
            LOG.info("response" + response);
        }catch (Exception e){
            producerService.publishMessage(Response.failedResponse(true,e.getMessage(),01,response),"/kupedes-kdp","testKafkaPubKupedes");
            return ResponseEntity.status(400).body(Response.failedResponse(true,e.getMessage(),01,response));
        }
        producerService.publishMessage(Response.successResponse(true,ResponseConstant.SUCCESS_SYNC,00),"/kupedes-kdp","testKafkaPubKupedes");
        return ResponseEntity.ok(Response.successResponse(true,ResponseConstant.SUCCESS_SYNC,00));
    }

    @KafkaListener(topics = "testKafkaPubKupedes", groupId = "penjaminan_online", containerFactory = "concurrentKafkaListenerContainerFactory")
    public void consumePubKupedes(Map<String,Object> param)throws Exception{
        Map<String,Object> listData = (Map<String, Object>) param.get("requestPayload");
        LOG.info("param" + listData);
        try{
            GenerateTransaksiRequest request = new GenerateTransaksiRequest();
            request.setId_dd_bank(Integer.valueOf(listData.get("id_dd_bank").toString()));
            generateTransaksiKupedesKdp.insertTransKupedesKdp(request);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
