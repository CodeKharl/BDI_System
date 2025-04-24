package org.isu_std.user.user_acc_manage.userpersonal.namecreation;

import org.isu_std.io.Util;

public class NameCreationController {
    private final NameCreationService nameCreationService;
    private final StringBuilder nameBuilder;

    public NameCreationController(NameCreationService nameCreationService){
        this.nameCreationService = nameCreationService;
        this.nameBuilder = nameCreationService.createNameBuilderIns();
    }

    protected boolean isInputAccepted(String type, String input){
        try{
            nameCreationService.checkInputs(type, input);

            // Input is split by spaces and stored in a String array from the argument;
            setNameBuilder(input.split(" "));

            return true;
        }catch (IllegalArgumentException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected void setNameBuilder(String[] strings){
        // Can Handle names that consist of multiple spaces. E.g -> "Boy Bakal"

        for(String str : strings){
            nameBuilder.append(str.toUpperCase().charAt(0))
                    .append(str.substring(1).toLowerCase())
                    .append(" ");
        }
    }

    protected String getNameBuilder(){
        return nameBuilder.toString().trim();
    }
}
