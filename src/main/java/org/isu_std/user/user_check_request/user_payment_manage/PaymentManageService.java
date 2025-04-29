package org.isu_std.user.user_check_request.user_payment_manage;

import org.isu_std.dao.DocumentDao;
import org.isu_std.dao.PaymentDao;
import org.isu_std.io.DateTime;
import org.isu_std.io.exception.NotFoundException;
import org.isu_std.io.exception.OperationFailedException;
import org.isu_std.models.Payment;
import org.isu_std.models.modelbuilders.BuilderFactory;
import org.isu_std.models.modelbuilders.PaymentBuilder;

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
        double documentPrice = documentDao.getDocumentPrice(barangayId, documentId);

        if(documentPrice == 0){
            throw new NotFoundException("The document price is not found! Please report this to the admin");
        }

        return documentPrice;
    }

    protected String getPaymentTime(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yy-MM-dd hh:mm:ss");
        return DateTime.localTimeStr(dateTimeFormatter);
    }

    protected void addPaymentPerformed(String referenceId, Payment payment){
        if(!paymentDao.addPayment(referenceId, payment)){
            throw new OperationFailedException("Failed to add the payment informations! Please try again!");
        }
    }
}
