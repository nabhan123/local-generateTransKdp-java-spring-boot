package com.jamkrindo.generate.generatesertfkatspr.daos.producer;

import com.jamkrindo.generate.generatesertfkatspr.connection.DbKupedes;
import com.jamkrindo.generate.generatesertfkatspr.dtos.KlaimKupedesDto;
import com.jamkrindo.generate.generatesertfkatspr.request.GenerateTransaksiRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PengajuanKlaimKupedesDaos {

    private final Logger LOG = LoggerFactory.getLogger(PengajuanKlaimKupedesDaos.class);

    public List<KlaimKupedesDto> fnDebiturKupedesToSync(GenerateTransaksiRequest request)throws Exception{

        DbKupedes dbKupedes = new DbKupedes();
        Connection connection = dbKupedes.getConnection();

        List<KlaimKupedesDto> response = new ArrayList<>();

        try{

            String queryKupedes = "SELECT \n" +
                    "\tkl.id_pengajuan_klaim\n" +
                    "\t,kl.no_spk\n" +
                    "\t,kl.tgl_spk\n" +
                    "\t,kl.id_dd_bank\n" +
                    "\t,kl.nilai_tuntutan_klaim\n" +
                    "\t,det.id_opmt_klaim_detail\n" +
                    "\t,reg.id_opmt_klaim_register\n" +
                    "\t,ok.id_opmt_klaim\n" +
                    "\t,otp.id_opmt_transaksi_penjaminan\n" +
                    "\t,os.id_opmt_sertifikat\n" +
                    "\t,cb.id_dd_bank_cabang\n" +
                    "\t,det.flag_sync\n" +
                    "\t,reg.flag_sync\n" +
                    "\t,det.id_dc_penyebab_klaim\n" +
                    "\t,det.jumlah_klaim_bank\n" +
                    "\t,wk.id_dc_wilayah_kerja\n" +
                    "FROM pengajuan_klaim kl\n" +
                    "inner join opmt_pengajuan_tamp opt ON opt.id_opmt_pengajuan_tamp = kl.id_opmt_pengajuan_tamp\n" +
                    "left join opmt_klaim_detail det ON det.id_pengajuan_klaim = kl.id_pengajuan_klaim\n" +
                    "left join opmt_klaim_register reg ON reg.id_opmt_klaim_detail = det.id_opmt_klaim_detail\n" +
                    "left join opmt_transaksi_penjaminan otp ON otp.id_opmt_pengajuan_tamp = kl.id_opmt_pengajuan_tamp\n" +
                    "left join opmt_permohonan op ON op.id_opmt_permohonan = otp.id_opmt_permohonan\n" +
                    "inner join dd_bank_cabang cb ON cb.id_dd_bank_cabang = op.id_dd_bank_cabang\n" +
                    "inner join dc_wilayah_kerja wk ON wk.id_dc_wilayah_kerja = otp.id_dc_wilayah_kerja\n" +
                    "left join opmt_sertifikat os ON os.id_opmt_sertifikat = op.id_opmt_sertifikat\n" +
                    "left join opmt_klaim ok ON ok.id_opmt_sertifikat = os.id_opmt_sertifikat\n" +
                    "where 1=1 \n" +
                    "and kl.id_dd_bank = ?\n" +
                    "and det.id_opmt_klaim_detail is null\n" +
                    "and reg.id_opmt_klaim_register is null\n" +
                    "and kl.id_pengajuan_klaim = 20277\n" +
                    "and ok.id_opmt_klaim is null";
            PreparedStatement statement = connection.prepareStatement(queryKupedes);
            statement.setObject(1,request.getId_dd_bank());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                KlaimKupedesDto klaimKupedesDto = new KlaimKupedesDto();
                klaimKupedesDto.setId_pengajuan_klaim(resultSet.getInt("id_pengajuan_klaim"));
                klaimKupedesDto.setId_dd_bank(resultSet.getInt("id_dd_bank"));
                klaimKupedesDto.setId_opmt_klaim_detail(resultSet.getInt("id_opmt_klaim_detail"));
                klaimKupedesDto.setId_opmt_sertifikat(resultSet.getInt("id_opmt_sertifikat"));
                klaimKupedesDto.setId_opmt_transaksi_penjaminan(resultSet.getInt("id_opmt_transaksi_penjaminan"));
                klaimKupedesDto.setId_dc_penyebab_klaim(resultSet.getInt("id_dc_penyebab_klaim"));
                klaimKupedesDto.setJumlah_klaim_bank(resultSet.getBigDecimal("jumlah_klaim_bank"));
                klaimKupedesDto.setNo_spk(resultSet.getString("no_spk"));
                klaimKupedesDto.setTgl_spk(resultSet.getString("tgl_spk"));
                klaimKupedesDto.setId_dd_bank_cabang(resultSet.getInt("id_dd_bank_cabang"));
                klaimKupedesDto.setId_dc_wilayah_kerja(resultSet.getInt("id_dc_wilayah_kerja"));
                klaimKupedesDto.setNilai_tuntutan_klaim(resultSet.getBigDecimal("nilai_tuntutan_klaim"));
                response.add(klaimKupedesDto);
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return response;
    }
}
