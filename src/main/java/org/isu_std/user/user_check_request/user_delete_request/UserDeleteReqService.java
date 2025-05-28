package org.isu_std.user.user_check_request.user_delete_request;

import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.doc_output_file_manager.DocOutFileManager;
import org.isu_std.io.SystemLogger;
import org.isu_std.io.custom_exception.DataAccessException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.models.DocumentRequest;
import org.isu_std.user.user_check_request.RequestSelectContext;

public class UserDeleteReqService {
    private final DocumentRequestDao documentRequestDao;

    public UserDeleteReqService(DocumentRequestDao documentRequestDao){
        this.documentRequestDao = documentRequestDao;
    }

    protected void deleteRequestPerform(RequestSelectContext requestSelectContext){
        String referenceId = requestSelectContext.getSelectedDocRequest().referenceId();

        try {
            if (documentRequestDao.deleteDocRequest(referenceId)) {
                DocOutFileManager.deleteOutputDocFile(requestSelectContext);
            }

            throw new OperationFailedException(
                    "Failed to delete the request! Please try to cancel it again."
            );
        }catch (DataAccessException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException("Failed to delete request : " + requestSelectContext);
        }
    }
}
