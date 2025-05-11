package org.isu_std.user.user_check_request.user_delete_request;

import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.dao.PaymentDao;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.models.DocumentRequest;

public class UserDeleteReqService {
    private final DocumentRequestDao documentRequestDao;

    public UserDeleteReqService(DocumentRequestDao documentRequestDao){
        this.documentRequestDao = documentRequestDao;
    }

    protected void deleteRequestPerform(DocumentRequest documentRequest){
        if(!documentRequestDao.deleteDocRequest(documentRequest)){
            throw new OperationFailedException("Failed to delete the request! Please try to cancel it again.");
        }
    }
}
