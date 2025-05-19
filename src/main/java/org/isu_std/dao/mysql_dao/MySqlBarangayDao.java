package org.isu_std.dao.mysql_dao;

import org.isu_std.dao.BarangayDao;
import org.isu_std.config.MySQLDBConfig;
import org.isu_std.dao.jdbc_helper.JDBCHelper;
import org.isu_std.io.SystemLogger;
import org.isu_std.io.custom_exception.DataAccessException;
import org.isu_std.models.Barangay;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MySqlBarangayDao implements BarangayDao{
    private final JDBCHelper jdbcHelper;

    public MySqlBarangayDao(JDBCHelper jdbcHelper){
        this.jdbcHelper = jdbcHelper;
    }

    @Override
    public Optional<Integer> findBarangayId(Barangay barangay){
        String query = "SELECT barangay_id FROM barangay " +
                "WHERE barangay_name = ? " +
                "AND municipality = ? " +
                "AND province = ? LIMIT 1";

        try{
            return jdbcHelper.executeSingleSet(
                    query,
                    resultSet -> resultSet.getInt(1),
                    barangay.barangayName(),
                    barangay.municipality(),
                    barangay.province()
            );
        }catch (SQLException | IOException e){
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    @Override
    public boolean insertBarangay(Barangay barangay){
        var query = "INSERT barangay(barangay_name, municipality, province, barangay_pin) " +
                "VALUES(?, ?, ?, ?)";

        try{
            int rowsAffected = jdbcHelper.executeUpdate(
                    query,
                    barangay.barangayName(),
                    barangay.municipality(),
                    barangay.province(),
                    barangay.barangayPin()
            );

            return rowsAffected == 1;
        }catch(SQLException | IOException e){
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<Barangay> findOptionalBarangay(int barangayId) {
        String query = "SELECT barangay_name, municipality, province, barangay_pin " +
                "FROM barangay WHERE barangay_id = ? LIMIT 1";

        try{
            return jdbcHelper.executeSingleSet(
                    query,
                    resultSet -> getResultBarangay(barangayId, resultSet),
                    barangayId
            );
        }catch (SQLException | IOException e){
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    public Barangay getResultBarangay(int barangayId, ResultSet resultSet) throws SQLException{
        return new Barangay(
                barangayId,
                resultSet.getString(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getInt(4)
        );
    }
}
