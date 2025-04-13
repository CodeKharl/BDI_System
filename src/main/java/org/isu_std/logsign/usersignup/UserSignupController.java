package org.isu_std.logsign.usersignup;

import org.isu_std.io.Util;
import org.isu_std.io.exception.OperationFailedException;
import org.isu_std.logsign.usersignup.brgyselection.BrgySelection;
import org.isu_std.models.modelbuilders.UserBuilder;

public class UserSignupController {
    private final String[] SIGNUP_INFORMATION_NEEDS = {
            "Username (Min. %s)".formatted(UserSignupService.getMinUsernameLength()),
            "Password (Min. %s)".formatted(UserSignupService.getMinPasswordLength())
    };

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
                    userSignupService.checkUserIdExistence(input);
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
        while(true) {
            BrgySelection brgySelection = userSignupService.createBrgySelection();
            int barangayId = brgySelection.getBarangayID();

            // -1 == cancellation
            if(barangayId == - 1){
                return false;
            }

            if(barangayId != 0){
                userBuilder.barangayId(barangayId);
                return true;
            }
        }
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

    protected String[] getSignupInfAttributes(){
        return this.SIGNUP_INFORMATION_NEEDS;
    }
}
