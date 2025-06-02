package org.isu_std.admin.admin_main.admin_account_setting;

import org.isu_std.dao.AdminDao;

class AdminAccSettingService {
    private final AdminDao adminDao;

    protected AdminAccSettingService(AdminDao adminDao){
        this.adminDao = adminDao;
    }

    protected AdminAccSettingProcess[] getAdminAccSettingProcess(){
        var adminAccSettingProcessFactory = new AdminAccSettingProcessFactory();

        return adminAccSettingProcessFactory.create();
    }
}
