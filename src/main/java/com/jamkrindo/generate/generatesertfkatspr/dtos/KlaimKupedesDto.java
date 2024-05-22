package com.jamkrindo.generate.generatesertfkatspr.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KlaimKupedesDto {

    private int id_pengajuan_klaim;
    private String no_spk;
    private String tgl_spk;
    private BigDecimal nilai_tuntutan_klaim;
    private int flag_transfer;
    private int id_dd_bank;
    private BigDecimal nilai_persetujuan;
    private int id_opmt_pengajuan_tamp;
    private String no_surat_bank;
    private String tgl_surat_bank;
    private int flag_status;
    private int id_opmt_klaim_detail;
    private int id_opmt_sertifikat;
    private int id_opmt_transaksi_penjaminan;
    private int id_dc_wilayah_kerja;
    private int id_dd_bank_cabang;
    private int id_dc_penyebab_klaim;
    private BigDecimal jumlah_klaim_bank;
}
