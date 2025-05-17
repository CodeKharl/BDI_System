package org.isu_std.admin.admin_doc_manage.adminDoc_func.others.doc_Id_Validation;

import org.isu_std.io.collections_enum.ChoiceCollection;
import org.isu_std.io.SystemInput;

// Class that manage the input of the admins for documents id. No structure

public class ValidDocIDProvider {
    private final ValidDocIDController validDocIDController;

    public ValidDocIDProvider(ValidDocIDController validDocIDController){
        this.validDocIDController = validDocIDController;
    }

    public int getValidatedId(){
        int cancellationValue = ChoiceCollection.EXIT_INT_CODE.getValue();

        while(true) {
            int documentId = SystemInput.getIntInput(
                    "Enter the Document ID (%d == Cancel) : ".formatted(cancellationValue)
            );

            if (documentId == cancellationValue) {
                return 0; // This will terminate the process.
            }

            if(validDocIDController.isDocumentExist(documentId)){
                return documentId;
            }
        }
    }
}
