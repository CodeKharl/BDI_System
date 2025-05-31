package org.isu_std.admin_info_manager;

import org.isu_std.dao.AdminDao;

public class AdminInfoManagerFactory {
    public static AdminInfoManager create(AdminDao adminDao){
        return new AdminInfoManager(adminDao);
    }
}
