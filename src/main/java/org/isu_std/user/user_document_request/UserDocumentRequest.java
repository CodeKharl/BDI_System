package org.isu_std.user.user_document_request;

import org.isu_std.io.collections.ChoiceCollection;
import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;
import org.isu_std.user.UserProcess;

public class UserDocumentRequest implements UserProcess {
    private final UserDocumentRequestController userDocumentRequestController;

    protected UserDocumentRequest(UserDocumentRequestController userDocumentRequestController){
        this.userDocumentRequestController = userDocumentRequestController;
    }

    public void processPerformed(String processTitle){
        Util.printSectionTitle(processTitle);

        if(!userDocumentRequestController.setBrgyDocs()){
            return;
        }

        documentProcess();
    }

    private void documentProcess(){
        userDocumentRequestController.printAvailableDocs();
        if(!setDocument()){
            return;
        }

        if(!documentInfoProcess()){
            return;
        }
        
        if(userDocumentRequestController.isAddDocRequestSuccess()){
            Util.printMessage(
                    "Your document request has been process! Please wait for the approval."
            );
        }
    }

    private boolean setDocument() {
        while(true){
            int choice = SystemInput.getIntInput(
                    "Enter choice (%d == cancel) : ".formatted(ChoiceCollection.EXIT_INT_CODE.getIntValue())
            );

            if (choice == ChoiceCollection.EXIT_INT_CODE.getIntValue()) {
                return false;
            }

            if(userDocumentRequestController.isDocumentChoiceAccepted(choice)){
                userDocumentRequestController.setChoiceDocument(choice);
                break;
            }
        }

        return isSelectedDocConfirm();
    }

    private boolean isSelectedDocConfirm(){
        Util.printMessage("You selected %s document"
                .formatted(userDocumentRequestController.getDocumentName())
        );

        return (SystemInput.isPerformConfirmed(
                "Document Choice Confirmation",
                ChoiceCollection.CONFIRM.getValue(),
                ChoiceCollection.SUB_CANCEL.getValue())
        );
    }

    private boolean documentInfoProcess(){
        if(!userDocumentRequestController.setDocUserRequirements()){
            return false;
        }

        userDocumentRequestController.printAllInformations();

        return SystemInput.isPerformConfirmed(
                "Confirm Request",
                DocRequestConfig.CONFIRM_VALUE.getCharValue(),
                DocRequestConfig.CANCEL_VALUE.getCharValue()
        );
    }
}
