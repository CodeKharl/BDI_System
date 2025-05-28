package org.isu_std.admin.admin_main.requested_documents.req_approve;

import org.isu_std.admin.admin_main.RequestDocumentContext;
import org.isu_std.doc_output_file_manager.DocOutFileManager;
import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.io.SystemLogger;
import org.isu_std.io.custom_exception.DataAccessException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.custom_exception.ServiceException;

import java.io.IOException;

public class RequestApproveService {
    private final DocumentRequestDao documentRequestDao;

    public RequestApproveService(DocumentRequestDao documentRequestDao){
        this.documentRequestDao = documentRequestDao;
    }

    protected void requestApprovePerformed(RequestDocumentContext requestDocumentContext){
        String referenceId = requestDocumentContext
                .documentRequest()
                .referenceId();

        try {
            if (!documentRequestDao.requestApprove(referenceId)) {
                throw new OperationFailedException("Failed to Approve the Requested Document! Please try again.");
            }
        }catch (DataAccessException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException("Failed to update request status with : " + requestDocumentContext);
        }
    }

    protected void createOutputDocumentFile(RequestDocumentContext requestDocumentContext) {
        try {
            DocOutFileManager.createOutputDocumentFile(requestDocumentContext);
        }catch (IOException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException("Failed to create document output file with : " + requestDocumentContext);
        }
    }
}
