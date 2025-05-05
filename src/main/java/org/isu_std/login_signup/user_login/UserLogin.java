package org.isu_std.login_signup.user_login;

import org.isu_std.io.collections.ChoiceCollection;
import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;
import org.isu_std.login_signup.Login;

public class UserLogin implements Login {
    private final UserLoginController userLoginController;

    public UserLogin(UserLoginController userLoginController){
        this.userLoginController = userLoginController;
    }

    @Override
    public boolean setLoginInformation(){
        Util.printSectionTitle("User Login");

        if(!setUserInfo()){
            return false;
        }

        Util.printMessage("Login Success!");
        return true;
    }

    private boolean setUserInfo(){
        String[] userDetails = userLoginController.userDetails();

        int count = 0;
        while(count < userDetails.length){
            String input = SystemInput.getStringInput(
                    "Enter your %s (%c == Cancel) : ".formatted(
                            userDetails[count],
                            ChoiceCollection.EXIT_CODE.getValue()
                    )
            );

            if(input.charAt(0) == ChoiceCollection.EXIT_CODE.getValue()){
                return false;
            }

            if(userLoginController.isInputAccepted(count, input)){
                count++;
            }
        }

        return true;
    }
}
