package org.isu_std.admin.admin_doc_manage.adminDoc_func.others.docIdValidation;

import org.isu_std.io.collections.ChoiceCollection;
import org.isu_std.io.SystemInput;

import java.util.concurrent.CancellationException;

// Class that manage the input of the admins for documents id. No structure

public class DocIDValidation {
    private final DocIDValidationController docIDValidationController;

    public DocIDValidation(DocIDValidationController docIDValidationController){
        this.docIDValidationController = docIDValidationController;
    }

    public int getInputDocumentId(int barangayId){
        while(true) {
            int documentId = SystemInput.getIntInput(
                    "Enter the Document ID (%d == Cancel) : ".formatted(ChoiceCollection.EXIT_INT_CODE.getIntValue())
            );

            if (documentId == ChoiceCollection.EXIT_INT_CODE.getIntValue()) {
                return 0; // This will terminate the process.
            }

            if(docIDValidationController.isDocumentExist(barangayId, documentId)){
                return documentId;
            }
        }
    }
}
