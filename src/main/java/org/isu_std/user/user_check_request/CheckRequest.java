package org.isu_std.user.user_check_request;

import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;
import org.isu_std.io.collections.ChoiceCollection;
import org.isu_std.user.UserProcess;

public class CheckRequest implements UserProcess {
    private final String[] CHECK_REQUEST_CONTENTS = {
            "Check Status", "Check Payment", "Cancel Request","Back to User Menu"
    };

    private final CheckRequestController checkRequestController;

    public CheckRequest(CheckRequestController checkRequestController){
        this.checkRequestController = checkRequestController;
    }

    @Override
    public void processPerformed(String processTitle){
        while(true) {
            Util.printSectionTitle(processTitle);

            if (!checkRequestController.isExistingDocMapSet()) {
                return;
            }

            checkRequestController.printDocumentDetails();
            if(!setDocumentChoice()){
                return;
            }

            if(!checkRequestOnProcess()){
                return;
            }
        }
    }

    private boolean setDocumentChoice(){
        int cancellationValue =  ChoiceCollection.EXIT_INT_CODE.getIntValue();

        while(true) {
            int inputDocId = SystemInput.getIntInput(
                    "Enter chosen document id (%d == cancel) : ".formatted(cancellationValue)
            );

            if (inputDocId == cancellationValue) {
                return false;
            }

            if (checkRequestController.isChosenDocumentSet(inputDocId)) {
                return true;
            }
        }
    }

    private boolean checkRequestOnProcess(){
        while (true){
            Util.printSectionTitle("Check Request Section -> " + checkRequestController.selectedDocName());
            Util.printChoices(CHECK_REQUEST_CONTENTS);

            int contentsLength = CHECK_REQUEST_CONTENTS.length;
            int choice = SystemInput.getIntChoice("Enter your choice : ", contentsLength);

            if(choice == contentsLength){
                return false;
            }

            if(checkRequestController.isRequestProcessFinished(choice)){
                return true;
            }
        }
    }
}
