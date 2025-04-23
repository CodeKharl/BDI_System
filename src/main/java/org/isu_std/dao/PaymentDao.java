package org.isu_std.dao;

import org.isu_std.models.Payment;

import java.util.Optional;

public interface PaymentDao {
    Optional<Payment> getOptionalPayment(String referenceId);
}
