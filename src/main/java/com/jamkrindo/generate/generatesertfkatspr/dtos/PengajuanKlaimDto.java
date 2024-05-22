package com.jamkrindo.generate.generatesertfkatspr.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PengajuanKlaimDto {

    private Integer id_pengajuan_klaim;
    private String no_rekening;
    private String jenis_kredit;
    private String jenis_kur;
    private String no_spk;
    private String tgl_spk;
    private String kode_uker;
    private Integer cabang_rekanan;
    private String nama_debitur;
    private String nomor_pk;
    private BigDecimal nilai_penjaminan;
    private BigDecimal nilai_pengikatan;
    private String sebab_klaim;
    private BigDecimal nilai_tuntutan_klaim;
    private Integer id_dd_bank;
    private Integer persetujuan_klaim;
    private String nomor_persetujuan;
    private Date tgl_keputusan;
    private BigDecimal nilai_persetujuan;
    private String alasan_penolakan;
    private String no_permintaan_berkas;

    private int id_opmt_sertifikat;
    private int id_dc_wilayah_kerja;
    private int id_opmt_transaksi_penjaminan;
    private int id_dd_bank_cabang;

}
