package org.isu_std.database;

import org.isu_std.config.SQLDatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Class that gives a connection to the SQL Database.

public class MySQLDBConnection {
    private MySQLDBConnection(){}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                SQLDatabaseConfig.URL.getValue(),
                SQLDatabaseConfig.ROOT_NAME.getValue(),
                SQLDatabaseConfig.PASSWORD.getValue()
        );
    }
}
