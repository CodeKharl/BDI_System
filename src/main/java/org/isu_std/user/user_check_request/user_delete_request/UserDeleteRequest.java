package org.isu_std.user.user_check_request.user_delete_request;

import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;
import org.isu_std.io.collections_enum.ChoiceCollection;

public class UserDeleteRequest {
    private final UserDeleteReqController userDeleteReqController;

    public UserDeleteRequest(UserDeleteReqController userDeleteReqController){
        this.userDeleteReqController = userDeleteReqController;
    }

    public boolean requestDeletePerform(){
        boolean isDeleteProcessSuccess = isRequestCancellationConfirmed() &&
                userDeleteReqController.isRequestCancellationSuccess();

        if(isDeleteProcessSuccess){
            Util.printMessage("Request deletion completed successfully.");
            return true;
        }

        return false;
    }

    private boolean isRequestCancellationConfirmed(){
        return SystemInput.isPerformConfirmed(
                "Cancellation Request Confirm",
                ChoiceCollection.CONFIRM.getValue(),
                ChoiceCollection.EXIT_CODE.getValue()
        );
    }
}
