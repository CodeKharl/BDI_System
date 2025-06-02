package org.isu_std.admin.admin_main.admin_account_setting.admin_manage_account;

import org.isu_std.client_context.AdminContext;
import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.models.Admin;

public class AdminManageAccController {
    private final AdminManageAccService adminManageAccService;
    private final AdminManageContext adminManageContext;

    public AdminManageAccController(AdminManageAccService adminManageAccService, AdminContext adminContext){
        this.adminManageAccService = adminManageAccService;
        this.adminManageContext = adminManageAccService.createAdminManageContext(adminContext);
    }

    protected String getAdminDetails(){
        Admin admin = adminManageContext.getAdminContext().getAdmin();

        return "User Details [Admin Name, Pin] -> [%s, %s]".
                formatted(admin.adminName(), admin.adminPin());
    }

    protected String[] getAdminAttributeNames(){
        return this.adminManageContext.getAdminAttributeNames();
    }

    protected boolean isModifySuccess(){
        try{
            adminManageAccService.modifyAdminInfo(null, null);
        }catch (ServiceException | OperationFailedException e){
            Util.printMessage(e.getMessage());
        }

        return false;
    }
}
