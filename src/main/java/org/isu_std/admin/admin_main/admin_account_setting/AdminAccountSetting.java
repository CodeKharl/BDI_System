package org.isu_std.admin.admin_main.admin_account_setting;

import org.isu_std.admin.admin_main.AdminSection;
import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;

/**
 * Class that manage (modify, delete, etc.) admin account information.
 */

public class AdminAccountSetting implements AdminSection {
    private final String[] ACCOUNT_SETTING_CONTENTS = {
            "Account Information",
            "Barangay Information",
            "Delete Account",
            "Back to Admin Menu"
    };

    private final AdminAccSettingController adminAccSettingController;

    AdminAccountSetting(AdminAccSettingController adminAccSettingController){
        this.adminAccSettingController = adminAccSettingController;
    }

    @Override
    public void run(String sectionTitle) {
        int backValue = ACCOUNT_SETTING_CONTENTS.length;

        while(true){
            Util.printSectionTitle(sectionTitle);
            Util.printChoices(ACCOUNT_SETTING_CONTENTS);

            int choice = SystemInput.getIntChoice("Enter your choice : ", backValue);

            if(choice == backValue){
                return;
            }

            adminAccSettingController.sectionStart(ACCOUNT_SETTING_CONTENTS, choice);
        }
    }
}
