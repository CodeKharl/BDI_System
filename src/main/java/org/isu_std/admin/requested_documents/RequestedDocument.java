package org.isu_std.admin.requested_documents;

import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;

public class RequestedDocument {
    private final RequestedDocumentController reqDocController;
    private final String[] REQUEST_VIEW_CONTENTS = {
            "Approve the Request", "Display Requested Document",
            "Display User Details", "View Requirement Files", "Back to Pending Document Requests"
    };

    protected RequestedDocument(RequestedDocumentController reqDocController){
        this.reqDocController = reqDocController;
    }

    public void requestedDocSection(){
        while(true){
            if (!reqDocController.isThereExistingRequest()) {
                return;
            }

            if(!setRequestedDocChoice()) {
                return;
            }

            docRequestProcess();
        }
    }

    private boolean setRequestedDocChoice(){
        int backLengthValue = reqDocController.getReqDocListLength() + 1;
        reqDocController.printRequestedDocs();
        Util.printChoices(
                "%d. Back to Menu.".formatted(backLengthValue)
        );

        while(true){
            int docChoice = SystemInput.getIntChoice("Enter Document No. you want to view : ", backLengthValue);

            if(docChoice == backLengthValue){ // Back Statement
                return false;
            }

            if(reqDocController.setDocumentReqChoice(docChoice)){
                return true;
            }
        }
    }

    private void docRequestProcess(){
        while(true){
            Util.printSectionTitle(reqDocController.getDocReqSubTitle());
            Util.printChoices(REQUEST_VIEW_CONTENTS);

            int choice = SystemInput.getIntChoice("Enter your choice : ", REQUEST_VIEW_CONTENTS.length);

            if(choice == REQUEST_VIEW_CONTENTS.length){
                return;
            }

            if(reqDocController.isRequestFinishProcess(choice)){
                return;
            }
        }
    }
}
