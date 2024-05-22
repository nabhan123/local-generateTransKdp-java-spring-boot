package com.jamkrindo.generate.generatesertfkatspr.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransaksiKlaim {

    private int id_opmt_klaim_detail;
    private int id_opmt_transaksi_penjaminan;
    private int id_dc_penyebab_klaim;
    private int jumlah_klaim_bank;
    private int flag_setuju;
    private int flag_sync;
    private int id_dc_wilayah_kerja;
    private int id_pengajuan_klaim;
    private int id_opmt_klaim;
    private String tgl_status;

}
