package com.jamkrindo.generate.generatesertfkatspr.services.transaksi;


import com.jamkrindo.generate.generatesertfkatspr.connection.DbCoreBri;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.Map;


@Service
public class GenerateTransKurKdp {

    private final Logger LOG = LoggerFactory.getLogger(GenerateTransKurKdp.class);

    public void publishTransKur(Map<String,Object> param)throws Exception{

        DbCoreBri dbCoreBri = new DbCoreBri();
        Connection connection = dbCoreBri.getConnection();
        try {
            LOG.info("==START GENERATE TRANSACTION==");
            LOG.info("==Get Data Klaim yang Belum Generate==");
            LOG.info("id Bank adalah :" + param);


        }catch (Exception e){
            e.getMessage();
        }

    }
}
