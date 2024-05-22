package com.jamkrindo.generate.generatesertfkatspr.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class KafkaRequestPar {

    private Integer id_dc_produk;
    private Integer id_dd_bank;
    private Integer status_code;
    private Date requestTime;
    private Date responseTime;



}
