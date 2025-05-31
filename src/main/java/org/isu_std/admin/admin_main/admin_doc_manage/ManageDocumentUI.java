package org.isu_std.admin.admin_main.admin_doc_manage;

import org.isu_std.admin.admin_main.AdminSection;
import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;
import org.isu_std.login_signup.admin_login.AdminLogin;

import java.awt.event.ActionListener;

public class ManageDocumentUI implements AdminSection {
    private final String[] MANAGE_CONTENTS = {"Add Document", "Modify Document",
            "Delete Document", "Display Document", "Back to Admin Menu"};

    private final ManageDocumentController manageDocumentController;

    ManageDocumentUI(ManageDocumentController manageDocumentController){
        this.manageDocumentController = manageDocumentController;
    }

    @Override
    public void run(String sectionTitle){
        while(true){
            Util.printSectionTitle(sectionTitle);
            Util.printChoices(MANAGE_CONTENTS);

            int choice = SystemInput.getIntChoice(
                    "Enter choice : ",
                    MANAGE_CONTENTS.length
            );

            if(choice == MANAGE_CONTENTS.length){
                return; // Back to admin menu.
            }

            manageDocumentController.startManageDocFunc(MANAGE_CONTENTS, choice);
        }
    }
}


