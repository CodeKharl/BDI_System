package org.isu_std.admin.admin_main.admin_doc_manage.adminDoc_func.modify;

import org.isu_std.admin.admin_main.admin_doc_manage.ManageDocumentImpl;
import org.isu_std.admin.admin_main.admin_doc_manage.adminDoc_func.others.DocumentManageCodes;
import org.isu_std.io.collections_enum.ChoiceCollection;
import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;

/*
   Class for updating or modifying a document.
   This system support specific modification.
   Modification cannot change multiple information at once.
   Document ID cannot also be change.
*/

public class ModifyingDocument implements ManageDocumentImpl {
    private final ModifyingDocController modifyingDocController;

    public ModifyingDocument(ModifyingDocController modifyingDocController){
        this.modifyingDocController = modifyingDocController;
    }

    @Override
    public void manageProcess(String manageTitle){
        while(true) {
            Util.printSectionTitle(manageTitle);

            if(!modifyingDocController.setValidDocumentId()){
                return;
            }

            // Method that getting the doc info's and process it.
            modifyDocumentProcess();
        }
    }

    private void modifyDocumentProcess() {
        while(true){
            if (!setChosenDocAttributeName()) {
                return;
            }

            if(!setChosenDocAttributeValue()){
                continue;
            }

            if(isModifyConfirm()){
                modifyingDocController.modifyProcess();
            }
        }
    }

    private boolean setChosenDocAttributeName(){
        // Function that returning the specific document detail that the client wants to modify.
        String[] documentAttributes = modifyingDocController.getDocAttributeNames();
        int backValue = documentAttributes.length + 1;

        Util.printSubSectionTitle("Selection of Document Information to be Modify : ");
        Util.printChoices(documentAttributes);
        Util.printChoice("%d. Cancel Process".formatted(backValue));

        int inputChoice = SystemInput.getIntChoice(
                "Enter choice : ", backValue
        );

        if(inputChoice == backValue){
            return false;
        }

        modifyingDocController.setChosenDocAttributeName(inputChoice);
        return true;
    }

    private boolean setChosenDocAttributeValue(){
        String chosenDocAttributeName = modifyingDocController.getChosenDocAttributeName();
        String[] docAttributeNames = modifyingDocController.getDocAttributeNames();

        if(chosenDocAttributeName.equals(docAttributeNames[0])){
            return modifyingDocController.setDocName(getDocNameOrPrice(chosenDocAttributeName));
        }

        if(chosenDocAttributeName.equals(docAttributeNames[1])){
            return modifyingDocController.setPrice(getDocNameOrPrice(chosenDocAttributeName));
        }

        if(chosenDocAttributeName.equals(docAttributeNames[2])){
            return modifyingDocController.setRequirement();
        }

        return modifyingDocController.setDocFile();
    }

    private String getDocNameOrPrice(String chosenDocAttributeName){
        char backValue = ChoiceCollection.EXIT_CODE.getValue();

        while(true) {
            String input = SystemInput.getStringInput(
                    "Enter %s (%c == return to doc id section) : "
                            .formatted(chosenDocAttributeName, backValue)
            );

            if(input.charAt(0) == backValue){
                return null;
            }

            if(modifyingDocController.isDocNameOrPriceValid(chosenDocAttributeName, input)){
                return input;
            }
        }
    }

    private boolean isModifyConfirm(){
        modifyingDocController.printDocDetail();

        return SystemInput.isPerformConfirmed(
                "Modify Confirmation",
                DocumentManageCodes.MODIFYING.getCode(),
                DocumentManageCodes.CANCELING.getCode()
        );
    }
}
