package com.jamkrindo.generate.generatesertfkatspr.connection;

import com.jamkrindo.generate.generatesertfkatspr.GenerateSertfkatSprApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbCoreBri {

    private static Connection connection;
    private final Logger log =LoggerFactory.getLogger(DbCoreBri.class);

    public DbCoreBri(){

        if(DbCoreBri.connection == null){
//            bikin koneksi
            try{
                Properties prop = new Properties();
                InputStream inputStream = GenerateSertfkatSprApplication.class.getClassLoader()
                        .getResourceAsStream("application.properties");
                prop.load(inputStream);
                log.info("inputStream : " + inputStream);
                String driver = prop.getProperty("core_kur_bri_dev.driverClassName");
                String url = prop.getProperty("core_kur_bri_dev.url");
                String user = prop.getProperty("core_kur_bri_dev.username");
                String password = prop.getProperty("core_kur_bri_dev.password");
                Class.forName(driver);
                DbCoreBri.connection = DriverManager.getConnection(url, user, password);
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }catch (SQLException e){
                e.printStackTrace();
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }

        }

    }

    public Connection getConnection() {
        return DbCoreBri.connection;
    }
}
