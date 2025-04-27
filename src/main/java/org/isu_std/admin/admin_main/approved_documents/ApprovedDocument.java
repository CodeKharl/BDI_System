package org.isu_std.admin.admin_main.approved_documents;

import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;

public class ApprovedDocument {
    private final String[] APPROVE_SECTION_CONTENTS = {
            "Confirm and Export the File", "Open Approved Document File", "View Payment Method",
            "Display User Details", "Open Requirement Files", "Return to Approve Requests Selection"
    };

    private final ApprovedDocumentController approvedDocumentController;

    protected ApprovedDocument(ApprovedDocumentController approvedDocumentController){
        this.approvedDocumentController = approvedDocumentController;
    }

    public void approvedDocView(){
        while(true) {
            if (!approvedDocumentController.isThereExistingApprovedRequests()) {
                return;
            }

            if(!setApprovedDocumentChoice()){
                return;
            }

            docApprovedValidationProcess();
        }
    }

    private boolean setApprovedDocumentChoice(){
        int backValue = approvedDocumentController.getApprovedDocsCount() + 1;

        approvedDocumentController.printApprovedDocuments();
        Util.printChoices("%d. Back to Admin Menu".formatted(backValue));

        while(true){
            int docsChoice = SystemInput.getIntChoice("Enter approved document choice : ", backValue);

            if(docsChoice == backValue){
                return false;
            }

            if(approvedDocumentController.isDocumentRequestSet(docsChoice)){
                return true;
            }
        }
    }

    private void docApprovedValidationProcess(){
        while(true){
            Util.printSectionTitle(approvedDocumentController.getApprovedSectionTitle());
            Util.printChoices(APPROVE_SECTION_CONTENTS);

            int choice = SystemInput.getIntChoice("Enter your choice : ", APPROVE_SECTION_CONTENTS.length);

            if(choice == APPROVE_SECTION_CONTENTS.length){
                return;
            }

            if(approvedDocumentController.isApprovedValidatingFinished(choice)){
                return;
            }
        }
    }
}
