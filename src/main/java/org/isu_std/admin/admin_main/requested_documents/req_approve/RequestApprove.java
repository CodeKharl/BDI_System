package org.isu_std.admin.admin_main.requested_documents.req_approve;

import org.isu_std.io.collections_enum.ChoiceCollection;
import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;

public class RequestApprove {
    private final RequestApproveController requestApproveController;

    public RequestApprove(RequestApproveController requestApproveController){
        this.requestApproveController = requestApproveController;
    }

    public boolean requestApprovePerformed(){
        Util.printSectionTitle("Request Approve");
        if(!isConfirm()){
            return false;
        }

        if(!requestApproveController.isReqApproved()){
            return false;
        }

        Util.printMessage("Document request approve!");
        return true;
    }

    private boolean isConfirm(){
        return SystemInput.isPerformConfirmed(
                "Approve Confirmation",
                ChoiceCollection.CONFIRM.getValue(),
                ChoiceCollection.SUB_CANCEL.getValue()
        );
    }
}
