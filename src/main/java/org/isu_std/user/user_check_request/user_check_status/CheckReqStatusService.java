package org.isu_std.user.user_check_request.user_check_status;

import org.isu_std.dao.DocumentRequestDao;

public class CheckReqStatusService {
    private final DocumentRequestDao documentRequestDao;

    public CheckReqStatusService(DocumentRequestDao documentRequestDao){
        this.documentRequestDao = documentRequestDao;
    }

    protected boolean checkRequestedStatus(String referenceId){
        return documentRequestDao.isRequestApproved(referenceId);
    }
}
