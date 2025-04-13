package org.isu_std.dao.mysqldao;

import org.isu_std.dao.AdminDao;
import org.isu_std.database.MySQLDBConnection;
import org.isu_std.io.SystemLogger;
import org.isu_std.models.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MySqlAdmin implements AdminDao {
    @Override
    public int getAdminID(String adminName) {
        String query = "SELECT admin_id FROM admin WHERE admin_name = ? LIMIT 1";

        try(Connection connection = MySQLDBConnection.getConnection();
            PreparedStatement preStatement = connection.prepareStatement(query);
        ){
            preStatement.setString(1, adminName);
            ResultSet resultSet = preStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt(1);
            }
        }catch (SQLException e){
            SystemLogger.logWarning(MySqlAdmin.class, e.getMessage());
        }

        return 0;
    }

    @Override
    public boolean insertAdmin(Admin admin){
        String query = "INSERT admin(admin_name, admin_pin) VALUES(?, ?)";

        try(Connection connection = MySQLDBConnection.getConnection();
            PreparedStatement preStatement = connection.prepareStatement(query);
        ){
            preStatement.setString(1, admin.adminName());
            preStatement.setInt(2, admin.adminPin());
            return preStatement.executeUpdate() == 1;
        }catch (SQLException e){
            SystemLogger.logWarning(MySqlAdmin.class, e.getMessage());
        }

        return false;
    }

    @Override
    public Optional<Admin> getOptionalAdmin(int adminId) {
        // Index: 0 == adminId, 1 == adminName, 2 == adminPin, 3 == barangayId
        String query = "SELECT * FROM admin WHERE admin_id = ? LIMIT 1";

        try(Connection connection = MySQLDBConnection.getConnection();
            PreparedStatement preStatement = connection.prepareStatement(query);
        ){
            preStatement.setInt(1, adminId);
            ResultSet resultSet = preStatement.executeQuery();

            if(resultSet.next()){
                return Optional.of(getResultAdmin(resultSet));
            }
        }catch (SQLException e){
            SystemLogger.logWarning(MySqlAdmin.class, e.getMessage());
        }

        return Optional.empty();
    }

    private Admin getResultAdmin(ResultSet resultSet) throws SQLException{
        return new Admin(
                resultSet.getInt(1),
                resultSet.getString(2),
                resultSet.getInt(3),
                resultSet.getInt(4)
        );
    }

    @Override
    public boolean setAdminBarangayId(int barangayId, int adminId) {
        String query = "UPDATE admin SET barangay_id = ? WHERE admin_id = ?";

        try(Connection connection = MySQLDBConnection.getConnection();
            PreparedStatement preStatement = connection.prepareStatement(query);
        ){
            preStatement.setInt(1, barangayId);
            preStatement.setInt(2, adminId);

            return preStatement.executeUpdate() == 1;
        }catch (SQLException e){
            SystemLogger.logWarning(MySqlAdmin.class, e.getMessage());
        }

        return false;
    }
}
