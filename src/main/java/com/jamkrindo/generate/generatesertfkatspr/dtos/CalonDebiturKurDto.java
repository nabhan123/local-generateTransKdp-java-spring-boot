package com.jamkrindo.generate.generatesertfkatspr.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CalonDebiturKurDto {

    private String nomorAplikasi;
    private String jenisKredit;
    private int jenisKur;
    private String kodeUker;
    private String namaDebitur;
    private String alamatDebitur;
    private String kodePos;
    private String tanggalLahir;
    private String jenisKelamin;
    private String noTelepon;
    private String noHp;
    private String noIdentitas;
    private String jenisIdentitas;
    private String npwp;
    private String noIjinUsaha;
    private String kodePekerjaan;
    private int jangkaWaktu;
    private String coverage;
    private int jmlTenagaKerja;
    private String jenisAgunan;
    private BigDecimal nilaiAgunan;
    private String jenisPengikatan;
    private int cabangRekanan;
    private String tglRekam;
    private String jenisLinkage;
    private String lembagaLinkage;
    private String statusLunas;
    private String statusKolektibilitas;
    private String usahaProduktif;
    private String statusAplikasi;
    private String statusData;
    private BigDecimal modalUsaha;
    private String alamatUsaha;
    private String tglMulaiUsaha;
    private Date jCreateTime;
    private int jTransfer;
    private int idCalonDebiturKurSync;
    private int idDdBank;
    private int jFlagPersetujuan;
    private String kodeBank;
    private BigDecimal omsetUsaha;

    }
