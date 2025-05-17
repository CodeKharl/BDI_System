package org.isu_std.login_signup.user_signup;

import org.isu_std.io.collections_enum.ChoiceCollection;
import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;
import org.isu_std.login_signup.Signup;

public class UserSignup implements Signup {
    private final UserSignupController userSignupController;

    public UserSignup(UserSignupController userSignupController){
        this.userSignupController = userSignupController;
    }

    @Override
    public void setSignupInformation() {
        do {
            Util.printSectionTitle("User Signup");

            if (!setUserInformation()) {
                return;
            }

            if (!userSignupController.setBarangayId()) {
                return;
            }

        } while(!userSignupController.isAddUserSuccess());
    }

    public boolean setUserInformation(){
        String[] userDetails = userSignupController.userDetailSpecWithSpecs();
        char backValue = ChoiceCollection.EXIT_CODE.getValue();

        int count = 0;
        while (count < userDetails.length) {
            String input = SystemInput.getStringInput(
                    "Enter a %s (%c == Cancel) : ".formatted(userDetails[count], backValue)
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
