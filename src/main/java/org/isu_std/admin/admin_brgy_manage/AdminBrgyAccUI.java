package org.isu_std.admin.admin_brgy_manage;

import org.isu_std.PostLoginNavigator;
import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;

public class AdminBrgyAccUI implements PostLoginNavigator {
    private final String[] ADMIN_BRGY_CONTENTS = {
            "Link your Account to a Barangay", "Register a Barangay", "Cancel"
    };

    private final AdminBrgyAccController adminBrgyAccController;

    public AdminBrgyAccUI(AdminBrgyAccController adminBrgyAccController){
        this.adminBrgyAccController = adminBrgyAccController;
    }

    @Override
    public void navigateToSection() {
        if(adminBrgyAccController.isUserHasNoBarangayId()){
            if(!connectAccount()){
                return;
            }
        }

        adminBrgyAccController.launchAdminUI();
    }

    private boolean connectAccount(){
        while(true){
            Util.printSectionTitle("Connect Admin Account : Link or Register");
            Util.printChoices(ADMIN_BRGY_CONTENTS);

            int choice = SystemInput.getIntChoice("Enter choice : ", ADMIN_BRGY_CONTENTS.length);

            // Exit statement.
            if(choice == ADMIN_BRGY_CONTENTS.length){
                return false;
            }

            if(adminBrgyAccController.isProcessSuccess(choice)) {
                return true;
            }
        }
    }
}
