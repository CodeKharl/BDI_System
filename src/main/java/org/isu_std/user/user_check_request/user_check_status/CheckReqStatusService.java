package org.isu_std.user.user_check_request.user_check_status;

import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.io.SystemLogger;
import org.isu_std.io.custom_exception.DataAccessException;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.user.user_check_request.CheckRequestService;

import java.util.Optional;

public class CheckReqStatusService {
    private final CheckRequestService checkRequestService;
    private final DocumentRequestDao documentRequestDao;

    public CheckReqStatusService(CheckRequestService checkRequestService, DocumentRequestDao documentRequestDao){
        this.checkRequestService = checkRequestService;
        this.documentRequestDao = documentRequestDao;
    }

    protected boolean checkRequestedStatus(String referenceId){
        return checkRequestService.isRequestApproved(referenceId);
    }
}
