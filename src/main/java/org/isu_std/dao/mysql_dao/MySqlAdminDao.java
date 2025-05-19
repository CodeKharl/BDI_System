package org.isu_std.dao.mysql_dao;

import org.isu_std.dao.AdminDao;
import org.isu_std.config.MySQLDBConfig;
import org.isu_std.dao.jdbc_helper.JDBCHelper;
import org.isu_std.io.SystemLogger;
import org.isu_std.io.custom_exception.DataAccessException;
import org.isu_std.models.Admin;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MySqlAdminDao implements AdminDao {
    private final JDBCHelper jdbcHelper;

    public MySqlAdminDao(JDBCHelper jdbcHelper){
        this.jdbcHelper = jdbcHelper;
    }

    @Override
    public Optional<Integer> findAdminIDByName(String adminName) {
        String query = "SELECT admin_id FROM admin WHERE admin_name = ? LIMIT 1";

        try {
            return jdbcHelper.executeSingleSet(
                    query,
                    resultSet -> resultSet.getInt(1),
                    adminName
            );
        }catch (SQLException | IOException e){
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    @Override
    public boolean insertAdmin(Admin admin){
        String query = "INSERT admin(admin_name, admin_pin) VALUES(?, ?)";

        try{
            int rowsAffected = jdbcHelper.executeUpdate(
                    query,
                    admin.adminName(),
                    admin.adminPin()
            );

            return rowsAffected == 1;
        }catch (SQLException | IOException e){
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<Admin> findOptionalAdmin(int adminId) {
        // Index: 0 == adminId, 1 == adminName, 2 == adminPin, 3 == barangayId
        String query = "SELECT admin_name, admin_pin, barangay_id FROM admin WHERE admin_id = ? LIMIT 1";

        try{
            return jdbcHelper.executeSingleSet(
                    query,
                    resultSet -> createResultAdmin(adminId, resultSet),
                    adminId
            );
        }catch (SQLException | IOException e){
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    private Admin createResultAdmin(int adminId, ResultSet resultSet) throws SQLException{
        return new Admin(
                adminId,
                resultSet.getString(1),
                resultSet.getInt(2),
                resultSet.getInt(3)
        );
    }

    @Override
    public boolean updateAdminBrgyId(int barangayId, int adminId) {
        String query = "UPDATE admin SET barangay_id = ? WHERE admin_id = ?";

        try{
            int rowsAffected = jdbcHelper.executeUpdate(query, barangayId, adminId);

            return rowsAffected == 1;
        }catch (SQLException | IOException e){
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
