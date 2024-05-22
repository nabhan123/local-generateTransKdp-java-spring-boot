package com.jamkrindo.generate.generatesertfkatspr.daos.producer;

import com.jamkrindo.generate.generatesertfkatspr.connection.DbCoreBri;
import com.jamkrindo.generate.generatesertfkatspr.dtos.PengajuanKlaimDto;
import com.jamkrindo.generate.generatesertfkatspr.request.GenerateTransaksiRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PengajuanKlaimKurDaos {

    private static Logger log = LoggerFactory.getLogger(PengajuanKlaimKurDaos.class);
    private static Connection connection;
    public static List<PengajuanKlaimDto> findDebiturKlaimtoSync()throws Exception{

        DbCoreBri dbCoreBri = new DbCoreBri();
        Connection conn = dbCoreBri.getConnection();
        String query = """
                SELECT TOP
                      	10 kl.id_pengajuan_klaim,
                      	det.id_opmt_klaim_detail,
                      	ok.id_opmt_klaim,
                      	kl.nama_debitur,
                      	kl.id_dd_bank,
                      	kl.no_spk,
                      	kl.tgl_spk,
                      	otp.id_opmt_transaksi_penjaminan,
                      	op.id_opmt_permohonan,
                      	ots.id_opmt_sertifikat,
                      	wk.id_dc_wilayah_kerja,
                      	kl.nilai_tuntutan_klaim,
                      	cb.id_dd_bank_cabang,
                      	kl.no_rekening\s
                      FROM
                      	pengajuan_klaim kl
                      	LEFT JOIN opmt_transaksi_penjaminan otp ON otp.id_calon_debitur_kur = kl.id_calon_debitur_kur
                      	INNER JOIN opmt_permohonan op ON op.id_opmt_permohonan = otp.id_opmt_permohonan
                      	INNER JOIN opmt_sertifikat ots ON ots.id_opmt_sertifikat = op.id_opmt_sertifikat
                      	INNER JOIN dc_wilayah_kerja_new wk ON wk.id_dc_wilayah_kerja = otp.id_dc_wilayah_kerja
                      	INNER JOIN dd_bank_cabang cb ON cb.id_dd_bank_cabang = op.id_dd_bank_cabang
                      	LEFT JOIN opmt_klaim ok ON ok.id_opmt_sertifikat = ots.id_opmt_sertifikat
                      	LEFT JOIN opmt_klaim_detail det ON det.id_pengajuan_klaim = kl.id_pengajuan_klaim
                      	LEFT JOIN opmt_klaim_register reg ON reg.id_opmt_klaim_detail = det.id_opmt_klaim_detail\s
                      WHERE
                      	det.id_opmt_klaim_detail IS NULL\s
                      	AND reg.id_opmt_klaim_register IS NULL\s
                      	AND ok.id_opmt_klaim IS NULL\s
                      	AND COALESCE ( kl.persetujuan_klaim, 0 ) = 0\s
                      	AND kl.persetujuan_klaim != 99 
                """;

        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        List<PengajuanKlaimDto> tamp = new ArrayList<>();
        while (resultSet.next()){
            PengajuanKlaimDto data = new PengajuanKlaimDto();
            data.setId_dd_bank(resultSet.getInt("id_dd_bank")); //1
            data.setNo_rekening(resultSet.getString("no_rekening")); //2
            data.setId_pengajuan_klaim(resultSet.getInt("id_pengajuan_klaim")); //3
            data.setId_opmt_sertifikat(resultSet.getInt("id_opmt_sertifikat")); //4
            data.setNo_spk(resultSet.getString("no_spk")); //5
            data.setTgl_spk(resultSet.getString("tgl_spk")); //6
            data.setId_dc_wilayah_kerja(resultSet.getInt("id_dc_wilayah_kerja")); //7
            data.setId_opmt_transaksi_penjaminan(resultSet.getInt("id_opmt_transaksi_penjaminan")); //8
            data.setNilai_tuntutan_klaim(resultSet.getBigDecimal("nilai_tuntutan_klaim")); //9
            data.setId_dd_bank_cabang(resultSet.getInt("id_dd_bank_cabang")); //10
            tamp.add(data);
        }

        return tamp;

    }

    public List<PengajuanKlaimDto> fndDebReadyToSync(Object request)throws Exception{

        DbCoreBri dbCoreBri = new DbCoreBri();
        GenerateTransaksiRequest req = new GenerateTransaksiRequest();
        Connection conn = dbCoreBri.getConnection();
        String query = "SELECT kl.id_pengajuan_klaim, " +
                "kl.nama_debitur, " +
                "kl.id_dd_bank, " +
                "kl.no_spk, " +
                "kl.tgl_spk, " +
                "otp.id_opmt_transaksi_penjaminan, " +
                "op.id_opmt_permohonan, " +
                "ots.id_opmt_sertifikat, " +
                "wk.id_dc_wilayah_kerja, " +
                "kl.nilai_tuntutan_klaim, " +
                "cb.id_dd_bank_cabang, " +
                "kl.no_rekening FROM " +
                "pengajuan_klaim kl " +
                "LEFT JOIN opmt_transaksi_penjaminan otp ON otp.id_calon_debitur_kur = kl.id_calon_debitur_kur " +
                "INNER JOIN opmt_permohonan op ON op.id_opmt_permohonan = otp.id_opmt_permohonan " +
                "INNER JOIN opmt_sertifikat ots ON ots.id_opmt_sertifikat = op.id_opmt_sertifikat " +
                "INNER JOIN dc_wilayah_kerja_new wk ON wk.id_dc_wilayah_kerja = otp.id_dc_wilayah_kerja " +
                "INNER JOIN dd_bank_cabang cb ON cb.id_dd_bank_cabang = op.id_dd_bank_cabang " +
                "LEFT JOIN opmt_klaim ok ON ok.id_opmt_sertifikat = ots.id_opmt_sertifikat " +
                "LEFT JOIN opmt_klaim_detail det ON det.id_pengajuan_klaim = kl.id_pengajuan_klaim " +
                "LEFT JOIN opmt_klaim_register reg ON reg.id_opmt_klaim_detail = det.id_opmt_klaim_detail " +
                "WHERE kl.id_dd_bank = ? " +
                "AND kl.id_pengajuan_klaim = 1656628 " +
                "AND det.id_opmt_klaim_detail is null " +
                "AND reg.id_opmt_klaim_register is null " +
                "AND ok.id_opmt_klaim is null";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setObject(1, req.getId_dd_bank());
//        statement.setObject(2, pengajuanKlaimDto.getNo_rekening());
        ResultSet resultSet = statement.executeQuery();

        List<PengajuanKlaimDto> tamp = new ArrayList<>();
        while (resultSet.next()){
            PengajuanKlaimDto data = new PengajuanKlaimDto();
            data.setId_dd_bank(resultSet.getInt("id_dd_bank")); //1
            data.setNo_rekening(resultSet.getString("no_rekening")); //2
            data.setId_pengajuan_klaim(resultSet.getInt("id_pengajuan_klaim")); //3
            data.setId_opmt_sertifikat(resultSet.getInt("id_opmt_sertifikat")); //4
            data.setNo_spk(resultSet.getString("no_spk")); //5
            data.setTgl_spk(resultSet.getString("tgl_spk")); //6
            data.setId_dc_wilayah_kerja(resultSet.getInt("id_dc_wilayah_kerja")); //7
            data.setId_opmt_transaksi_penjaminan(resultSet.getInt("id_opmt_transaksi_penjaminan")); //8
            data.setNilai_tuntutan_klaim(resultSet.getBigDecimal("nilai_tuntutan_klaim")); //9
            data.setId_dd_bank_cabang(resultSet.getInt("id_dd_bank_cabang")); //10
            tamp.add(data);
        }

        return tamp;

    }


}
