package org.isu_std.admin.admin_ui;

/*
    This class will be the UI for Admins.
    It manages the available features for the admin
    and managing barangay documents.
 */

import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;


public class AdminUI {
    private final String[] ADMIN_UI_CONTENTS = {"Pending/Processed Document Requests", "Manage Documents", "Account Settings", "Sign-out"};

    private final AdminUIController adminUIController;

    public AdminUI(AdminUIController adminUIController){
        this.adminUIController = adminUIController;
    }

    public void adminMenu(){
        do {
            Util.printSectionTitle(
                    "Admin Menu (%s)".formatted(
                            adminUIController.getAdminName()
                    )
            );

            Util.printChoices(ADMIN_UI_CONTENTS);
        }while(isAdminActive());
    }

    private boolean isAdminActive(){
        switch(SystemInput.getIntChoice("Enter your choice : ", ADMIN_UI_CONTENTS.length)){
            case 1 -> adminUIController.startRequestedDocument();
            case 2 -> adminUIController.startManageDocument();
            case 3 -> {}
            case 4 -> {
                Util.printMessage("Account sign-out...");
                return false;
            }
        };

        return true;
    }
}
