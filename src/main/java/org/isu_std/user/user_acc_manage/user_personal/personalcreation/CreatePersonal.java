package org.isu_std.user.user_acc_manage.user_personal.personalcreation;

import org.isu_std.io.collections_enum.ChoiceCollection;
import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;

public class CreatePersonal{
    private final CreatePersonalController createPersonalController;

    public CreatePersonal(CreatePersonalController createPersonalController){
        this.createPersonalController = createPersonalController;
    }

    public void createPerformed(){
        Util.printSectionTitle("Personal Information Creation");

        if(!setInformations()){
            return;
        }

        if(confirmAndPerformed()){
            Util.printMessage("Personal Information has been save! You can now request documents.");
        }
    }

    private boolean setInformations(){
        // Setting up the name.
        if(!createPersonalController.setName()){
            return false;
        }

        return setOtherInformations();
    }

    private boolean setOtherInformations(){
        String[] attributeNames = createPersonalController.getPersonalDetails();
        String[] specifications = createPersonalController.getPersonalDetailSpecs();

        char cancellationValue = ChoiceCollection.EXIT_CODE.getValue();

        int count = 1; // start at one since name is already set.
        while(count < attributeNames.length){
            String input = SystemInput.getStringInput(
                    "Enter your %s %s (Cancel == %c) : "
                            .formatted(attributeNames[count], specifications[count], cancellationValue)
            );

            if(input.charAt(0) == cancellationValue){
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
            return createPersonalController.savePersonalInfo();
        }

        return false;
    }


}
