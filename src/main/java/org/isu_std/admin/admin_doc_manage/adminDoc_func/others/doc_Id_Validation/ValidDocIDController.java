package org.isu_std.admin.admin_doc_manage.adminDoc_func.others.doc_Id_Validation;

import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.NotFoundException;

public class ValidDocIDController {
    private final ValidDocIDService docIdValidService;
    private final int barangayId;

    public ValidDocIDController(ValidDocIDService docIdValidService, int barangayId){
        this.docIdValidService = docIdValidService;
        this.barangayId = barangayId;
    }

    protected boolean isDocumentExist(int documentId){
        // Checks the document id that the user input is exists on the database.
        try{
            String documentName = docIdValidService.getDocumentName(
                    this.barangayId,
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
