package org.isu_std.logsign.adminsignup;

import org.isu_std.io.collections.ChoiceCollection;
import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;
import org.isu_std.logsign.Signup;

public class AdminSignup implements Signup {
    private final AdminSignController adminSignController;

    public AdminSignup(AdminSignController adminSignController){
        this.adminSignController = adminSignController;
    }

    @Override
    public void setSignupInformation() {
        while(true) {
            Util.printSectionTitle("Admin Signup");

            if(!setAdminInformations(adminSignController.getInfoAttributes())){
                return;
            }

            if(adminSignController.adminSignupProcessComplete()){
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

            if(adminSignController.isInputAccepted(input, count)){
                count++;
            }
        }

        return true;
    }
}
