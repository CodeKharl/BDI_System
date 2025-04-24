package org.isu_std.user.user_acc_manage.userpersonal.namecreation;

import org.isu_std.io.collections.ChoiceCollection;
import org.isu_std.io.SystemInput;

// Class that handle the name create of the user.
// Functional paradigm. No controller and service class.

public class NameCreation {
    private final static String[] NAME_INFO = {"First Name", "Middle Name", "Last Name"};

    private final NameCreationController nameCreationController;

    public NameCreation(NameCreationController nameCreationController){
        this.nameCreationController = nameCreationController;
    }

    public String getCreatedName(){
        int count = 0;
        while(count < NAME_INFO.length){
            String input = SystemInput.getStringInput(
                    "Enter your %s : ".formatted(NAME_INFO[count])
            );

            if(input.charAt(0) == ChoiceCollection.EXIT_CODE.getValue()){
                return null;
            }

            if(nameCreationController.isInputAccepted(NAME_INFO[count], input)){
                count++;
            }
        }

        return nameCreationController.getNameBuilder();
    }
}
