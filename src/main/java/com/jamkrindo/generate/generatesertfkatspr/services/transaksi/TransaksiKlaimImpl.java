package com.jamkrindo.generate.generatesertfkatspr.services.transaksi;

import com.jamkrindo.generate.generatesertfkatspr.dtos.KlaimPenDto;
import com.jamkrindo.generate.generatesertfkatspr.services.TransaksiKlaim;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class TransaksiKlaimImpl implements TransaksiKlaim {

    @Override
    public ResponseEntity<KlaimPenDto> inPenDto(Integer id_dd_bank) throws Exception {
        return null;
    }

    @Override
    public Map<String, Object> responseCustom() throws Exception {
        return null;
    }
}
