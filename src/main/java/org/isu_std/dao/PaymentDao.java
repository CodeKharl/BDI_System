package org.isu_std.dao;

import org.isu_std.models.Payment;

import java.util.Optional;

public interface PaymentDao {
    boolean addPayment(String referenceId, Payment payment);
    Optional<Payment> getOptionalPayment(String referenceId);
    boolean deletePayment(String referenceId);
}
