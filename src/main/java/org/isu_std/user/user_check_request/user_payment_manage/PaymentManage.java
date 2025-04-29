package org.isu_std.user.user_check_request.user_payment_manage;

import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;
import org.isu_std.io.collections.ChoiceCollection;

public class PaymentManage {
    private final PaymentManageController paymentManageController;

    public PaymentManage(PaymentManageController paymentManageController){
        this.paymentManageController = paymentManageController;
    }

    public void sectionPerformed(){
        Util.printSectionTitle("Payment Manage");
        Util.printMessage("Important: Payment can only be set once. Please review carefully before submitting.");

        if(paymentManageController.isPaymentAlreadyExist()){
            paymentManageController.printPaySuccessMessage();
            paymentManageController.printPaymentDetails();
            return;
        }

        if(!setPayment()){
            return;
        }

        if(paymentManageController.isAddPaymentSuccess()){
            paymentManageController.printPaySuccessMessage();
            paymentManageController.printPaymentDetails();
        }
    }

    private boolean setPayment(){
        Util.printMessage("Currently, Cash on Barangay is only available type of payment!");
        Util.printMessage("Reason : On development stage");

        String paymentType = "Cash on Barangay";
        if(isConfirmedPayment(paymentType)){
            return paymentManageController.settingUpPayment(paymentType);
        }

        return false;
    }

    private boolean isConfirmedPayment(String paymentType){
        return SystemInput.isPerformConfirmed(
                "Payment Confirmation -> %s".formatted(paymentType),
                ChoiceCollection.CONFIRM.getValue(),
                ChoiceCollection.SUB_CANCEL.getValue()
        );
    }
}
