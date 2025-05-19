package org.isu_std.dao.jdbc_helper;

import org.isu_std.config.MySQLDBConfig;
import org.isu_std.models.Document;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCHelper {
    private final MySQLDBConfig mySQLDBConfig;

    public JDBCHelper(MySQLDBConfig mySQLDBConfig){
        this.mySQLDBConfig = mySQLDBConfig;
    }

    public int executeUpdate(String sql, Object... params) throws SQLException, IOException{
        try(Connection connection = mySQLDBConfig.getConnection();
            PreparedStatement preStatement = connection.prepareStatement(sql)
        ){
            setParameters(preStatement, params);
            return preStatement.executeUpdate();
        }
    }

    public <T> Optional<T> executeSingleSet(String sql, ResultSetMapper<T> mapper, Object... params)
            throws SQLException, IOException{
        try(Connection connection = mySQLDBConfig.getConnection();
            PreparedStatement preStatement = connection.prepareStatement(sql);
        ){
            setParameters(preStatement, params);

            try(ResultSet resultSet = preStatement.executeQuery()){
                if(resultSet.next()){
                    return Optional.of(mapper.map(resultSet));
                }
            }
        }

        return Optional.empty();
    }

    public <T> List<T> executeQuery(String sql, ResultSetMapper<T> mapper, Object... params)
            throws SQLException, IOException{
        List<T> list = new ArrayList<>();

        try(Connection connection = mySQLDBConfig.getConnection();
            PreparedStatement preStatement = connection.prepareStatement(sql)
        ){
            setParameters(preStatement, params);

            try(ResultSet resultSet = preStatement.executeQuery()){
                while(resultSet.next()){
                    list.add(mapper.map(resultSet));
                }
            }

            return list;
        }
    }

    private void setParameters(PreparedStatement preStatement, Object... params) throws SQLException, IOException{
        int paramIndex = 1;

        for(Object param : params){
            if(param == null){
                continue;
            }

            String paramStr = String.valueOf(param);
            if(Integer.parseInt(paramStr) == 0 || paramStr.isBlank()){
                continue;
            }

            setParameter(preStatement, paramIndex, param);
            paramIndex++;
        }
    }

    private void setParameter(PreparedStatement preStatement, int paramIndex, Object param)
            throws SQLException, IOException{
        if(param instanceof File file){
            setFileParameter(preStatement, paramIndex, file);
            return;
        }

        preStatement.setObject(paramIndex, param);
    }

    private void setFileParameter(PreparedStatement preStatement, int paramIndex, File file)
            throws IOException, SQLException {
        try(InputStream inputStream = new FileInputStream(file)){
            preStatement.setBinaryStream(paramIndex, inputStream, file.length());
        }
    }
}
