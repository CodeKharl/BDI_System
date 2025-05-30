package org.isu_std.admin.admin_main.admin_doc_manage.adminDoc_func.others.doc_Id_Validation;

import org.isu_std.dao.DocumentDao;
import org.isu_std.io.SystemLogger;
import org.isu_std.io.collections_enum.InputMessageCollection;
import org.isu_std.io.custom_exception.DataAccessException;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.ServiceException;

import java.util.Optional;

public class ValidDocIDService {
    private final DocumentDao documentDao;

    public ValidDocIDService(DocumentDao documentDao){
        this.documentDao = documentDao;
    }

    protected String getDocumentName(int barangayId, int documentId){
        try {
            Optional<String> optionalDocumentName = documentDao
                    .findDocumentName(barangayId, documentId);

            return optionalDocumentName.orElseThrow(
                    () -> new NotFoundException(
                            InputMessageCollection.INPUT_OBJECT_NOT_EXIST.getFormattedMessage("Document name")
                    )
            );
        }catch (DataAccessException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException("Failed to fetch document name with document ID : " + documentId);
        }
    }
}
