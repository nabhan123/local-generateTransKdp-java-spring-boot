package com.jamkrindo.generate.generatesertfkatspr.daos;

import com.jamkrindo.generate.generatesertfkatspr.connection.DbCoreBri;
import com.jamkrindo.generate.generatesertfkatspr.dtos.PengajuanKlaimDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KlaimDalamProses {

    private final Logger log = LoggerFactory.getLogger(KlaimDalamProses.class);
    private static Connection connection;

    public List<PengajuanKlaimDto> getKlaimDalamProses(PengajuanKlaimDto data, DbCoreBri dbCoreBri)throws Exception{

        try{

            Connection conn = dbCoreBri.getConnection();

            Integer idDdBank = data.getId_dd_bank();
            String kdp = "SELECT\n" +
                    "\tkl.no_rekening,\n" +
                    "\tkl.id_dd_bank,\n" +
                    "\tkl.id_pengajuan_klaim,\n" +
                    "\tkl.id_pengajuan_klaim_sync,\n" +
                    "\tdet.id_opmt_klaim_detail,\n" +
                    "\treg.id_opmt_klaim_register,\n" +
                    "\tok.id_opmt_klaim,\n" +
                    "\tos.id_opmt_sertifikat,\n" +
                    "\tdet.flag_setuju,\n" +
                    "\tdet.flag_sync,\n" +
                    "\tdet.flag_transfer,\n" +
                    "\treg.flag_status,\n" +
                    "\treg.flag_sync,\n" +
                    "\tok.flag_sync,\n" +
                    "\tok.flag_transfer \n" +
                    "FROM\n" +
                    "\tpengajuan_klaim kl\n" +
                    "\tINNER JOIN opmt_transaksi_penjaminan otp ON otp.id_calon_debitur_kur = kl.id_calon_debitur_kur\n" +
                    "\tINNER JOIN opmt_permohonan op ON op.id_opmt_permohonan = otp.id_opmt_permohonan\n" +
                    "\tINNER JOIN opmt_sertifikat os ON os.id_opmt_sertifikat = op.id_opmt_sertifikat\n" +
                    "\tINNER JOIN opmt_klaim ok ON ok.id_opmt_sertifikat = os.id_opmt_sertifikat\n" +
                    "\tLEFT JOIN opmt_klaim_detail det ON det.id_pengajuan_klaim = kl.id_pengajuan_klaim\n" +
                    "\tLEFT JOIN opmt_klaim_register reg ON reg.id_opmt_klaim_detail = det.id_opmt_klaim_detail \n" +
                    "WHERE\n" +
                    "\tkl.id_dd_bank = 1299 \n" +
                    "\tAND det.id_opmt_klaim_detail IS NOT NULL \n" +
                    "\tAND reg.id_opmt_klaim_register IS NOT NULL \n" +
                    "\tAND ok.id_opmt_klaim IS NOT NULL";
            PreparedStatement statement = conn.prepareStatement(kdp);
            ResultSet resultSet = statement.executeQuery();
            List<PengajuanKlaimDto> res = new ArrayList<>();

            while (resultSet.next()){
                PengajuanKlaimDto tamp = new PengajuanKlaimDto();
                tamp.setId_pengajuan_klaim(resultSet.getInt("id_pengajuan_klaim"));
                tamp.setNo_rekening(resultSet.getString("no_rekening"));
                tamp.setId_dd_bank(resultSet.getInt("id_dd_bank"));
                res.add(tamp);
            }
            return res;
        }catch (SQLException e){
            throw new Exception(e.getMessage());
        }
    }

    public void updatingFlagKdp(PengajuanKlaimDto request)throws Exception{

        String updateKdp = "UPDATE opmt_klaim_detail ";

    }
}
