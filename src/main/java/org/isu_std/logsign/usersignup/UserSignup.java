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
        do {
            Util.printSectionTitle("User Signup");

            if (!setUserInformation(userSignupController.getSignupInfAttributes())) {
                return;
            }

            if (!userSignupController.setBarangayId()) {
                return;
            }

        } while(!userSignupController.isAddUserSuccess());
    }

    public boolean setUserInformation(String[] userInfoContents){
        char backValue = ChoiceCollection.EXIT_CODE.getValue();

        int count = 0;
        while (count < userInfoContents.length) {
            String input = SystemInput.getStringInput(
                    "Enter a %s (%c == Cancel) : ".formatted(userInfoContents[count], backValue)
            );

            if(input.charAt(0) == backValue){
                return false;
            }

            if(userSignupController.isInputAccepted(count, input)){
                count++;
            }
        }

        return true;
    }
}
