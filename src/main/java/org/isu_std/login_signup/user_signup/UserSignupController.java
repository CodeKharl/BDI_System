package org.isu_std.login_signup.user_signup;

import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.user_brgy_select.BarangaySelect;
import org.isu_std.models.model_builders.UserBuilder;

public class UserSignupController {
    private final UserSignupService userSignupService;
    private final UserBuilder userBuilder;

    public UserSignupController(UserSignupService userSignupService){
        this.userSignupService = userSignupService;
        this.userBuilder = userSignupService.getUserBuilder();
    }

    protected boolean isInputAccepted(int count, String input){
        try {
            switch (count) {
                case 0 -> {
                    userSignupService.checkInputUsername(input);
                    userBuilder.username(input);
                }
                case 1 -> {
                    userSignupService.checkInputPassword(input);
                    userBuilder.password(input);
                }
            }

            return true;
        }catch (IllegalArgumentException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected boolean setBarangayId(){
        BarangaySelect barangaySelect = userSignupService.createBrgySelection();
        int barangayId = barangaySelect.getBarangayID();

        if(barangayId != 0){
            userBuilder.barangayId(barangayId);
            return true;
        }

        return false;
    }

    protected boolean isAddUserSuccess(){
        try{
            userSignupService.addingUser(userBuilder.build());
            Util.printMessage("The account was successfully created.");
            return true;
        }catch (OperationFailedException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected String[] userDetailSpecWithSpecs(){
        return this.userSignupService.getUserDetailsWithSpecs();
    }
}
