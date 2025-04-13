package org.isu_std.user;

import org.isu_std.PostLoginNavigator;
import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;

public class UserUI implements PostLoginNavigator {
    private static final String[] USER_UI_CONTENTS = {
            "Purchase Documents", "Manage Account", "Sign-out"
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

            switch(choice){
                case 1 -> userController.userDocumentRequest();
                case 2 -> userController.userManageAcc();
                case 3 -> {
                    Util.printMessage("Account sign-out...");
                    return;
                }
            }
        }
    }

    private void printTitleAndContents(){
        Util.printSectionTitle("User Menu (%s)".formatted(
                        userController.getUsername()
                )
        );

        Util.printChoices(USER_UI_CONTENTS);
    }

    public static String getContent(int index){
        return USER_UI_CONTENTS[index];
    }
}
