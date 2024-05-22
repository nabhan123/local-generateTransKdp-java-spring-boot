package com.jamkrindo.generate.generatesertfkatspr.services;

import com.jamkrindo.generate.generatesertfkatspr.dtos.KlaimPenDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface TransaksiKlaim {

    ResponseEntity<KlaimPenDto> inPenDto(Integer id_dd_bank) throws Exception;

    Map<String,Object> responseCustom()throws Exception;


}
