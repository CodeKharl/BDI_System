package org.isu_std.admin.admin_main;

import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;

/*
    This class will be the UI for Admins.
    It manages the available features for the admin
    and managing barangay documents.
 */

public class AdminUI {
    private final String[] ADMIN_UI_CONTENTS = {
            "Pending/Processed Document Requests", "Approved Requested Documents", "Manage Documents",
            "Account Settings", "Sign-out"
    };

    private final AdminUIController adminUIController;

    public AdminUI(AdminUIController adminUIController){
        this.adminUIController = adminUIController;
    }

    public void adminMenu(){
        while(true){
            Util.printSectionTitle("Admin Menu (%s) (%s)"
                    .formatted(adminUIController.getAdminName(), adminUIController.getBarangayFullName())
            );

            Util.printChoices(ADMIN_UI_CONTENTS);

            int choice = SystemInput.getIntChoice(
                    "Enter your choice : ",
                    ADMIN_UI_CONTENTS.length
            );

            if(choice == ADMIN_UI_CONTENTS.length){
                Util.printMessage("Account sign-out...");
                return;
            }

            adminUIController.adminOnProcess(ADMIN_UI_CONTENTS, choice);
        }
    }
}
