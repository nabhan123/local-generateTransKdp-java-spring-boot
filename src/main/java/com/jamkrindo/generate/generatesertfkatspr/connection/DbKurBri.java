package com.jamkrindo.generate.generatesertfkatspr.connection;

import com.jamkrindo.generate.generatesertfkatspr.GenerateSertfkatSprApplication;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbKurBri {

    private static Connection connection;
    public DbKurBri(){
        if(DbKurBri.connection == null){
            try{
                Properties prop = new Properties();
                InputStream inputStream = GenerateSertfkatSprApplication.class.getClassLoader()
                        .getResourceAsStream("applilcation.properties");
                prop.load(inputStream);
                String driver = prop.getProperty("kur_bri_dev.driverClassName");
                String url = prop.getProperty("kur_bri_dev.url");
                String user = prop.getProperty("kur_bri_dev.username");
                String password = prop.getProperty("kur_bri_dev.password");
                Class.forName(driver);
                DbKurBri.connection = DriverManager.getConnection(url, user, password);

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

    public static Connection getConnection() {
        return DbKurBri.connection;
    }
}
