package org.isu_std.user.user_check_request.user_delete_request;

import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.user.user_check_request.RequestSelectContext;

public class UserDeleteReqController {
    private final UserDeleteReqService userDeleteReqService;
    private final RequestSelectContext requestSelectContext;

    public UserDeleteReqController(UserDeleteReqService userDeleteReqService, RequestSelectContext requestSelectContext){
        this.userDeleteReqService = userDeleteReqService;
        this.requestSelectContext = requestSelectContext;
    }

    protected boolean isRequestCancellationSuccess(){
        try{
            userDeleteReqService.deleteRequestPerform(requestSelectContext);
            return true;
        }catch (ServiceException | OperationFailedException e){
            Util.printException(e.getMessage());
        }

        return false;
    }
}
