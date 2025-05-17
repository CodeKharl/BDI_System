package org.isu_std.user.user_acc_manage.user_personal.personalmodify;

import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;
import org.isu_std.io.collections_enum.ChoiceCollection;

public class ModifyPersonal {
    private final ModifyPersonalController modifyPersonalController;

    public ModifyPersonal(ModifyPersonalController modifyPersonalController){
        this.modifyPersonalController = modifyPersonalController;
    }

    public void modifyPerformed(){
        String[] personalDetails = modifyPersonalController.getPersonalDetails();

        while(true){
            if(!setDetailsToModify(personalDetails)){
                return;
            }

            if(!isDetailsConfirmed()){
                continue;
            }

            if(modifyPersonalController.modifyPerform()){
                Util.printMessage("Modify Success!");
            }
        }
    }

    private boolean setDetailsToModify(String[] personalDetails){
        int backValue = personalDetails.length + 1;

        while(true){
            Util.printSectionTitle("Personal Information Modification");
            Util.printChoices(personalDetails);
            Util.printChoice("%d. Back to Manage Account Menu".formatted(backValue));

            int choice = SystemInput.getIntChoice(
                    "Enter the detail you want to modify : ", backValue
            );

            if (choice == backValue) {
                return false;
            }

            modifyPersonalController.setDetailToModify(personalDetails, choice);
            if(setDetailValue(choice)){
                return true;
            }
        }
    }

    private boolean setDetailValue(int choice){
        // If name
        if(choice == 1) {
            return modifyPersonalController.isNameSet();
        }

        return setOtherDetailValue(choice);
    }

    private boolean setOtherDetailValue(int choice){
        String personalDetail = modifyPersonalController.getChosenDetail();
        String personalDetailSpec = modifyPersonalController.getPersonalDetailSpec(choice);
        char cancellationValue = ChoiceCollection.EXIT_CODE.getValue();

        while(true){
            String input = SystemInput.getStringInput(
                    "Enter your %s %s (%c == cancel): ".formatted(
                            personalDetail,
                            personalDetailSpec,
                            cancellationValue
                    )
            );

            if(input.charAt(0) == cancellationValue){
                return false;
            }

            if(modifyPersonalController.isInputValue(choice, input)){
                return true;
            }
        }
    }

    private boolean isDetailsConfirmed(){
        return SystemInput.isPerformConfirmed(
                "Personal Modification Confirmation",
                ChoiceCollection.CONFIRM.getValue(),
                ChoiceCollection.SUB_CANCEL.getValue()
        );
    }
}
