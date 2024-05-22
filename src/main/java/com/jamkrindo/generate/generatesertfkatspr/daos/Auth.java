package com.jamkrindo.generate.generatesertfkatspr.daos;

import com.jamkrindo.generate.generatesertfkatspr.connection.DbCoreBri;
import com.jamkrindo.generate.generatesertfkatspr.dtos.AuthDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Auth {

    private static Logger log = LoggerFactory.getLogger(Auth.class);

    public String encryptPassword(String password)throws Exception{

        try{
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(password.getBytes());

            byte[] bytes = messageDigest.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for (byte aByte : bytes){
                stringBuilder.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            return stringBuilder.toString();
        }catch (NoSuchAlgorithmException e){
            throw new Exception("MD5 algorithm not found",e);
        }
    }

    public List<AuthDto> userLogin(AuthDto authDto, DbCoreBri dbCoreBri)throws Exception{

        try {
            Connection connection = dbCoreBri.getConnection();
            List<AuthDto> tamp = new ArrayList<>();

            String user = "SELECT * FROM dd_user WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(user);
            statement.setObject(1, authDto.getUsername());
            statement.setObject(2,encryptPassword(authDto.getPassword()));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                AuthDto data = new AuthDto();
                log.info("hasil" + resultSet);
                data.setUsername(resultSet.getString("username"));
                data.setPassword(resultSet.getString("password"));
                tamp.add(data);
            }
            return tamp;
        }catch (SQLException e){
            e.printStackTrace();
            log.error("Error occurred while executing SQL query: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
