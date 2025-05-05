package org.isu_std.user.user_acc_manage.user_personal.namecreation;

import org.isu_std.io.Util;

public class NameCreationController {
    private final static String NAME_SPLIT_VALUE = " ";

    private final NameCreationService nameCreationService;
    private final StringBuilder nameBuilder;

    protected NameCreationController(NameCreationService nameCreationService){
        this.nameCreationService = nameCreationService;
        this.nameBuilder = nameCreationService.createNameBuilderIns();
    }

    protected boolean isInputAccepted(String type, String input){
        try{
            nameCreationService.checkInputs(type, input);

            // Input is split by spaces and stored in a String array from the argument;
            String[] names = input.split(NAME_SPLIT_VALUE);
            setNameBuilder(names);

            return true;
        }catch (IllegalArgumentException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected void setNameBuilder(String[] strings){
        // Can Handle names that consist of multiple spaces. E.g -> "Boy Bakal"

        for(String str : strings){
            char firstLetter = str.toUpperCase().charAt(0);
            String subsString = str.substring(1).toLowerCase();

            nameBuilder.append(firstLetter)
                    .append(subsString)
                    .append(" ");
        }
    }

    protected String getName(){
        return nameBuilder.toString().trim();
    }
}
