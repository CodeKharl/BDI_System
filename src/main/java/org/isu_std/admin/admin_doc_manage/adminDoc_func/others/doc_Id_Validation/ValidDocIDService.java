package org.isu_std.admin.admin_doc_manage.adminDoc_func.others.doc_Id_Validation;

import org.isu_std.dao.DocumentDao;
import org.isu_std.io.collections_enum.InputMessageCollection;
import org.isu_std.io.custom_exception.NotFoundException;

import java.util.Optional;

public class ValidDocIDService {
    private final DocumentDao documentDao;

    public ValidDocIDService(DocumentDao documentDao){
        this.documentDao = documentDao;
    }

    protected String getDocumentName(int barangayId, int documentId){
        Optional<String> optionalDocumentName = documentDao
                .getDocumentName(barangayId, documentId);

        return optionalDocumentName.orElseThrow(
                () -> new NotFoundException(
                        InputMessageCollection.INPUT_OBJECT_NOT_EXIST.getFormattedMessage("Document name")
                )
        );
    }
}
