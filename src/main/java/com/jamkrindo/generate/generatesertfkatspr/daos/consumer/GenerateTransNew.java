package com.jamkrindo.generate.generatesertfkatspr.daos.consumer;

import com.jamkrindo.generate.generatesertfkatspr.connection.DbCoreBri;
import com.jamkrindo.generate.generatesertfkatspr.constant.ResponseConstant;
import com.jamkrindo.generate.generatesertfkatspr.daos.producer.PengajuanKlaimKurDaos;
import com.jamkrindo.generate.generatesertfkatspr.dtos.PengajuanKlaimDto;
import com.jamkrindo.generate.generatesertfkatspr.request.GenerateTransaksiRequest;
import com.jamkrindo.generate.generatesertfkatspr.services.kafka.ProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;


@Service
public class GenerateTransNew {

    @Autowired
    ProducerService producerService;

    private final Logger LOG = LoggerFactory.getLogger(GenerateTransNew.class);

    public void insertToTransaction()throws Exception{
        try{
            DbCoreBri dbCoreBri = new DbCoreBri();
            Connection conn = dbCoreBri.getConnection();
            LOG.info("====masuk kesini=====");
            PengajuanKlaimKurDaos data = new PengajuanKlaimKurDaos();
            PengajuanKlaimDto req = new PengajuanKlaimDto();
            LocalDate currentDate = LocalDate.now();
            conn.setAutoCommit(false);

//            ambil parameter dari sini
            List<PengajuanKlaimDto> cek = data.findDebiturKlaimtoSync();
            LOG.info("Data yang akan di sync :" + cek);

            for (PengajuanKlaimDto pengajuanKlaimDto : cek){
                if (cek.isEmpty()){
                    System.out.println("=======TIDAK ADA DATA YANG DI SYNC========");
                    throw new Exception(ResponseConstant.DATA_NOT_FOUND);
                }
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
                String opmtKlaimRegister = "INSERT INTO opmt_klaim_register ("
                        + "id_opmt_klaim_detail," //1
                        + "id_dd_bank_cabang," //2
                        + "flag_status," //3
                        + "tgl_status," //4
                        + "id_dc_wilayah_kerja," //5
                        + "flag_sync)" + //6
                        "values(?,?,?,?,?,?)";
//                set object opmt_klaim
                PreparedStatement statement = conn.prepareStatement(opmtKlaim, Statement.RETURN_GENERATED_KEYS);
                statement.setObject(1,pengajuanKlaimDto.getId_opmt_sertifikat());
                statement.setObject(2,pengajuanKlaimDto.getNo_spk());
                statement.setObject(3,pengajuanKlaimDto.getTgl_spk());
                statement.setObject(4,0);
                statement.setObject(5,currentDate);
                statement.setObject(6,pengajuanKlaimDto.getId_dc_wilayah_kerja());
                statement.setObject(7,0); //sesuaikan dengan proses KDP
                statement.executeUpdate();
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()){
                    Integer idOpmtKlaim = resultSet.getInt(1);
                    LOG.info("Success Sync, id_opmt_klaim : " + idOpmtKlaim);
                    conn.commit();
                }else {
                    LOG.error("========Gagal Insert Opmt Klaim========");
                    conn.rollback();
                    throw new Exception(ResponseConstant.FAILED_INSERT);
                }
//                set object opmt_klaim_detail
                PreparedStatement stmt = conn.prepareStatement(opmtKlaimDetail,Statement.RETURN_GENERATED_KEYS);
                stmt.setObject(1,resultSet.getInt(1));
                stmt.setObject(2,pengajuanKlaimDto.getId_opmt_transaksi_penjaminan());
                stmt.setObject(3,3);
                stmt.setObject(4,pengajuanKlaimDto.getNilai_tuntutan_klaim());
                stmt.setObject(5,0);
                stmt.setObject(6,currentDate);
                stmt.setObject(7,pengajuanKlaimDto.getId_dc_wilayah_kerja());
                stmt.setObject(8,pengajuanKlaimDto.getId_pengajuan_klaim());
                stmt.setObject(9,0);
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();

                if (rs.next()){
                    LOG.info("Success sync, id_opmt_klaim_detail : " + rs.getInt(1));
                    conn.commit();
                }else{
                    LOG.error("=======gagal insert opmt_klam_detail======");
                    conn.rollback();
                    throw new Exception(ResponseConstant.FAILED_INSERT);
                }
//                set object opmt_klaim_register
                PreparedStatement state = conn.prepareStatement(opmtKlaimRegister,Statement.RETURN_GENERATED_KEYS);
                state.setObject(1,rs.getInt(1));
                state.setObject(2,pengajuanKlaimDto.getId_dd_bank_cabang());
                state.setObject(3,1);
                state.setObject(4,currentDate);
                state.setObject(5,pengajuanKlaimDto.getId_dc_wilayah_kerja());
                state.setObject(6,0);
                state.executeUpdate();
                ResultSet res = state.getGeneratedKeys();

                if (res.next()){
                    Integer idOpmtKlaimReg = null;
                    idOpmtKlaimReg = res.getInt(1);
                    LOG.info("Success sync, id_opmt_klaim_register : " + idOpmtKlaimReg);
                    conn.commit();
                }else {
                    LOG.error("======Gagal Insert opmt_klaim_register======");
                    conn.rollback();
                    throw new Exception(ResponseConstant.FAILED_INSERT);
                }
            }
        }catch (SQLException e){
            LOG.error("gagal proses insert", e.getMessage());
            throw new Exception(ResponseConstant.FAILED_INSERT);
        }
    }

    public void generateTransKdpKur(Object request)throws Exception{

    }

}
