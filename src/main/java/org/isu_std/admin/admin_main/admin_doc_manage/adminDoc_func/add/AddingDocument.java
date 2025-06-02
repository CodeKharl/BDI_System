package org.isu_std.admin.admin_main.admin_doc_manage.adminDoc_func.add;

import org.isu_std.admin.admin_main.admin_doc_manage.ManageDocumentImpl;
import org.isu_std.admin.admin_main.admin_doc_manage.adminDoc_func.others.DocumentManageCodes;
import org.isu_std.io.collections_enum.ChoiceCollection;
import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;

public class AddingDocument implements ManageDocumentImpl {
    private final AddingDocController addingDocController;

    public AddingDocument(AddingDocController addingDocController){
        this.addingDocController = addingDocController;
    }

    @Override
    public void manageProcess(String manageTitle){
        while(true) {
            Util.printSectionTitle(
                    "%s : (%c == Back to Manage Document Menu)"
                            .formatted(manageTitle, ChoiceCollection.EXIT_CODE.getValue()
                    )
            );

            if(!setDocumentInformation()){
                return;
            }

            if (isDocumentConfirm()) {
                addingDocController.processDocument();
            }
        }
    }

    private boolean setDocumentInformation(){
        String[] docInfoArr = addingDocController.getDocumentAttributeNames();
        // Set only the document name and price.

        int count = 0;
        while(count < docInfoArr.length - 2){
            String input = SystemInput.
                    getStringInput("Enter the %s : ".formatted(docInfoArr[count]));

            if(input.charAt(0) == ChoiceCollection.EXIT_CODE.getValue()){
                return false;
            }

            if(addingDocController.setDocumentInformation(count, input)){
                count++;
            }
        }

        return setDocReqFile();
    }

    private boolean setDocReqFile(){
        // Function that sets contains the requirements and files.
        if(!addingDocController.setDocumentRequirements()){
            return false;
        }

        return addingDocController.setDocumentFile();
    }

    private boolean isDocumentConfirm(){
        Util.printSectionTitle("Document Confirmation");
        addingDocController.printDocument();

        return SystemInput.isPerformConfirmed(
                "Add Confirmation",
                DocumentManageCodes.ADDING.getCode(),
                DocumentManageCodes.CANCELING.getCode()
        );
    }
}
