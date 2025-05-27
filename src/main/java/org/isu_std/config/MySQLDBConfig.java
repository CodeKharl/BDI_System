package org.isu_std.config;

import org.isu_std.io.SystemLogger;
import org.isu_std.io.custom_exception.NotFoundException;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

// Class that gives a connection to the SQL Database.

public class MySQLDBConfig {
    private final static String DB_FILE_NAME = "db.properties";

    private final Properties dbProperties;

    public MySQLDBConfig(){
        dbProperties = new Properties();

        try(InputStream inputStream = MySQLDBConfig.class
                .getClassLoader().getResourceAsStream(DB_FILE_NAME)
        ){
            if(inputStream == null){
                throw new RuntimeException(DB_FILE_NAME + "not found");
            }

            dbProperties.load(inputStream);
        }catch (IOException | RuntimeException e){
            SystemLogger.log(e.getMessage(), e);

            throw new RuntimeException("Failed to load Sql Data Base Connection!");
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                dbProperties.getProperty("db.url"),
                dbProperties.getProperty("db.username"),
                dbProperties.getProperty("db.password")
        );
    }
}
