package org.isu_std.user.user_check_request.user_check_status;

import org.isu_std.io.Util;
import org.isu_std.models.DocumentRequest;

public class CheckReqStatusController {
    private final CheckReqStatusService checkReqStatusService;
    private final DocumentRequest documentRequest;

    public CheckReqStatusController(CheckReqStatusService checkReqStatusService, DocumentRequest documentRequest){
        this.checkReqStatusService = checkReqStatusService;
        this.documentRequest = documentRequest;
    }

    protected void printRequestStatus(){
        if(checkReqStatusService.checkRequestedStatus(documentRequest.referenceId())){
            Util.printMessage("Your request has been approved!");
            Util.printMessage("You can now proceed to payment selection (Payment Manage)");
            return;
        }

        Util.printMessage(
                "Your request is in validation state! Please wait for admin approval."
        );
    }
}
