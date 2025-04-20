package org.isu_std.logsign.usersignup;

import org.isu_std.io.collections.ChoiceCollection;
import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;
import org.isu_std.logsign.Signup;

public class UserSignup implements Signup {
    private final UserSignupController userSignupController;

    public UserSignup(UserSignupController userSignupController){
        this.userSignupController = userSignupController;
    }

    @Override
    public void setSignupInformation() {
        while(true){
            Util.printSectionTitle("User Signup");

            if(!setUserInformation(userSignupController.getSignupInfAttributes())){
                return;
            }

            if(!userSignupController.setBarangayId()){
                continue;
            }

            if(userSignupController.isAddUserSuccess()){
                break;
            }
        }
    }

    public boolean setUserInformation(String[] userInfoContents){
        int count = 0;
        while (count < userInfoContents.length) {
            String input = SystemInput.getStringInput(
                    "Enter a %s (%c == Cancel) : "
                            .formatted(userInfoContents[count], ChoiceCollection.EXIT_CODE.getValue())
            );

            if(input.charAt(0) == ChoiceCollection.EXIT_CODE.getValue()){
                return false;
            }

            if(userSignupController.isInputAccepted(count, input)){
                count++;
            }
        }

        return true;
    }
}
