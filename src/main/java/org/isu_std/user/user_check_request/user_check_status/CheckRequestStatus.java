package org.isu_std.user.user_check_request.user_check_status;

import org.isu_std.io.Util;

public class CheckRequestStatus {
    private final CheckReqStatusController checkReqStatusController;

    public CheckRequestStatus(CheckReqStatusController checkReqStatusController){
        this.checkReqStatusController = checkReqStatusController;
    }

    public void checkRequestStatus(){
        if(checkReqStatusController.isRequestApproved()){
            Util.printMessage("Your request has been approved!");
            Util.printMessage("You can now proceed to payment selection (Payment Manage)");

            return;
        }

        Util.printMessage(
                "Your request is in validation state! Please wait for admin approval."
        );
    }
}
