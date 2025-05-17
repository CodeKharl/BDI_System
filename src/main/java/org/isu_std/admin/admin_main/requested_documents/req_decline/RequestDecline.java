package org.isu_std.admin.admin_main.requested_documents.req_decline;

import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;
import org.isu_std.io.collections_enum.ChoiceCollection;

public class RequestDecline {
    private final RequestDeclineController requestDeclineController;

    public RequestDecline(RequestDeclineController requestDeclineController){
        this.requestDeclineController = requestDeclineController;
    }

    public boolean requestDeclinePerformed(){
        if(!isDeclinedConfirmed()){
            return false;
        }

        if(requestDeclineController.isRequestRemovedSuccess()){
            Util.printMessage("The request is declined and have been removed.");
            return true;
        }

        return false;
    }

    private boolean isDeclinedConfirmed(){
        return SystemInput.isPerformConfirmed(
                "Request Decline Confirm",
                ChoiceCollection.CONFIRM.getValue(),
                ChoiceCollection.SUB_CANCEL.getValue()
        );
    }
}
