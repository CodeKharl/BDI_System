package org.isu_std.admin.admin_main.requested_documents.req_decline;

import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.models.DocumentRequest;

public class RequestDeclineService {
    private final DocumentRequestDao documentRequestDao;

    public RequestDeclineService(DocumentRequestDao documentRequestDao){
        this.documentRequestDao = documentRequestDao;
    }

    protected void deleteRequestPerformed(DocumentRequest documentRequest) throws OperationFailedException{
        if(!documentRequestDao.deleteDocRequest(documentRequest)){
            throw new OperationFailedException("Failed to delete the request! Please try again.");
        }
    }
}
