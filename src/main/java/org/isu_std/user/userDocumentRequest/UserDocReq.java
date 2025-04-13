package org.isu_std.user.userDocumentRequest;

import org.isu_std.io.collections.ChoiceCollection;
import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;
import org.isu_std.user.UserUI;

public class UserDocReq {
    private final UserDocReqController userDocReqController;

    protected UserDocReq(UserDocReqController userDocReqController){
        this.userDocReqController = userDocReqController;
    }

    public void requestDocument(){
        Util.printSectionTitle(UserUI.getContent(0));

        if(!userDocReqController.setBrgyDocs()){
            return;
        }

        documentProcess();
    }

    private void documentProcess(){
        userDocReqController.printAvailableDocs();
        if(!setDocument()){
            return;
        }

        if(!documentInfoProcess()){
            return;
        }
        
        if(userDocReqController.isAddDocRequestSuccess()){
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

            if(userDocReqController.isDocumentChoiceAccepted(choice)){
                userDocReqController.setChoiceDocument(choice);
                break;
            }
        }

        return isSelectedDocConfirm();
    }

    private boolean isSelectedDocConfirm(){
        Util.printMessage("You selected %s document"
                .formatted(userDocReqController.getDocumentName())
        );

        return (SystemInput.isPerformConfirmed(
                "Document Choice Confirmation",
                ChoiceCollection.CONFIRM.getValue(),
                ChoiceCollection.SUB_CANCEL.getValue())
        );
    }

    private boolean documentInfoProcess(){
        if(!userDocReqController.setDocUserRequirements()){
            return false;
        }

        userDocReqController.printAllInformations();

        return SystemInput.isPerformConfirmed(
                "Confirm Request",
                DocRequestConfig.CONFIRM_VALUE.getCharValue(),
                DocRequestConfig.CANCEL_VALUE.getCharValue()
        );
    }
}
