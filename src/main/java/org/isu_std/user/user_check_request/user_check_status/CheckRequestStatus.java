package org.isu_std.user.user_check_request.user_check_status;

public class CheckRequestStatus {
    private final CheckReqStatusController checkReqStatusController;

    public CheckRequestStatus(CheckReqStatusController checkReqStatusController){
        this.checkReqStatusController = checkReqStatusController;
    }

    public void checkRequestStatus(){
        checkReqStatusController.printRequestStatus();
    }
}
