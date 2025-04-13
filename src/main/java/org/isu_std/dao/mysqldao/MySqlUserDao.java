package org.isu_std.dao.mysqldao;

import org.isu_std.dao.UserDao;
import org.isu_std.dao.UserPersonalDao;
import org.isu_std.database.MySQLDBConnection;
import org.isu_std.io.SystemLogger;
import org.isu_std.models.User;
import org.isu_std.models.UserPersonal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MySqlUserDao implements UserDao, UserPersonalDao {
    @Override
    public int getUserId(String username) {
        String query = "SELECT user_id FROM user WHERE user_name = ? LIMIT 1";

        try(Connection connection = MySQLDBConnection.getConnection();
            PreparedStatement preStatement = connection.prepareStatement(query);
        ){
            preStatement.setString(1, username);

            ResultSet resultSet = preStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
        }catch (SQLException e){
            SystemLogger.logWarning(MySqlUserDao.class, e.getMessage());
        }

        return 0;
    }

    @Override
    public boolean addUser(User user){
        String query = "INSERT INTO user (user_name, password, barangay_id) values(?, ?, ?)";

        try(Connection connection = MySQLDBConnection.getConnection();
            PreparedStatement preStatement = connection.prepareStatement(query);
        ){
            preStatement.setString(1, user.username());
            preStatement.setString(2, user.password());
            preStatement.setInt(3, user.barangayId());

            return preStatement.executeUpdate() == 1;
        }catch (SQLException e){
            SystemLogger.logWarning(MySqlUserDao.class, e.getMessage());
        }

        return false;
    }

    @Override
    public Optional<User> getOptionalUser(String username){
        String query = "SELECT * FROM user WHERE user_name = ? LIMIT 1";

        try(Connection connection = MySQLDBConnection.getConnection();
            PreparedStatement preStatement = connection.prepareStatement(query);
        ){
            preStatement.setString(1, username);

            ResultSet resultSet = preStatement.executeQuery();
            if(resultSet.next()){
                return Optional.of(getResultUser(resultSet));
            }
        }catch (SQLException e){
            SystemLogger.logWarning(MySqlUserDao.class, e.getMessage());
        }

        return Optional.empty();
    }

    private User getResultUser(ResultSet resultSet) throws SQLException{
        return new User(
                resultSet.getInt(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getInt(4),
                resultSet.getDouble(5)
        );
    }

    @Override
    public Optional<UserPersonal> getOptionalUserPersonal(int userId) {
        String query = "SELECT name, sex, age, birth_date, civil_status, nationality, phone_number " +
                "FROM user_personal WHERE user_id = ? LIMIT 1";

        try(Connection connection = MySQLDBConnection.getConnection();
            PreparedStatement preStatement = connection.prepareStatement(query);
        ){
            preStatement.setInt(1, userId);

            ResultSet resultSet = preStatement.executeQuery();
            if(resultSet.next()){
                return Optional.of(getResultUserPersonal(resultSet));
            }
        }catch (SQLException e){
            SystemLogger.logWarning(MySqlUserDao.class, e.getMessage());
        }

        return Optional.empty();
    }

    private UserPersonal getResultUserPersonal(ResultSet resultSet) throws SQLException{
        return new UserPersonal(
                resultSet.getString(1),
                resultSet.getString(2).charAt(0),
                resultSet.getInt(3),
                resultSet.getString(4),
                resultSet.getString(5),
                resultSet.getString(6),
                resultSet.getString(7)
        );
    }

    @Override
    public boolean addUserPersonal(int userId, UserPersonal userPersonal) {
        String query = "INSERT INTO user_personal(user_id, name, sex, age, " +
                "birth_date, civil_status, nationality, phone_number) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        try(Connection connection = MySQLDBConnection.getConnection();
            PreparedStatement preStatement = connection.prepareStatement(query);
        ){
            preStatement.setInt(1, userId);
            preStatement.setString(2, userPersonal.getName());
            preStatement.setString(3, String.valueOf(userPersonal.getSex()));
            preStatement.setInt(4, userPersonal.getAge());
            preStatement.setString(5, userPersonal.getBirthDate());
            preStatement.setString(6, userPersonal.getCivilStatus());
            preStatement.setString(7, userPersonal.getNationality());
            preStatement.setString(8, userPersonal.getPhoneNumber());

            return preStatement.executeUpdate() == 1;
        }catch (SQLException e){
            SystemLogger.logWarning(MySqlUserDao.class, e.getMessage());
        }

        return false;
    }
}
