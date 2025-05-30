package org.isu_std.admin.admin_main.admin_requested_documents.req_decline;

import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.io.SystemLogger;
import org.isu_std.io.custom_exception.DataAccessException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.models.DocumentRequest;

public class RequestDeclineService {
    private final DocumentRequestDao documentRequestDao;

    public RequestDeclineService(DocumentRequestDao documentRequestDao){
        this.documentRequestDao = documentRequestDao;
    }

    protected void deleteRequestPerformed(DocumentRequest documentRequest){
        try {
            String referenceId = documentRequest.referenceId();

            if (!documentRequestDao.deleteDocRequest(referenceId)) {
                throw new OperationFailedException("Failed to delete the request! Please try again.");
            }
        }catch (DataAccessException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException("Failed to delete request with : " + documentRequest);
        }
    }
}
