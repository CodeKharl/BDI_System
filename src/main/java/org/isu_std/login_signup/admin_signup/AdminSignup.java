package org.isu_std.login_signup.admin_signup;

import org.isu_std.io.collections_enum.ChoiceCollection;
import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;
import org.isu_std.login_signup.Signup;

public class AdminSignup implements Signup {
    private final AdminSignupController adminSignupController;

    public AdminSignup(AdminSignupController adminSignupController){
        this.adminSignupController = adminSignupController;
    }

    @Override
    public void setSignupInformation() {
        while(true) {
            Util.printSectionTitle("Admin Signup");

            if(!setAdminInformations(adminSignupController.getInfoAttributes())){
                return;
            }

            if(adminSignupController.adminSignupProcessComplete()){
               return;
            }
        }
    }

    private boolean setAdminInformations(String[] infoAttributes){
        int count = 0;
        while(count < infoAttributes.length){
            String input = SystemInput.getStringInput("Enter a %s (Exit == %s) : "
                    .formatted(infoAttributes[count], ChoiceCollection.EXIT_CODE.getValue())
            );

            if(input.charAt(0) == ChoiceCollection.EXIT_CODE.getValue()){
                return false;
            }

            if(adminSignupController.isInputAccepted(input, count)){
                count++;
            }
        }

        return true;
    }
}
