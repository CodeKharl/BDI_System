package org.isu_std.admin.admin_brgy_manage;

import org.isu_std.client_context.AdminContext;
import org.isu_std.dao.*;
import org.isu_std.models.Admin;

public class AdminBrgyAccFactory {
    private final DaoFactory daoFactory;

    public AdminBrgyAccFactory(DaoFactory daoFactory){
        this.daoFactory = daoFactory;
    }

    public AdminBrgyAccUI createAdminBrgyAcc(AdminContext adminContext){
        var adminBrgyService = new AdminBrgyService(
                daoFactory.createBrgyDao(),
                daoFactory.createAdminDao(),
                daoFactory.getDocManageDao(),
                daoFactory.getDocumentDao(),
                daoFactory.getPersonalDao(),
                daoFactory.getDocumentRequestDao(),
                daoFactory.paymentDao()
        );

        var adminBrgyAccController = new AdminBrgyAccController(
                adminContext, adminBrgyService
        );

        return new AdminBrgyAccUI(adminBrgyAccController);
    }
}
