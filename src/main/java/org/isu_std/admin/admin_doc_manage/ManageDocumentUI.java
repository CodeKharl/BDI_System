package org.isu_std.admin.admin_doc_manage;

import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;

public class ManageDocumentUI {
    private final String[] MANAGE_CONTENTS = {"Add Document", "Modify Document",
            "Delete Document", "Display Document", "Back to Admin Menu"};

    private final ManageDocumentController manageDocumentController;

    public ManageDocumentUI(ManageDocumentController manageDocumentController){
        this.manageDocumentController = manageDocumentController;
    }

    public final void manageMenu(){
        while(true){
            Util.printSectionTitle("Manage Document");
            Util.printChoices(MANAGE_CONTENTS);

            int choice = SystemInput.getIntChoice(
                    "Enter choice : ",
                    MANAGE_CONTENTS.length
            );

            if(choice == MANAGE_CONTENTS.length){
                return; // Back to admin menu.
            }

            manageDocumentController.startManageDocFunc(choice);
        }
    }

}


