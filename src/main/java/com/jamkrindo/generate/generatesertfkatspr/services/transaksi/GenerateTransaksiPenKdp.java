package com.jamkrindo.generate.generatesertfkatspr.services.transaksi;

import com.jamkrindo.generate.generatesertfkatspr.connection.DbPen;
import com.jamkrindo.generate.generatesertfkatspr.constant.ResponseConstant;
import com.jamkrindo.generate.generatesertfkatspr.daos.producer.PengajuanKlaimPenDaos;
import com.jamkrindo.generate.generatesertfkatspr.dtos.KlaimPenDto;
import com.jamkrindo.generate.generatesertfkatspr.request.GenerateTransaksiRequest;
import com.jamkrindo.generate.generatesertfkatspr.services.kafka.ProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.*;


@Service
public class GenerateTransaksiPenKdp {

    @Autowired
    ProducerService producerService;


    private final Logger LOG = LoggerFactory.getLogger(GenerateTransaksiPenKdp.class);

    public void insertToTabelTransPen()throws Exception{
        DbPen dbPen = new DbPen();
        Connection connection = dbPen.getConnection();
        connection.setAutoCommit(false);
        LocalDate currentDate = LocalDate.now();

        try{

        PengajuanKlaimPenDaos pengajuanKlaimPenDaos = new PengajuanKlaimPenDaos();
        List<KlaimPenDto> listData = pengajuanKlaimPenDaos.fnKdpPen();
        List<Map<String,Object>> record = new ArrayList<>();
        for (KlaimPenDto reqDto : listData) {
            String opmtKlaim = "INSERT INTO opmt_klaim ("
                    + "id_opmt_sertifikat," //1
                    + "no_surat_bank," //2
                    + "tgl_surat_bank," //3
                    + "flag_transfer," //4
                    + "tgl_status," //5
                    + "id_dc_wilayah_kerja," //6
                    + "flag_sync)" + //7
                    "values(?,?,?,?,?,?,?)";
            String opmtKlaimDetail = "INSERT INTO opmt_klaim_detail ("
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
//            awalnya dipakai, namun dicomment aja
//            String opmtKlaimRegister = "INSERT INTO opmt_klaim_register ("
//                    + "id_opmt_klaim_detail," //1
//                    + "id_dd_bank_cabang," //2
//                    + "flag_status," //3
//                    + "tgl_status," //4
//                    + "id_dc_wilayah_kerja," //5
//                    + "flag_sync)" + //6
//                    "values(?,?,?,?,?,?)";
            LOG.info("noSuratBank :" + reqDto.getNomor_permohonan_klaim());
            PreparedStatement statement = connection.prepareStatement(opmtKlaim, Statement.RETURN_GENERATED_KEYS);
            statement.setObject(1, reqDto.getId_opmt_sertifikat());
            statement.setObject(2, reqDto.getNomor_permohonan_klaim());
            statement.setObject(3, reqDto.getTanggal_permohonan_klaim());
            statement.setObject(4, 0);
            statement.setObject(5, currentDate);
            statement.setObject(6, reqDto.getId_dc_wilayah_kerja());
            statement.setObject(7, 0);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys(); // get id_opmt_klaim
            LOG.info("resultSet :" + resultSet);
            int idOpmtKlaim = 0;
            if (resultSet.next()) {
                int resId = resultSet.getInt(1);
                idOpmtKlaim += resId;
                connection.commit();
            }else {
                connection.rollback();
                throw new Exception(ResponseConstant.FAILED_INSERT);
            }
            LOG.info("idOpmtKlaim :" + idOpmtKlaim);
            LOG.info("jumlah_klaim_bank" + reqDto.getJumlah_klaim_bank());
            PreparedStatement stmt = connection.prepareStatement(opmtKlaimDetail, Statement.RETURN_GENERATED_KEYS);
            stmt.setObject(1, idOpmtKlaim);
            stmt.setObject(2, reqDto.getId_opmt_transaksi_penjaminan());
            stmt.setObject(3, reqDto.getId_dd_penyebab_klaim());
            if(reqDto.getNominal_tuntutan_klaim() == null){
                stmt.setObject(4, 0);
            }
            stmt.setObject(4, reqDto.getNominal_tuntutan_klaim());
            stmt.setObject(5, 0); //pastikan ulang
            stmt.setObject(6, currentDate);
            stmt.setObject(7, reqDto.getId_dc_wilayah_kerja());
            stmt.setObject(8, reqDto.getId_pengajuan_klaim());
            stmt.setObject(9, 0); //pastikan ulang
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            LOG.info("rs :" + rs);
            int resKlaimDet = 0;
            if (rs.next()){
                int idOpmtKlaimDetail = rs.getInt(1);
                resKlaimDet += idOpmtKlaimDetail;
                connection.commit();
            }else {
                connection.rollback();
                throw new Exception(ResponseConstant.FAILED_INSERT);
            }

//            comment dulu insert register

//            PreparedStatement state = connection.prepareStatement(opmtKlaimRegister,Statement.RETURN_GENERATED_KEYS);
//            state.setObject(1,resKlaimDet);
//            state.setObject(2, reqDto.getId_dd_bank_cabang());
//            state.setObject(3,1);
//            state.setObject(4,currentDate);
//            state.setObject(5,reqDto.getId_dc_wilayah_kerja());
//            state.setObject(6,0);
//            state.executeUpdate();
//            ResultSet resultSetN = state.getGeneratedKeys();
//            int resKlaimReg = 0;
//            if(resultSetN.next()){
//                int idOpmtKlaimReg = resultSetN.getInt(1);
//                resKlaimReg += idOpmtKlaimReg;
//                connection.commit();
//            }else{
//                connection.rollback();
//                throw new Exception("Gagal Proses Insert Opmt Klaim Register");
//            }
//            LOG.info("idOPmtKlaimReg : " + resKlaimReg);
        }

        }catch (Exception e){
            LOG.info(e.getMessage());
        }

    }



}
