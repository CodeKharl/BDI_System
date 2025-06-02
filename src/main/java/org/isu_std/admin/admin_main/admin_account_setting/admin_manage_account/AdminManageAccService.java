package org.isu_std.admin.admin_main.admin_account_setting.admin_manage_account;

import org.isu_std.admin_info_manager.AdminInfoManager;
import org.isu_std.client_context.AdminContext;
import org.isu_std.dao.AdminDao;
import org.isu_std.io.SystemLogger;
import org.isu_std.io.custom_exception.DataAccessException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.models.Admin;
import org.isu_std.models.model_builders.BuilderFactory;

public class AdminManageAccService {
    private final AdminDao adminDao;
    private final AdminInfoManager adminInfoManager;

    public AdminManageAccService(AdminDao adminDao, AdminInfoManager adminInfoManager){
        this.adminDao = adminDao;
        this.adminInfoManager = adminInfoManager;
    }

    AdminManageContext createAdminManageContext(AdminContext adminContext){
        return new AdminManageContext(
                adminContext,
                BuilderFactory.createAdminBuilder(),
                adminInfoManager.getAdminAttributeNames(),
                adminInfoManager.getAdminAttributeSpecs()
        );
    }

    protected void modifyAdminInfo(String chosenAttributeName, Admin admin){
        try{
            if(!adminDao.updateAdminInfo(chosenAttributeName, admin)){
                throw new OperationFailedException(
                        "Failed to modify %s! Please try again.".formatted(chosenAttributeName)
                );
            }
        }catch (DataAccessException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException("Failed to update admin by : " + admin);
        }
    }
}
