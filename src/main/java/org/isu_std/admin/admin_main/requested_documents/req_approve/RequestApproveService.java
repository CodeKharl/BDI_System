package org.isu_std.admin.admin_main.requested_documents.req_approve;

import org.isu_std.admin.admin_main.RequestDocumentContext;
import org.isu_std.doc_output_file_provider.DocOutFileManager;
import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.io.custom_exception.OperationFailedException;

public class RequestApproveService {
    private final DocumentRequestDao documentRequestDao;

    public RequestApproveService(DocumentRequestDao documentRequestDao){
        this.documentRequestDao = documentRequestDao;
    }

    protected void requestApprovePerformed(RequestDocumentContext requestDocumentContext)
            throws OperationFailedException{
        String referenceId = requestDocumentContext
                .documentRequest().referenceId();

        if (!documentRequestDao.requestApprove(referenceId)) {
            throw new OperationFailedException(
                    "Failed to Approve the Requested Document! Please try again."
            );
        }
    }

    protected void createOutputDocumentFile(RequestDocumentContext requestDocumentContext)
            throws OperationFailedException{
        if(!DocOutFileManager.createOutputDocumentFile(requestDocumentContext)){
            throw new OperationFailedException(
                    "Failed to Create Document Output File! Please try again."
            );
        }
    }
}
