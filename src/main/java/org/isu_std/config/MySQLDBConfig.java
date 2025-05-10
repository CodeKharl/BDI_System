package org.isu_std.config;

import org.isu_std.io.SystemLogger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

// Class that gives a connection to the SQL Database.

public class MySQLDBConfig {
    private final static String DB_FILE_NAME = "db.properties";

    private static Properties dbProperties;

    private MySQLDBConfig(){}

    public static Connection getConnection() throws SQLException {
        if(dbProperties == null){
            dbProperties = new Properties();
            setDbProperties();
        }

        return DriverManager.getConnection(
                dbProperties.getProperty("db.url"),
                dbProperties.getProperty("db.username"),
                dbProperties.getProperty("db.password")
        );
    }

    private static void setDbProperties(){
        try(InputStream inputStream = MySQLDBConfig.class
                .getClassLoader().getResourceAsStream(DB_FILE_NAME)
        ){
            if(inputStream == null){
                throw new RuntimeException("Failed to load " + DB_FILE_NAME);
            }

            dbProperties.load(inputStream);
        }catch (IOException e){
            throw new RuntimeException("Error loading " + DB_FILE_NAME);
        }
    }
}
