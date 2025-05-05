package org.isu_std.user.user_acc_manage.user_personal.namecreation;

import org.isu_std.io.collections.InputMessageCollection;
import org.isu_std.io.Validation;

public class NameCreationService {

    protected StringBuilder createNameBuilderIns(){
        return new StringBuilder();
    }

    protected void checkInputs(String type, String input){
        if(input.isBlank()){
            throw new IllegalArgumentException(
                    InputMessageCollection.INPUT_INVALID.getFormattedMessage(type)
            );
        }

        if(!Validation.isInputMatchesLettersWithSpaces(input)){
            throw new IllegalArgumentException(
                    InputMessageCollection.INPUT_LETTERS_ONLY.getMessage()
            );
        }
    }
}
