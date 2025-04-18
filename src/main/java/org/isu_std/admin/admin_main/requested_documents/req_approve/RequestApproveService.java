package org.isu_std.admin.admin_main.requested_documents.req_approve;

import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.io.exception.OperationFailedException;

public class RequestApproveService {
    private final DocumentRequestDao documentRequestDao;

    public RequestApproveService(DocumentRequestDao documentRequestDao){
        this.documentRequestDao = documentRequestDao;
    }

    protected void requestApprovePerformed(String referenceId){
        if(!documentRequestDao.setRequestApprove(referenceId)){
            throw new OperationFailedException("Failed to Approve the Requested Document! Please try again.");
        }
    }
}
