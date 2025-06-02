package org.isu_std.admin.admin_main.admin_account_setting;

import org.isu_std.client_context.AdminContext;
import org.isu_std.dao.AdminDao;

public class AdminAccountSettingFactory {
    private final AdminDao adminDao;

    public AdminAccountSettingFactory(AdminDao adminDao){
        this.adminDao = adminDao;
    }

    public AdminAccountSetting create(AdminContext adminContext){
        var adminAccSettingService = new AdminAccSettingService(adminDao);
        var adminAccSettingController = new AdminAccSettingController(adminAccSettingService, adminContext);

        return new AdminAccountSetting(adminAccSettingController);
    }
}
