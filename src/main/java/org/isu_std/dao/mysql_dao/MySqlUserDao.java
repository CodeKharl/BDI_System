package org.isu_std.dao.mysql_dao;

import org.isu_std.dao.UserDao;
import org.isu_std.dao.UserPersonalDao;
import org.isu_std.config.MySQLDBConfig;
import org.isu_std.dao.jdbc_helper.JDBCHelper;
import org.isu_std.io.SystemLogger;
import org.isu_std.io.custom_exception.DataAccessException;
import org.isu_std.models.User;
import org.isu_std.models.UserPersonal;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MySqlUserDao implements UserDao, UserPersonalDao {
    private final JDBCHelper jdbcHelper;

    public MySqlUserDao(JDBCHelper jdbcHelper){
        this.jdbcHelper = jdbcHelper;
    }

    @Override
    public Optional<Integer> getUserId(String username) {
        var query = "SELECT user_id FROM user WHERE username = ? LIMIT 1";

        try{
            return jdbcHelper.executeSingleSet(
                    query,
                    resultSet -> resultSet.getInt(1),
                    username
            );
        }catch (SQLException | IOException e){
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    @Override
    public boolean addUser(User user){
        var query = "INSERT INTO user (username, password, barangay_id) values(?, ?, ?)";

        try{
            int affectedRows = jdbcHelper.executeUpdate(
                    query,
                    user.username(),
                    user.password(),
                    user.barangayId()
            );

            return affectedRows == 1;
        }catch (SQLException | IOException e){
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<User> getOptionalUser(String username){
        var query = "SELECT * FROM user WHERE username = ? LIMIT 1";

        try{
            return jdbcHelper.executeSingleSet(
                    query,
                    this::getResultUser,
                    username
            );
        }catch (SQLException | IOException e){
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    private User getResultUser(ResultSet resultSet) throws SQLException{
        return new User(
                resultSet.getInt(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getInt(4)
        );
    }

    @Override
    public Optional<UserPersonal> getOptionalUserPersonal(int userId) {
        var query = "SELECT name, sex, age, birth_date, birth_place, civil_status, nationality, phone_number " +
                "FROM user_personal WHERE user_id = ? LIMIT 1";

        try{
            return jdbcHelper.executeSingleSet(
                    query,
                    this::getResultUserPersonal,
                    userId
            );
        }catch (SQLException | IOException e){
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    private UserPersonal getResultUserPersonal(ResultSet resultSet) throws SQLException{
        return new UserPersonal(
                resultSet.getString(1),
                resultSet.getString(2).charAt(0),
                resultSet.getInt(3),
                resultSet.getString(4),
                resultSet.getString(5),
                resultSet.getString(6),
                resultSet.getString(7),
                resultSet.getString(8)
        );
    }

    @Override
    public boolean addUserPersonal(int userId, UserPersonal userPersonal) {
        var query = "INSERT INTO user_personal(user_id, name, sex, age, " +
                "birth_date, birth_place, civil_status, nationality, phone_number) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try{
            int rowsAffected = jdbcHelper.executeUpdate(
                    query,
                    userId,
                    userPersonal.name(),
                    userPersonal.sex(),
                    userPersonal.age(),
                    userPersonal.birthDate(),
                    userPersonal.birthPlace(),
                    userPersonal.civilStatus(),
                    userPersonal.nationality(),
                    userPersonal.phoneNumber()
            );

            return rowsAffected == 1;
        }catch (SQLException | IOException e){
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    @Override
    public boolean modifyUserPersonal(int userId, String chosenDetail, UserPersonal userPersonal){
        var query = getModifyQuery(chosenDetail);

        try{
            int rowsAffected = jdbcHelper.executeUpdate(
                    query,
                    userPersonal.name(),
                    userPersonal.sex(),
                    userPersonal.age(),
                    userPersonal.birthDate(),
                    userPersonal.birthPlace(),
                    userPersonal.civilStatus(),
                    userPersonal.nationality(),
                    userPersonal.phoneNumber(),
                    userId
            );

            return rowsAffected == 1;
        }catch(SQLException | IOException e){
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    private String getModifyQuery(String chosenDetail){
        return "UPDATE user_personal SET %s = ? WHERE user_id = ?"
                .formatted(chosenDetail);
    }

    @Override
    public boolean updateUserBarangay(User newUser){
        String query = "UPDATE user SET barangay_id = ? WHERE user_id = ?";

        try{
            int rowsAffected = jdbcHelper.executeUpdate(
                    query,
                    newUser.barangayId(),
                    newUser.userId()
            );

            return rowsAffected == 1;
        }catch (SQLException | IOException e){
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    @Override
    public boolean updateUserInfo(String chosenDetail, User user){
        String query = getUpdateDetailQuery(chosenDetail);

        try{
            int rowsAffected = jdbcHelper.executeUpdate(
                    query,
                    user.username(),
                    user.password()
            );

            return rowsAffected == 1;
        }catch (SQLException | IOException e){
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    private String getUpdateDetailQuery(String chosenDetail){
        return "UPDATE user SET %s = ? WHERE user_id = ?".formatted(chosenDetail);
    }
}
