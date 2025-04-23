package org.isu_std.admin.admin_brgy_manage;

import org.isu_std.dao.*;
import org.isu_std.models.Admin;

public class AdminBrgyAccFactory {
    private final DaoFactory daoFactory;
    private AdminBrgyAccFactory(DaoFactory daoFactory){
        this.daoFactory = daoFactory;
    }

    private final static class Holder{
        private static AdminBrgyAccFactory adminBrgyAccFactory;
    }

    public static AdminBrgyAccFactory getInstance(DaoFactory daoFactory){
        if(Holder.adminBrgyAccFactory == null){
            Holder.adminBrgyAccFactory = new AdminBrgyAccFactory(daoFactory);
        }

        return Holder.adminBrgyAccFactory;
    }

    public AdminBrgyAccUI createAdminBrgyAcc(Admin admin){
        AdminBrgyService adminBrgyService = new AdminBrgyService(
                daoFactory.getBrgyDao(),
                daoFactory.getAdminDao(),
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
