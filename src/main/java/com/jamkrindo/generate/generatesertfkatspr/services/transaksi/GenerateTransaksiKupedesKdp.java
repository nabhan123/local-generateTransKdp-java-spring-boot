package com.jamkrindo.generate.generatesertfkatspr.services.transaksi;


import com.jamkrindo.generate.generatesertfkatspr.connection.DbKupedes;
import com.jamkrindo.generate.generatesertfkatspr.constant.ResponseConstant;
import com.jamkrindo.generate.generatesertfkatspr.daos.producer.PengajuanKlaimKupedesDaos;
import com.jamkrindo.generate.generatesertfkatspr.dtos.KlaimKupedesDto;
import com.jamkrindo.generate.generatesertfkatspr.request.GenerateTransaksiRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.*;

@Service
public class GenerateTransaksiKupedesKdp {

    final Logger LOG = LoggerFactory.getLogger(GenerateTransaksiKupedesKdp.class);


    public void insertTransKupedesKdp(GenerateTransaksiRequest request)throws Exception{
        DbKupedes dbKupedes = new DbKupedes();
        Connection connection = dbKupedes.getConnection();
        LocalDate dateNow = LocalDate.now();
        List<KlaimKupedesDto> response = new ArrayList<>();
        connection.setAutoCommit(false);
        try{
            KlaimKupedesDto klaimKupedesDto = new KlaimKupedesDto();
            PengajuanKlaimKupedesDaos pengajuanKlaimKupedesDaos = new PengajuanKlaimKupedesDaos();
            List<KlaimKupedesDto> lsData = pengajuanKlaimKupedesDaos.fnDebiturKupedesToSync(request);
            for (KlaimKupedesDto reqBody : lsData){

                String insertOpmtKlaim = "INSERT INTO opmt_klaim ("
                        + "id_opmt_sertifikat," //1
                        + "no_surat_bank," //2
                        + "tgl_surat_bank," //3
                        + "flag_transfer," //4
                        + "tgl_status," //5
                        + "id_dc_wilayah_kerja," //6
                        + "flag_sync)" + //7
                        "values(?,?,?,?,?,?,?)";
                String insertOpmtKlaimDet = "INSERT INTO opmt_klaim_detail ("
                        + "id_opmt_klaim," //1
                        + "id_opmt_transaksi_penjaminan," //2
                        + "id_dc_penyebab_klaim," //3
                        + "jumlah_klaim_bank," //4
                        + "flag_setuju," //5
                        + "tgl_status," //6
                        + "id_dc_wilayah_kerja," //7
                        + "id_pengajuan_klaim," //8
                        + "flag_sync)" + //9
                        "values(?,?,?,?,?,?,?,?,?)";
                String insertKlaimReg = "INSERT INTO opmt_klaim_register ("
                        + "id_opmt_klaim_detail," //1
                        + "id_dd_bank_cabang," //2
                        + "flag_status," //3
                        + "tgl_status," //4
                        + "id_dc_wilayah_kerja," //5
                        + "flag_sync)" + //6
                        "values(?,?,?,?,?,?)";
                LOG.info("noSuratBank" + reqBody.getNo_spk());
                PreparedStatement statement = connection.prepareStatement(insertOpmtKlaim, Statement.RETURN_GENERATED_KEYS);
                statement.setObject(1, reqBody.getId_opmt_sertifikat());
                statement.setObject(2, reqBody.getNo_spk());
                statement.setObject(3, reqBody.getTgl_spk());
                statement.setObject(4, 0);
                statement.setObject(5, dateNow);
                statement.setObject(6, reqBody.getId_dc_wilayah_kerja());
                statement.setObject(7, 0);
                statement.executeUpdate();
                ResultSet resultSet = statement.getGeneratedKeys();
                Integer idOpmtKlaim = 0;
                if (resultSet.next()){
                    Integer idKlaimDetail = resultSet.getInt(1);
                    idOpmtKlaim += idKlaimDetail;
                    connection.commit();
                }else {
                    connection.rollback();
                    throw new Exception(ResponseConstant.FAILED_INSERT);
                }
                LOG.info("idOpmtKlaim " + idOpmtKlaim);

                PreparedStatement stmt = connection.prepareStatement(insertOpmtKlaimDet, Statement.RETURN_GENERATED_KEYS);
                stmt.setObject(1,idOpmtKlaim);
                stmt.setObject(2,reqBody.getId_opmt_transaksi_penjaminan());
                stmt.setObject(3,reqBody.getId_dc_penyebab_klaim());
                stmt.setObject(4,reqBody.getNilai_tuntutan_klaim());
                stmt.setObject(5,1);
                stmt.setObject(6,dateNow);
                stmt.setObject(7,reqBody.getId_dc_wilayah_kerja());
                stmt.setObject(8,reqBody.getId_pengajuan_klaim());
                stmt.setObject(9,0);
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                int idOpmtKlaimDet = 0;
                if (rs.next()){
                    int generateId = rs.getInt(1);
                    idOpmtKlaimDet += generateId;
                    connection.commit();
                }else {
                    connection.rollback();
                    throw new Exception(ResponseConstant.FAILED_INSERT);
                }
                LOG.info("idOpmtKlaimDet " + idOpmtKlaimDet);

                PreparedStatement st = connection.prepareStatement(insertKlaimReg,Statement.RETURN_GENERATED_KEYS);
                st.setObject(1,idOpmtKlaimDet);
                st.setObject(2,reqBody.getId_dd_bank_cabang());
                st.setObject(3,1);
                st.setObject(4,dateNow);
                st.setObject(5,reqBody.getId_dc_wilayah_kerja());
                st.setObject(6,0);
                st.executeUpdate();
                ResultSet rS = st.getGeneratedKeys();
                int idKlaimReg = 0;
                if (rS.next()){
                    int resKlaimReg = rs.getInt(1);
                    idKlaimReg += resKlaimReg;
                    connection.commit();
                }else {
                    connection.rollback();
                    throw new Exception(ResponseConstant.FAILED_INSERT);
                }
                LOG.info("idOpmtKlaimRegister " + idKlaimReg);
            }

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
