package org.isu_std.login_signup.admin_login;

import org.isu_std.io.collections.ChoiceCollection;
import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;
import org.isu_std.login_signup.Login;

public class AdminLogin implements Login {
    private final String[] INFORMATION_NEEDS = {"Admin ID", "Pin"};

    private final AdminLoginController adminLoginController;

    public AdminLogin(AdminLoginController adminLoginController){
        this.adminLoginController = adminLoginController;
    }

    @Override
    public boolean setLoginInformation() {
        Util.printSectionTitle("Admin Login");

        if(!setInformations()){
            return false;
        }

        Util.printMessage("Login Success");
        return true;
    }

    private boolean setInformations(){
        int count = 0;
        while (count < INFORMATION_NEEDS.length) {
            int input = SystemInput.getIntInput(
                    "Enter your %s (Cancel == %d): "
                            .formatted(INFORMATION_NEEDS[count], ChoiceCollection.EXIT_INT_CODE.getIntValue())
            );

            if (input == ChoiceCollection.EXIT_INT_CODE.getIntValue()) {
                return false;
            }

            if (adminLoginController.isInputAccepted(count, input)){
                count++;
            }
        }

        return true;
    }

}
