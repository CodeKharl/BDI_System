package org.isu_std.admin.admin_doc_manage.adminDoc_func.modify;

import org.isu_std.admin.admin_doc_manage.AdminDocumentImpl;
import org.isu_std.admin.admin_doc_manage.ManageDocumentUI;
import org.isu_std.admin.admin_doc_manage.adminDoc_func.others.DocumentManageCodes;
import org.isu_std.io.collections.ChoiceCollection;
import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;

/*
   Class for updating or modifying a document.
   This system support specific modification.
   Modification cannot change multiple information at once.
   Document ID cannot also be change.
*/

public class ModifyingDocument implements AdminDocumentImpl {
    private final ModifyingDocController modifyingDocController;

    public ModifyingDocument(ModifyingDocController modifyingDocController){
        this.modifyingDocController = modifyingDocController;
    }

    @Override
    public void manageProcess(){
        while(true) {
            Util.printSectionTitle("Modify Document");

            if(!modifyingDocController.setValidDocumentId()){
                return;
            }

            // Method that getting the doc info's and process it.
            modifyDocumentProcess();
        }
    }

    private void modifyDocumentProcess() {
        while(true){
            if (!setDocumentDetail()) {
                return;
            }

            if(!isModifyDocumentInfoSet(modifyingDocController.getDocumentDetail())){
                continue;
            }

            if(isModifyConfirm()){
                modifyingDocController.modifyProcess();
            }
        }
    }

    private boolean setDocumentDetail(){
        // Function that returning the specific document detail that the client wants to modify.
        Util.printSubSectionTitle("Selection of Document Information to be Modify : ");
        modifyingDocController.printDocumentInfos();

        while(true){
            int inputChoice = SystemInput.getIntInput(
                    "Enter choice (%d == cancel selecting) : ".formatted(ChoiceCollection.EXIT_INT_CODE.getIntValue())
            );

            if(inputChoice == ChoiceCollection.EXIT_INT_CODE.getIntValue()){
                return false;
            }

            if(modifyingDocController.isDocDetailAccepted(inputChoice)){
                modifyingDocController.setDocumentDetail(inputChoice);
                return true;
            }
        }
    }

    private boolean isModifyDocumentInfoSet(String documentDetail){
        String[] details = modifyingDocController.getDocumentInfos();

        if(documentDetail.equals(details[0])){
            return modifyingDocController.setDocName(getDocNameOrPrice(documentDetail));
        }

        if(documentDetail.equals(details[1])){
            return modifyingDocController.setPrice(getDocNameOrPrice(documentDetail));
        }

        if(documentDetail.equals(details[2])){
            return modifyingDocController.setRequirement();
        }

        return modifyingDocController.setDocFile();
    }

    private String getDocNameOrPrice(String documentDetail){
        while(true) {
            String input = SystemInput.getStringInput(
                    "Enter %s (%c == return to doc id section) : "
                            .formatted(documentDetail, ChoiceCollection.EXIT_CODE.getValue())
            );

            if(input.charAt(0) == ChoiceCollection.EXIT_CODE.getValue()){
                return null;
            }

            if(modifyingDocController.isDocNameOrPriceValid(documentDetail, input)){
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
