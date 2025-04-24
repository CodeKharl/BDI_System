package org.isu_std.user;

import org.isu_std.PostLoginNavigator;
import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;

public class UserUI implements PostLoginNavigator {
    private static final String[] USER_UI_CONTENTS = {
            "Request Document", "Check Existing Requests", "Manage Account", "Sign-out"
    };

    private final UserController userController;

    public UserUI(UserController userController){
        this.userController = userController;
    }

    @Override
    public void navigateToSection() {
        while(true) {
            printTitleAndContents();

            int choice = SystemInput.getIntChoice(
                    "Enter your choice : ", USER_UI_CONTENTS.length
            );

            if(choice == USER_UI_CONTENTS.length){
                return;
            }

            userController.userOnProcess(USER_UI_CONTENTS, choice);
        }
    }

    private void printTitleAndContents(){
        Util.printSectionTitle("User Menu (%s)".formatted(
                        userController.getUsername()
                )
        );

        Util.printChoices(USER_UI_CONTENTS);
    }
}
