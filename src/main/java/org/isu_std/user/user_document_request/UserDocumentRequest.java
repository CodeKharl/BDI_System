package org.isu_std.user.user_document_request;

import org.isu_std.io.collections_enum.ChoiceCollection;
import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;
import org.isu_std.user.UserProcess;

public class UserDocumentRequest implements UserProcess {
    private final UserDocRequestController userDocRequestController;

    protected UserDocumentRequest(UserDocRequestController userDocRequestController){
        this.userDocRequestController = userDocRequestController;
    }

    public void processPerformed(String processTitle){
        Util.printSectionTitle(processTitle);

        if(!userDocRequestController.setUserPersonal()){
            //message for the user to put some personal information of his/her account if not exist.
            Util.printInformation("Guide : User Menu -> Manage Account -> Personal Information");
            return;
        }

        if(!userDocRequestController.setBrgyDocs()){
            return;
        }

        documentProcess();
    }

    private void documentProcess(){
        userDocRequestController.printAvailableDocs();
        if(!setDocument()){
            return;
        }

        if(!documentInfoProcess()){
            return;
        }
        
        if(userDocRequestController.isAddDocRequestSuccess()){
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

            if(userDocRequestController.isDocumentChoiceAccepted(choice)){
                userDocRequestController.setChoiceDocument(choice);
                break;
            }
        }

        return isSelectedDocConfirm();
    }

    private boolean isSelectedDocConfirm(){
        Util.printMessage("You selected %s document"
                .formatted(userDocRequestController.getDocumentName())
        );

        return (SystemInput.isPerformConfirmed(
                "Document Choice Confirmation",
                ChoiceCollection.CONFIRM.getValue(),
                ChoiceCollection.SUB_CANCEL.getValue())
        );
    }

    private boolean documentInfoProcess(){
        if(!userDocRequestController.setDocUserRequirements()){
            return false;
        }

        userDocRequestController.printAllInformations();

        return SystemInput.isPerformConfirmed(
                "Confirm Request",
                DocRequestConfig.CONFIRM_VALUE.getCharValue(),
                DocRequestConfig.CANCEL_VALUE.getCharValue()
        );
    }
}
