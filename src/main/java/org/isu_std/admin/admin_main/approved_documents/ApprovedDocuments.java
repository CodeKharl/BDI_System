package org.isu_std.admin.admin_main.approved_documents;

import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;

public class ApprovedDocuments {
    private final String[] APPROVE_SECTION_CONTENTS = {
            "Deploy the Document File", "Check Payment Method","Open Approved Document File",
            "Display User Details", "View Requirement Files", "Return to Choose Approved Docs"
    };

    private final ApprovedController approvedController;

    protected ApprovedDocuments(ApprovedController approvedController){
        this.approvedController = approvedController;
    }

    public void approvedDocView(){
        while(true) {
            if (!approvedController.isThereExistingApprovedRequests()) {
                return;
            }

            if(!setApprovedDocumentChoice()){
                return;
            }

            docApprovedValidationProcess();
        }
    }

    private boolean setApprovedDocumentChoice(){
        int backValue = approvedController.getApprovedDocsCount() + 1;

        approvedController.printApprovedDocuments();

        while(true){
            int docsChoice = SystemInput.getIntChoice("Enter approved document choice : ", backValue);

            if(docsChoice == backValue){
                return false;
            }

            if(approvedController.isDocumentRequestSet(docsChoice)){
                return true;
            }
        }
    }

    private void docApprovedValidationProcess(){
        Util.printSectionTitle(approvedController.getApprovedSectionTitle());

        while(true){
            int choice = SystemInput.getIntChoice("Enter your choice : ", APPROVE_SECTION_CONTENTS.length);

            if(choice == APPROVE_SECTION_CONTENTS.length){
                return;
            }

            approvedController.approvedValidatingOnProcess(choice);
        }
    }
}
