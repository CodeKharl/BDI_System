package org.isu_std.user.user_check_request.user_check_status;

import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.models.DocumentRequest;

public class CheckReqStatusController {
    private final CheckReqStatusService checkReqStatusService;
    private final DocumentRequest documentRequest;

    public CheckReqStatusController(CheckReqStatusService checkReqStatusService, DocumentRequest documentRequest){
        this.checkReqStatusService = checkReqStatusService;
        this.documentRequest = documentRequest;
    }

    protected boolean isRequestApproved(){
        try {
            return checkReqStatusService.checkRequestedStatus(documentRequest.referenceId());
        }catch (ServiceException | NotFoundException e){
            Util.printException(e.getMessage());
        }

        return false;
    }
}
