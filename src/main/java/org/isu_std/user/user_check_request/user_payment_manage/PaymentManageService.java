package org.isu_std.user.user_check_request.user_payment_manage;

import org.isu_std.dao.DocumentDao;
import org.isu_std.dao.PaymentDao;
import org.isu_std.io.DateTime;
import org.isu_std.io.SystemLogger;
import org.isu_std.io.custom_exception.DataAccessException;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.models.Payment;
import org.isu_std.models.model_builders.BuilderFactory;
import org.isu_std.models.model_builders.PaymentBuilder;

import javax.sound.midi.SysexMessage;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class PaymentManageService {
    private final PaymentDao paymentDao;
    private final DocumentDao documentDao;

    public PaymentManageService(PaymentDao paymentDao, DocumentDao documentDao){
        this.paymentDao = paymentDao;
        this.documentDao = documentDao;
    }

    protected Optional<Payment> getOptionalPaymentInfo(String referenceId){
        return paymentDao.getOptionalPayment(referenceId);
    }

    protected PaymentBuilder getPaymentBuilder(){
        return BuilderFactory.createPaymentBuilder();
    }

    protected double getResultedDocumentPrice(int barangayId, int documentId){
        try {
            Optional<Double> documentPrice = documentDao.findDocumentPrice(barangayId, documentId);

            return documentPrice.orElseThrow(
                    () -> new NotFoundException("The document price is not found! Please report this to the admin")
            );
        }catch (DataAccessException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException("Failed to fetch document price by document_id : " + documentId);
        }
    }

    protected String getPaymentTime(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yy-MM-dd hh:mm:ss");
        return DateTime.localDateTimeStr(dateTimeFormatter);
    }

    protected void addPaymentPerformed(String referenceId, Payment payment) throws OperationFailedException{
        try {
            if (!paymentDao.addPayment(referenceId, payment)) {
                throw new OperationFailedException(
                        "Failed to add the payment informations! Please try again!"
                );
            }
        }catch (DataAccessException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException("Failed to insert payment with reference_id : " + referenceId);
        }
    }
}
