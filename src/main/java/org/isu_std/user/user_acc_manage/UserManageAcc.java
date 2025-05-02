package org.isu_std.user.user_acc_manage;

import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;
import org.isu_std.user.UserProcess;

public class UserManageAcc implements UserProcess{
    private static final String[] MANAGE_CHOICES = {
            "Personal Info",
            "Account Info",
            "Barangay Info",
            "Back to User Menu"
    };

    private final UserManageAccController userAccController;

    public UserManageAcc(UserManageAccController userAccController){
        this.userAccController = userAccController;
    }

    @Override
    public void processPerformed(String processTitle){
        int backValue = MANAGE_CHOICES.length;

        while(true){
            Util.printSectionTitle(processTitle);
            Util.printChoices(MANAGE_CHOICES);

            int choice = SystemInput.getIntChoice("Enter choice : ", backValue);

            if(choice == backValue){
                return;
            }

            userAccController.managerPerformed(MANAGE_CHOICES, choice);
        }
    }
}

