package org.isu_std.user.user_check_request.user_payment_manage;

import org.isu_std.io.Util;
import org.isu_std.io.exception.NotFoundException;
import org.isu_std.models.Payment;
import org.isu_std.models.modelbuilders.PaymentBuilder;
import org.isu_std.user.user_check_request.ReqSelectManager;

import java.util.Optional;

public class PaymentManageController {
    private final PaymentManageService paymentManageService;
    private final ReqSelectManager reqSelectManager;

    private Payment payment;
    public PaymentManageController(PaymentManageService paymentManageService, ReqSelectManager reqSelectManager){
        this.paymentManageService = paymentManageService;
        this.reqSelectManager = reqSelectManager;
    }

    protected boolean isPaymentAlreadyExist(){
        String referenceId = reqSelectManager.getSelectedDocRequest().referenceId();
        Optional<Payment> optionalPayment = paymentManageService.getOptionalPaymentInfo(referenceId);

        if(optionalPayment.isPresent()){
            this.payment = optionalPayment.get();
            return true;
        }

        return false;
    }

    protected String paymentDetail(){
        return payment.toString();
    }

    protected String getPaymentSuccessMessage(){
        return """
                Your payment is already set.
                Kindly proceed to the barangay office to claim your document.""";
    }

    protected boolean settingUpPayment(String input){
        PaymentBuilder paymentBuilder = paymentManageService.getPaymentBuilder();

        try{
            int barangayId = reqSelectManager.getSelectedDocRequest().barangayId();
            int documentId = reqSelectManager.getSelectedDocRequest().documentId();

            double documentCost = paymentManageService.getResultedDocumentPrice(barangayId, documentId);

            paymentBuilder.paymentType(input).documentCost(documentCost)
        }catch (NotFoundException e){
            Util.printException(e.getMessage());
        }
    }
}
