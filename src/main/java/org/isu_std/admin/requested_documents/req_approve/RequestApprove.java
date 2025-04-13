package org.isu_std.admin.requested_documents.req_approve;

import org.isu_std.io.collections.ChoiceCollection;
import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;

public class RequestApprove {
    private final RequestApproveController requestApproveController;

    public RequestApprove(RequestApproveController requestApproveController){
        this.requestApproveController = requestApproveController;
    }

    public void RequestApprovePerformed(){
        Util.printSectionTitle("Request Approve");
        if(!isConfirm()){
            return;
        }

        if(!requestApproveController.isReqApproved()){
            return;
        }

        Util.printMessage("Document request approve");
    }

    private boolean isConfirm(){
        return SystemInput.isPerformConfirmed(
                "Approve Confirmation",
                ChoiceCollection.CONFIRM.getValue(),
                ChoiceCollection.SUB_CANCEL.getValue()
        );
    }
}
