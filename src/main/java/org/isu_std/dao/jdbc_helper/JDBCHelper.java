package org.isu_std.dao.jdbc_helper;

import org.isu_std.config.MySQLDBConfig;

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

    public int executeUpdate(String sql, Object... params) throws SQLException{
        try(Connection connection = mySQLDBConfig.getConnection();
            PreparedStatement preStatement = connection.prepareStatement(sql)
        ){
            setParameters(preStatement, params);
            return preStatement.executeUpdate();
        }
    }

    public int executeUpdateWithFiles(String sql, Object... params) throws SQLException, IOException{
        InputStream inputStream = null;

        try(Connection connection = mySQLDBConfig.getConnection();
            PreparedStatement preStatement = connection.prepareStatement(sql);

        ){
            inputStream = getAlreadySetParamWithFiles(preStatement, params);

            return preStatement.executeUpdate();
        }finally {
            closeInputStream(inputStream);
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

    private void setParameters(PreparedStatement preStatement, Object... params)
            throws SQLException{
        int paramIndex = 1;

        for(Object param : params){
            if(param == null){
                continue;
            }

            if(isParameterSet(preStatement, paramIndex, param)){
                paramIndex++;
            }
        }
    }

    private boolean isParameterSet(PreparedStatement preStatement, int paramIndex, Object param)
            throws SQLException{

        String paramStr = param.toString();
        if(!paramStr.isBlank()){
            preStatement.setObject(paramIndex, paramStr);

            return true;
        }

        return false;
    }

    private InputStream getAlreadySetParamWithFiles(PreparedStatement preStatement, Object... params)
            throws SQLException, IOException{
        int paramIndex = 1;
        InputStream inputStream = null;

        for(Object param : params){
            if(param == null){
                continue;
            }

            inputStream = getFileParameterSet(preStatement, paramIndex, param);

            if(inputStream != null || isParameterSet(preStatement, paramIndex, param)){
                paramIndex++;
            }
        }

        return inputStream;
    }

    private InputStream getFileParameterSet(
            PreparedStatement preStatement,
            int paramIndex,
            Object param
    ) throws IOException, SQLException {
        if(param instanceof File file){
            InputStream inputStream = new FileInputStream(file);
            preStatement.setBinaryStream(paramIndex, inputStream);

            return inputStream;
        }

        return null;
    }

    private void closeInputStream(InputStream inputStream) throws IOException{
        if(inputStream != null){
            inputStream.close();
        }
    }
}
