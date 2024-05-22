package com.jamkrindo.generate.generatesertfkatspr.daos;

import com.jamkrindo.generate.generatesertfkatspr.connection.DbCoreBri;
import com.jamkrindo.generate.generatesertfkatspr.dtos.PengajuanKlaimDto;
import com.jamkrindo.generate.generatesertfkatspr.request.GenerateTransaksiRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestKlaimDaos {

    private final Logger LOG = LoggerFactory.getLogger(TestKlaimDaos.class);

    public List<PengajuanKlaimDto> findDebKlaim(GenerateTransaksiRequest request)throws Exception{

        DbCoreBri dbCoreBri = new DbCoreBri();
        Connection connection = dbCoreBri.getConnection();
        List<PengajuanKlaimDto> response = new ArrayList<>();

        try{
            String query = "SELECT TOP 10 id_pengajuan_klaim FROM pengajuan_klaim " +
                    "WHERE id_dd_bank = ? ";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setObject(1,request.getId_dd_bank());
            LOG.info("idDdBank :" + request.getId_dd_bank());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                PengajuanKlaimDto dto = new PengajuanKlaimDto();
                dto.setId_pengajuan_klaim(resultSet.getInt("id_pengajuan_klaim"));
                response.add(dto);
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return response;

    }

    public List<PengajuanKlaimDto> fndAllKdpKlaim(GenerateTransaksiRequest request)throws Exception{
        DbCoreBri dbCoreBri = new DbCoreBri();
        Connection connection = dbCoreBri.getConnection();
        List<PengajuanKlaimDto> response = new ArrayList<>();
        try{

            String queryFndKdp = "SELECT kl.id_pengajuan_klaim, " +
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
                    "AND kl.id_pengajuan_klaim = 1500692 " +
                    "AND det.id_opmt_klaim_detail is null " +
                    "AND reg.id_opmt_klaim_register is null " +
                    "AND ok.id_opmt_klaim is null";
            PreparedStatement statement = connection.prepareStatement(queryFndKdp);
            statement.setObject(1,request.getId_dd_bank());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                PengajuanKlaimDto data = new PengajuanKlaimDto();
                data.setId_pengajuan_klaim(resultSet.getInt("id_pengajuan_klaim"));
                data.setId_opmt_sertifikat(resultSet.getInt("id_opmt_sertifikat"));
                data.setNo_spk(resultSet.getString("no_spk"));
                data.setTgl_spk(resultSet.getString("tgl_spk"));
                data.setId_dd_bank(resultSet.getInt("id_dd_bank"));
                data.setId_opmt_transaksi_penjaminan(resultSet.getInt("id_opmt_transaksi_penjaminan"));
                response.add(data);
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

        return response;

    }
}
