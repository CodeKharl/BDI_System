package org.isu_std.user.userAccManage;

import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;
import org.isu_std.user.UserUI;

public class UserManageAcc {
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

    public void manageMenu(){
        while(true){
            Util.printSectionTitle(UserUI.getContent(1));
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
}

