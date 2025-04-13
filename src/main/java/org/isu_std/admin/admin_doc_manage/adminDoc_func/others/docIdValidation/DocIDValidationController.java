package org.isu_std.admin.admin_doc_manage.adminDoc_func.others.docIdValidation;

import org.isu_std.io.Util;
import org.isu_std.io.collections.ChoiceCollection;
import org.isu_std.io.exception.NotFoundException;

import java.util.concurrent.CancellationException;

public class DocIDValidationController {
    private final DocIDValidationService docIdValidService;

    public DocIDValidationController(DocIDValidationService docIdValidService){
        this.docIdValidService = docIdValidService;
    }

    protected final boolean isDocumentExist(int barangayId, int documentId){
        // Checks the document id that the user input is exists on the database.
        try{
            String documentName = docIdValidService.getDocumentName(
                    barangayId,
                    documentId
            );

            Util.printInformation("Document Name : " + documentName);
            return true;
        }catch (NotFoundException e){
            Util.printException(e.getMessage());
        }

        return false;
    }
}
