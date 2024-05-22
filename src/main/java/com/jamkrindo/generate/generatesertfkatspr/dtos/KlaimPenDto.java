package com.jamkrindo.generate.generatesertfkatspr.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KlaimPenDto {

    private int id_dd_bank;
    private int id_pengajuan_klaim;
    private int id_opmt_transaksi_penjaminan;
    private int id_dc_wilayah_kerja;
    private int id_dc_produk;
    private String nomor_peserta;
    private String nomor_sertifikat;
    private String tanggal_sertifikat;
    private String no_rekening;
    private String nama_nasabah;
    private String nomor_permohonan_klaim;
    private Date tanggal_permohonan_klaim;
    private int flag_keputusan;
    private String nomor_keputusan;
    private Date tanggal_keputusan;
    private int kolektabilitas;
    private int id_dd_penyebab_klaim;
    private BigDecimal nominal_tuntutan_klaim;

    private int id_opmt_sertifikat;
    private int id_dd_bank_cabang;
    private BigDecimal jumlah_klaim_bank;
}
