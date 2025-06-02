package org.isu_std.admin.admin_main.admin_account_setting;

import org.isu_std.client_context.AdminContext;

class AdminAccSettingController {
    private final AdminAccSettingService adminAccSettingService;
    private final AdminContext adminContext;
    private final AdminAccSettingProcess[] adminAccSettingProcess;

    AdminAccSettingController(AdminAccSettingService adminAccSettingService, AdminContext adminContext){
        this.adminAccSettingService = adminAccSettingService;
        this.adminContext = adminContext;
        this.adminAccSettingProcess = adminAccSettingService.getAdminAccSettingProcess();
    }

    protected void sectionStart(String[] accSettingContents, int choice){
        int index = choice - 1;

        adminAccSettingProcess[index]
                .run(accSettingContents[index]);
    }
}
