package org.isu_std.user.user_check_request.user_delete_request;

import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.doc_output_file_provider.DocOutFileManager;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.file_setup.DocxFileManager;
import org.isu_std.models.DocumentRequest;
import org.isu_std.user.user_check_request.RequestSelectContext;

public class UserDeleteReqService {
    private final DocumentRequestDao documentRequestDao;

    public UserDeleteReqService(DocumentRequestDao documentRequestDao){
        this.documentRequestDao = documentRequestDao;
    }

    void deleteRequestPerform(RequestSelectContext requestSelectContext){
        DocumentRequest documentRequest = requestSelectContext.getSelectedDocRequest();

        if(!documentRequestDao.deleteDocRequest(documentRequest)){
            throw new OperationFailedException("Failed to delete the request! Please try to cancel it again.");
        }

        DocOutFileManager.deleteOutputDocFile(requestSelectContext);
    }
}
