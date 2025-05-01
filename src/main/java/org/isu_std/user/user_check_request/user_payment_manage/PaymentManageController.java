package org.isu_std.user.user_check_request.user_payment_manage;

import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.models.Payment;
import org.isu_std.models.modelbuilders.PaymentBuilder;
import org.isu_std.user.user_check_request.RequestSelectContext;

import java.util.Optional;

public class PaymentManageController {
    private final PaymentManageService paymentManageService;
    private final RequestSelectContext requestSelectContext;

    private final PaymentBuilder paymentBuilder;
    public PaymentManageController(PaymentManageService paymentManageService, RequestSelectContext requestSelectContext){
        this.paymentManageService = paymentManageService;
        this.requestSelectContext = requestSelectContext;
        this.paymentBuilder = paymentManageService.getPaymentBuilder();
    }

    protected boolean isPaymentAlreadyExist(){
        String referenceId = requestSelectContext.getSelectedDocRequest().referenceId();
        Optional<Payment> optionalPayment = paymentManageService.getOptionalPaymentInfo(referenceId);

        if(optionalPayment.isPresent()){
            this.paymentBuilder.setPayment(optionalPayment.get());
            return true;
        }

        return false;
    }

    protected boolean settingUpPayment(String input){
        try{
            int barangayId = requestSelectContext.getSelectedDocRequest().barangayId();
            int documentId = requestSelectContext.getSelectedDocRequest().documentId();

            double documentCost = paymentManageService.getResultedDocumentPrice(barangayId, documentId);
            String paymentDateTime = paymentManageService.getPaymentTime();

            paymentBuilder
                    .paymentType(input)
                    .paymentNumber("0")
                    .documentCost(documentCost)
                    .paymentDateTime(paymentDateTime);

            return true;
        }catch (NotFoundException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected boolean isAddPaymentSuccess(){
        try{
            String referenceId = requestSelectContext.getSelectedDocRequest().referenceId();
            Payment payment = paymentBuilder.build();
            paymentManageService.addPaymentPerformed(referenceId, payment);

            return true;
        }catch (OperationFailedException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    private String getReferenceId(){
        return "REFERENCE ID : " + requestSelectContext.getSelectedDocRequest().referenceId();
    }

    protected void printPaymentDetails(){
        paymentBuilder.printPaymentDetails();
        Util.printInformation(getReferenceId());
    }

    protected void printPaySuccessMessage(){
        Util.printMessage("Your payment is already set.");
        Util.printMessage("Kindly proceed to the barangay office and claim your document using the REFERENCE ID.");
        Util.printMessage("Bring also some proof for further validation.");
    }
}