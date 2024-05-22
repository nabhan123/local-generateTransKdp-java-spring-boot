package com.jamkrindo.generate.generatesertfkatspr.connection;

import com.jamkrindo.generate.generatesertfkatspr.GenerateSertfkatSprApplication;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbPen {

    private static Connection connection;

    public DbPen(){
        if (DbPen.connection == null){
            try{
                Properties prop = new Properties();
                InputStream inputStream = GenerateSertfkatSprApplication.class.getClassLoader()
                        .getResourceAsStream("application.properties");
                prop.load(inputStream);
                String driver = prop.getProperty("pen_dev.driverClassName");
                String url = prop.getProperty("pen_dev.url");
                String user = prop.getProperty("pen_dev.username");
                String password = prop.getProperty("pen_dev.password");
                Class.forName(driver);
                DbPen.connection = DriverManager.getConnection(url, user, password);
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
        return DbPen.connection;
    }
}
