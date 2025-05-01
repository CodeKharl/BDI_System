package org.isu_std.user.user_acc_manage.userpersonal.namecreation;

import org.isu_std.io.collections.ChoiceCollection;
import org.isu_std.io.SystemInput;

import java.util.Optional;

// Class that handle the name create of the user.
// Functional paradigm. No controller and service class.

public class NameCreation {
    private final static String[] NAME_INFO = {"First Name", "Middle Name", "Last Name"};

    private final NameCreationController nameCreationController;

    protected NameCreation(NameCreationController nameCreationController){
        this.nameCreationController = nameCreationController;
    }

    public Optional<String> getOptionalCreatedName(){
        char cancellationValue = ChoiceCollection.EXIT_CODE.getValue();

        int count = 0;
        while(count < NAME_INFO.length){
            String input = SystemInput.getStringInput(
                    "Enter your %s (Cancel == %c): ".formatted(NAME_INFO[count], cancellationValue)
            );

            if(input.charAt(0) == cancellationValue){
                return Optional.empty();
            }

            if(nameCreationController.isInputAccepted(NAME_INFO[count], input)){
                count++;
            }
        }

        return Optional.of(nameCreationController.getName());
    }
}
