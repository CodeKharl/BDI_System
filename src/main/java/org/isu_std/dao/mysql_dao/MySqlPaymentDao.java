package org.isu_std.dao.mysql_dao;

import org.isu_std.dao.PaymentDao;
import org.isu_std.config.MySQLDBConfig;
import org.isu_std.dao.jdbc_helper.JDBCHelper;
import org.isu_std.io.SystemLogger;
import org.isu_std.io.custom_exception.DataAccessException;
import org.isu_std.models.Payment;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MySqlPaymentDao implements PaymentDao {
    private final JDBCHelper jdbcHelper;

    public MySqlPaymentDao(JDBCHelper jdbcHelper){
        this.jdbcHelper = jdbcHelper;
    }

    @Override
    public boolean addPayment(String referenceId, Payment payment) {
        var query = "INSERT INTO payment(reference_id, payment_type, payment_number, document_cost, payment_date_time) " +
                "VALUES(?, ?, ?, ?, ?)";

        try{
            int rowsAffected = jdbcHelper.executeUpdate(
                    query,
                    referenceId,
                    payment.paymentType(),
                    payment.paymentNumber(),
                    payment.documentCost(),
                    payment.paymentDateTime()
            );

            return rowsAffected == 1;
        }catch (SQLException | IOException e){
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<Payment> getOptionalPayment(String referenceId) {
        var query = "SELECT payment_type, payment_number, document_cost, payment_date_time " +
                "FROM payment WHERE reference_id = ?";

        try{
            return jdbcHelper.executeSingleSet(
                    query,
                    this::createPayment,
                    referenceId
            );
        }catch (SQLException | IOException e){
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    private Payment createPayment(ResultSet resultSet) throws SQLException{
        return new Payment(
                resultSet.getString(1),
                resultSet.getString(2),
                resultSet.getDouble(3),
                resultSet.getString(4)
        );
    }
}
