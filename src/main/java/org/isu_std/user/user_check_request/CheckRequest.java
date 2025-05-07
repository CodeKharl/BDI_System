package org.isu_std.user.user_check_request;

import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;
import org.isu_std.user.UserProcess;

public class CheckRequest implements UserProcess {
    private final String[] CHECK_REQUEST_CONTENTS = {
            "Check Status", "Payment Manage", "Cancel Request","Back to Document Selection"
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

            printDocumentDetailsWithGuide();

            if(!setDocumentChoice()){
                return;
            }

            checkRequestProcess();
        }
    }

    private void printDocumentDetailsWithGuide(){
        Util.printSubSectionTitle("Existing Request (Document Name - Price - Requirements)");
        Util.printMessage("Note! Requested document cannot be found once the admin reject it.");
        checkRequestController.printDocumentDetails();
    }

    private boolean setDocumentChoice(){
        int cancellationValue = checkRequestController.docRequestListLength() + 1;
        Util.printChoice("%d. Back to User Menu".formatted(cancellationValue));

        int choice = SystemInput.getIntChoice(
                "Enter the chosen document : ",
                cancellationValue
        );

        if (choice == cancellationValue) {
            return false;
        }

        checkRequestController.setChosenDocument(choice);
        return true;
    }

    private void checkRequestProcess(){
        while (true){
            Util.printSectionTitle("Check Request Section -> " + checkRequestController.selectedDocName());
            Util.printChoices(CHECK_REQUEST_CONTENTS);

            int contentsLength = CHECK_REQUEST_CONTENTS.length;
            int choice = SystemInput.getIntChoice("Enter your choice : ", contentsLength);

            if(choice == contentsLength){
                return;
            }

            if(checkRequestController.isRequestProcessFinished(choice)){
                return;
            }
        }
    }
}
