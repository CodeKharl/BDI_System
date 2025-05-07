package org.isu_std.user.user_check_request.user_delete_request;

import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.models.DocumentRequest;

public class UserDeleteReqController {
    private final UserDeleteReqService userDeleteReqService;
    private final DocumentRequest selectedDocRequest;

    public UserDeleteReqController(UserDeleteReqService userDeleteReqService, DocumentRequest selectedDocRequest){
        this.userDeleteReqService = userDeleteReqService;
        this.selectedDocRequest = selectedDocRequest;
    }

    protected boolean requestCancellationProcess(){
        try{
            userDeleteReqService.deleteRequestPerform(this.selectedDocRequest);
            return true;
        }catch (OperationFailedException e){
            Util.printException(e.getMessage());
        }

        return false;
    }
}
