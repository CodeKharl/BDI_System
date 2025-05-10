package org.isu_std.dao.mysqldao;

import org.isu_std.dao.BarangayDao;
import org.isu_std.config.MySQLDBConfig;
import org.isu_std.io.SystemLogger;
import org.isu_std.models.Barangay;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MySqlBarangay implements BarangayDao{
    @Override
    public int getBarangayId(Barangay barangay){
        String query = "SELECT barangay_id FROM barangay " +
                "WHERE barangay_name = ? " +
                "AND municipality = ? " +
                "AND province = ? LIMIT 1";

        try(Connection connection = MySQLDBConfig.getConnection();
            PreparedStatement preStatement = connection.prepareStatement(query);
        ){
            preStatement.setString(1, barangay.barangayName());
            preStatement.setString(2, barangay.municipality());
            preStatement.setString(3, barangay.province());

          ResultSet resultSet = preStatement.executeQuery();

          if(resultSet.next()){
              return resultSet.getInt(1);
          }
        }catch (SQLException e){
            SystemLogger.logWarning(MySqlBarangay.class, e.getMessage());
        }

        return 0;
    }

    @Override
    public boolean addBarangay(Barangay barangay){
        String query = "INSERT barangay(barangay_name, municipality, province, barangay_pin) " +
                "VALUES(?, ?, ?, ?)";

        try(Connection connection = MySQLDBConfig.getConnection();
            PreparedStatement preStatement = connection.prepareStatement(query);
        ){
            preStatement.setString(1, barangay.barangayName());
            preStatement.setString(2, barangay.municipality());
            preStatement.setString(3, barangay.province());
            preStatement.setInt(4, barangay.barangayPin());

            return preStatement.executeUpdate() == 1;
        }catch(SQLException e){
            SystemLogger.logWarning(MySqlBarangay.class, e.getMessage());
        }

        return false;
    }

    @Override
    public Optional<Barangay> getOptionalBarangay(int barangayId) {
        String query = "SELECT * FROM barangay WHERE barangay_id = ? LIMIT 1";

        try(Connection connection = MySQLDBConfig.getConnection();
            PreparedStatement preStatement = connection.prepareStatement(query);
        ){
            preStatement.setInt(1, barangayId);

            ResultSet resultSet = preStatement.executeQuery();
            if(resultSet.next()){
                return Optional.of(getResultBarangay(resultSet));
            }
        }catch (SQLException e){
            SystemLogger.logWarning(MySqlBarangay.class, e.getMessage());
        }

        return Optional.empty();
    }

    public Barangay getResultBarangay(ResultSet resultSet) throws SQLException{
        return new Barangay(
                resultSet.getInt(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(4),
                resultSet.getInt(5)
        );
    }
}
