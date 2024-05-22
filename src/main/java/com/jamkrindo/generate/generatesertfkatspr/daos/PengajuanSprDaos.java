package com.jamkrindo.generate.generatesertfkatspr.daos;


import com.jamkrindo.generate.generatesertfkatspr.connection.DbCoreBri;
import com.jamkrindo.generate.generatesertfkatspr.dtos.PengajuanSprDtos;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class PengajuanSprDaos {

    private static Logger log = LoggerFactory.getLogger(PengajuanSprDaos.class);

    public static List<PengajuanSprDtos> findAllDebiturSpr(DbCoreBri dbCoreBri, PengajuanSprDtos pengajuanSprDtos) throws Exception {

        log.info("Get All Debitur SPR BRI");
        try {
            Connection connection = dbCoreBri.getConnection();

            String query = "SELECT * FROM pengajuan_spr WHERE id_dd_bank = ? and j_flag_persetujuan_spr = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setObject(1, pengajuanSprDtos.getId_dd_bank());
            statement.setObject(2, pengajuanSprDtos.getJ_flag_persetujuan_spr());
            log.info("kesini cek data ada : ");
            ResultSet rs = statement.executeQuery();

            //        bikin list buat menampung data tersebut
            List<PengajuanSprDtos> map = new ArrayList<>();
            while (rs.next()) {
                PengajuanSprDtos data = new PengajuanSprDtos();
                data.setNama_debitur(rs.getString("nama_debitur"));
                data.setJenis_kredit(rs.getString("jenis_kredit"));
                data.setJangka_waktu(rs.getInt("jangka_waktu"));
                data.setJw_sebelumnya(rs.getInt("jw_sebelumnya"));
                data.setJw_terlewati(rs.getInt("jw_terlewati"));
                data.setNo_sertifikat(rs.getString("no_sertifikat"));
                data.setNo_sertifikat_lama(rs.getString("no_sertifikat_lama"));
                data.setNo_rekening_baru(rs.getString("no_rekening_baru"));
                data.setNo_rekening_lama(rs.getString("no_rekening_lama"));
                data.setNo_rekening_asal(rs.getString("no_rekening_asal"));
                data.setId_dd_bank(rs.getInt("id_dd_bank"));
                map.add(data);
            }

            return map;

        } catch (Exception e) {
            throw new Exception("Error " + e.getMessage());
        }
    }
}



