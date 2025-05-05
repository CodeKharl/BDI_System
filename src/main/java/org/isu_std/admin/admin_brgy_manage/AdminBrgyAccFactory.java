package org.isu_std.admin.admin_brgy_manage;

import org.isu_std.dao.*;
import org.isu_std.models.Admin;

public class AdminBrgyAccFactory {
    private final DaoFactory daoFactory;

    public AdminBrgyAccFactory(DaoFactory daoFactory){
        this.daoFactory = daoFactory;
    }

    public AdminBrgyAccUI createAdminBrgyAcc(Admin admin){
        AdminBrgyService adminBrgyService = new AdminBrgyService(
                daoFactory.createBrgyDao(),
                daoFactory.createAdminDao(),
                daoFactory.getDocManageDao(),
                daoFactory.getDocumentDao(),
                daoFactory.getPersonalDao(),
                daoFactory.getDocumentRequestDao(),
                daoFactory.paymentDao()
        );

        AdminBrgyAccController adminBrgyAccController = new AdminBrgyAccController(admin, adminBrgyService);
        return new AdminBrgyAccUI(adminBrgyAccController);
    }
}
