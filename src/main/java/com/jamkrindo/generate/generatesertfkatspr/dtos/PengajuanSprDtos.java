package com.jamkrindo.generate.generatesertfkatspr.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class PengajuanSprDtos {
    private String nomor_aplikasi;
    private String nama_debitur;
    private String no_rekening_baru;
    private String no_rekening_lama;
    private String no_rekening_asal;
    private String no_pk_baru;
    private String tgl_pk_baru;
    private BigDecimal plafon_kredit;
    private int jangka_waktu;
    private int j_flag_persetujuan_spr;
    private int id_dd_bank;
    private String kode_uker;
    private String jenis_kur;
    private String jenis_kredit;
    private String no_sertifikat;
    private String tgl_sertifikat;
    private String tanggal_awal;
    private String tanggal_akhir;
    private String no_sertifikat_lama;
    private int flag_covid;
    private int status_rekening;
    private int jw_sebelumnya;
    private int jw_terlewati;
    private int sisa_jw_sebelumnya;
}
