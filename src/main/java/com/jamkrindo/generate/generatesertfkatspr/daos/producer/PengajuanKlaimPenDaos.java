package com.jamkrindo.generate.generatesertfkatspr.daos.producer;

import com.jamkrindo.generate.generatesertfkatspr.connection.DbPen;
import com.jamkrindo.generate.generatesertfkatspr.daos.producer.PengajuanKlaimKurDaos;
import com.jamkrindo.generate.generatesertfkatspr.dtos.KlaimPenDto;
import com.jamkrindo.generate.generatesertfkatspr.dtos.PengajuanKlaimDto;
import com.jamkrindo.generate.generatesertfkatspr.request.GenerateTransaksiRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PengajuanKlaimPenDaos {

    private final Logger LOG = LoggerFactory.getLogger(PengajuanKlaimKurDaos.class);

    public List<KlaimPenDto> fnKdpPen()throws Exception{

        DbPen dbPen = new DbPen();
        Connection connection = dbPen.getConnection();
        try{
            String query = "SELECT kl.id_pengajuan_klaim,\n" +
                    "       kl.no_rekening,\n" +
                    "       kl.nomor_permohonan_klaim,\n" +
                    "       kl.tanggal_permohonan_klaim,\n" +
                    "       kl.id_dc_wilayah_kerja,\n" +
                    "       kl.id_dd_penyebab_klaim,\n" +
                    "       kl.nominal_tuntutan_klaim,\n" +
                    "       os.nomor_sk,\n" +
                    "       otp.id_opmt_transaksi_penjaminan,\n" +
                    "       op.id_opmt_permohonan,\n" +
                    "       os.id_opmt_sertifikat,\n" +
                    "       det.id_opmt_klaim_detail,\n" +
                    "       reg.id_opmt_klaim_register,\n" +
                    "       ok.id_opmt_klaim,\n" +
                    "       cb.id_dd_bank_cabang,\n" +
                    "       wk.id_dc_wilayah_kerja, \n" +
                    "       det.jumlah_klaim_bank\n" +
                    "FROM pengajuan_klaim kl\n" +
                    "LEFT JOIN opmt_transaksi_penjaminan otp ON otp.id_calon_debitur_kur = kl.id_calon_debitur_kur\n" +
                    "INNER JOIN opmt_permohonan op ON op.id_opmt_permohonan = otp.id_opmt_permohonan\n" +
                    "INNER JOIN opmt_sertifikat os ON os.id_opmt_sertifikat = op.id_opmt_sertifikat\n" +
                    "INNER JOIN dc_wilayah_kerja_new wk ON wk.id_dc_wilayah_kerja = otp.id_dc_wilayah_kerja\n" +
                    "INNER JOIN dd_bank_cabang cb ON cb.id_dd_bank_cabang = op.id_dd_bank_cabang\n" +
                    "LEFT JOIN opmt_klaim_detail det ON det.id_pengajuan_klaim = kl.id_pengajuan_klaim\n" +
                    "LEFT JOIN opmt_klaim_register reg ON reg.id_opmt_klaim_detail = det.id_opmt_klaim_detail\n" +
                    "LEFT JOIN opmt_klaim ok ON ok.id_opmt_sertifikat = os.id_opmt_sertifikat\n" +
                    "WHERE det.id_opmt_klaim_detail IS NULL\n" +
                    "  AND reg.id_opmt_klaim_register IS NULL\n" +
                    "  AND ok.id_opmt_klaim IS NULL\n";
//                    "  AND kl.id_pengajuan_klaim = 1158\n" +
//                    "  AND kl.id_dd_bank = ?\n";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            List<KlaimPenDto> tamp = new ArrayList<>();
            while (resultSet.next()){
                KlaimPenDto data = new KlaimPenDto();
                data.setId_pengajuan_klaim(resultSet.getInt("id_pengajuan_klaim"));
                data.setId_opmt_sertifikat(resultSet.getInt("id_opmt_sertifikat"));
                data.setId_opmt_transaksi_penjaminan(resultSet.getInt("id_opmt_transaksi_penjaminan"));
                data.setNomor_permohonan_klaim(resultSet.getString("nomor_permohonan_klaim"));
                data.setTanggal_permohonan_klaim(resultSet.getDate("tanggal_permohonan_klaim"));
                data.setId_dc_wilayah_kerja(resultSet.getInt("id_dc_wilayah_kerja"));
                data.setId_dd_penyebab_klaim(resultSet.getInt("id_dd_penyebab_klaim"));
                data.setNominal_tuntutan_klaim(resultSet.getBigDecimal("nominal_tuntutan_klaim"));
                data.setId_dd_bank_cabang(resultSet.getInt("id_dd_bank_cabang"));
                data.setJumlah_klaim_bank(resultSet.getBigDecimal("jumlah_klaim_bank"));
                tamp.add(data);
            }
            return tamp;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }
}
