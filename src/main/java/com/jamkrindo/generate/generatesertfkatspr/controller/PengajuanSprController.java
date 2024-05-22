package com.jamkrindo.generate.generatesertfkatspr.controller;


import com.jamkrindo.generate.generatesertfkatspr.connection.DbCoreBri;
import com.jamkrindo.generate.generatesertfkatspr.daos.PengajuanSprDaos;
import com.jamkrindo.generate.generatesertfkatspr.dtos.PengajuanSprDtos;
import com.jamkrindo.generate.generatesertfkatspr.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1")
public class PengajuanSprController {

    private static Logger log = LoggerFactory.getLogger(PengajuanSprController.class);

    @PostMapping("getPengajuanSpr")
    public ResponseEntity<Object> getDataSpr(@RequestBody @Validated PengajuanSprDtos request) throws Exception{
    //        buat tampungan list key value
        try{
            DbCoreBri dbCoreBri = new DbCoreBri();
    //        panggil daos sebagai servicenya
            Map<String, Object> response = new HashMap<>();

            log.info("log db : " + dbCoreBri);
            PengajuanSprDtos parDto = new PengajuanSprDtos();
            parDto.setId_dd_bank(request.getId_dd_bank());
            parDto.setJ_flag_persetujuan_spr(request.getJ_flag_persetujuan_spr());

    //        cari no_rekening yang mau di sync (pastikan belum ada transaksi)
            List<PengajuanSprDtos> data = PengajuanSprDaos.findAllDebiturSpr(dbCoreBri,parDto);
            List<Map<String,Object>> res =new ArrayList<>();
            for (PengajuanSprDtos pengajuanSprDtos: data){

                response.put("noRekeningBaru",pengajuanSprDtos.getNo_rekening_baru());
                response.put("noRekeningLama",pengajuanSprDtos.getNo_rekening_lama());
                response.put("noRekeningAsal",pengajuanSprDtos.getNo_rekening_asal());
                response.put("nomorSertifikat",pengajuanSprDtos.getNo_sertifikat());
                response.put("jFlagPersetujuanSpr",pengajuanSprDtos.getJ_flag_persetujuan_spr());
                response.put("iidDdBank",pengajuanSprDtos.getId_dd_bank());
                res.add(response);
            }
            log.info("hasilnya res :" + res);
            return ResponseEntity.ok(Response.customeResponseSpr(true,"Success Get Data SPR",00,response));
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            return ResponseEntity.ok(Response.customeResponseSpr(false,"Illegal Argument",01, null));
        }
    }
}
