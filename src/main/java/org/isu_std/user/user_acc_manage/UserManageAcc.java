package org.isu_std.user.user_acc_manage;

import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;
import org.isu_std.user.UserProcess;
import org.isu_std.user.UserUI;

public class UserManageAcc implements UserProcess{
    private static final String[] MANAGE_CHOICES = {
            "Personal Information",
            "Account Information",
            "Barangay Information",
            "Back to User Menu"
    };

    private final UserManageAccController userAccController;

    public UserManageAcc(UserManageAccController userAccController){
        this.userAccController = userAccController;
    }

    @Override
    public void processPerformed(String processTitle){
        while(true){
            Util.printSectionTitle(processTitle);
            Util.printChoices(MANAGE_CHOICES);

            int choice = SystemInput.getIntChoice(
                    "Enter choice : ",
                    MANAGE_CHOICES.length
            );

            switch (choice){
                case 1 -> userAccController.personalProcess();
                case 2 -> userAccController.accInfoProcess();
                case 3 -> {}
                case 4 -> {
                    return;
                }
            }
        }
    }
    public void manageMenu(){

    }
}

