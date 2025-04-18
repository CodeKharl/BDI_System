package org.isu_std.admin.requested_documents.req_approve;

import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.io.exception.OperationFailedException;

public class RequestApproveService {
    private final DocumentRequestDao documentRequestDao;

    public RequestApproveService(DocumentRequestDao documentRequestDao){
        this.documentRequestDao = documentRequestDao;
    }

    protected void requestApprovePerformed(String referenceId){
        if(!documentRequestDao.isRequestApprove(referenceId)){
            throw new IllegalArgumentException("The request has already been approve!");
        }

        if(!documentRequestDao.setRequestApprove(referenceId)){
            throw new OperationFailedException("Failed to Approve the Requested Document! Please try again.");
        }
    }
}
