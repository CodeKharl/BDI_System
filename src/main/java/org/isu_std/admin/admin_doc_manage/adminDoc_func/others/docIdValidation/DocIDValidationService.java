package org.isu_std.admin.admin_doc_manage.adminDoc_func.others.docIdValidation;

import org.isu_std.dao.DocumentDao;
import org.isu_std.io.collections.InputMessageCollection;
import org.isu_std.io.custom_exception.NotFoundException;

import java.util.Optional;

public class DocIDValidationService {
    private final DocumentDao documentRepository;

    public DocIDValidationService(DocumentDao docManageRepository){
        this.documentRepository = docManageRepository;
    }

    protected String getDocumentName(int barangayId, int documentId) {
        Optional<String> optionalDocumentName = documentRepository.getDocumentName(barangayId, documentId);
        return optionalDocumentName.orElseThrow(
                () -> new NotFoundException(
                        InputMessageCollection.INPUT_OBJECT_NOT_EXIST.getFormattedMessage("Document name")
                )
        );
    }
}
