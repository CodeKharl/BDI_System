package org.isu_std.user.user_acc_manage.userpersonal.personalcreation;

import org.isu_std.io.collections.ChoiceCollection;
import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;
import org.isu_std.user.user_acc_manage.userpersonal.PersonalInfoConfig;

public class CreatePersonal {
    private final CreatePersonalController createPersonalController;

    public CreatePersonal(CreatePersonalController createPersonalController){
        this.createPersonalController = createPersonalController;
    }

    public void createPerformed(){
        Util.printSectionTitle(
                "Personal Information Creation (%c == cancel)"
                        .formatted(ChoiceCollection.EXIT_CODE.getValue())
        );

        if(!setInformations()){
            return;
        }

        if(confirmAndPerformed()){
            Util.printMessage("Personal Information has been save! You can now request documents.");
        }
    }

    private boolean setInformations(){
        if(!createPersonalController.isNameSet()){
            return false;
        }

        return setOtherInformations();
    }

    private boolean setOtherInformations(){
        String[] informations = PersonalInfoConfig.PERSONAL_NEEDS.getValues();
        String[] specifications = PersonalInfoConfig.PERSONAL_SPECIFICATION.getValues();

        int count = 1; // start at one since name is already set.
        while(count < informations.length){
            String input = SystemInput.getStringInput(
                    "Enter your %s %s : "
                            .formatted(informations[count], specifications[count])
            );

            if(input.charAt(0) == ChoiceCollection.EXIT_CODE.getValue()){
                return false;
            }

            if(createPersonalController.isInputAccepted(count, input)){
                count++;
            }
        }

        return true;
    }

    private boolean confirmAndPerformed(){
        createPersonalController.printUserPersonalInfo();

        if(SystemInput.isPerformConfirmed(
                "Confirmation", ChoiceCollection.CONFIRM.getValue(),
                ChoiceCollection.SUB_CANCEL.getValue()
        )){
            return createPersonalController.createPerformed();
        }

        return false;
    }


}
