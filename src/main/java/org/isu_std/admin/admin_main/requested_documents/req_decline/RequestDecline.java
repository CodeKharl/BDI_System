package org.isu_std.admin.admin_main.requested_documents.req_decline;

import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;
import org.isu_std.io.collections.ChoiceCollection;

public class RequestDecline {
    private final RequestDeclineController requestDeclineController;

    public RequestDecline(RequestDeclineController requestDeclineController){
        this.requestDeclineController = requestDeclineController;
    }

    public void requestDeclinePerformed(){
        if(!isDeclinedConfirmed()){
            return;
        }

        if(requestDeclineController.isRequestRemovedSuccess()){
            Util.printMessage("The request is declined and have been removed.");
        }
    }

    private boolean isDeclinedConfirmed(){
        return SystemInput.isPerformConfirmed(
                "Request Decline Confirm",
                ChoiceCollection.CONFIRM.getValue(),
                ChoiceCollection.SUB_CANCEL.getValue()
        );
    }
}
