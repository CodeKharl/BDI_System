package org.isu_std.admin.admin_main;

import org.isu_std.client_context.AdminContext;
import org.isu_std.dao.*;
import org.isu_std.models.Admin;
import org.isu_std.models.Barangay;

public class AdminUIFactory {
    private final DocManageDao docManageDao;
    private final DocumentDao documentDao;
    private final DocumentRequestDao documentRequestDao;
    private final UserPersonalDao userPersonalDao;
    private final PaymentDao paymentDao;

    public AdminUIFactory(
            DocManageDao docManageDao,
            DocumentDao documentDao,
            DocumentRequestDao documentRequestDao,
            UserPersonalDao userPersonalDao,
            PaymentDao paymentDao
    ){
        this.docManageDao = docManageDao;
        this.documentDao = documentDao;
        this.documentRequestDao = documentRequestDao;
        this.userPersonalDao = userPersonalDao;
        this.paymentDao = paymentDao;
    }

    public AdminUI createAdmin(AdminContext adminContext, Barangay barangay){
        var adminUIService = new AdminUIService(
                docManageDao,
                documentDao,
                documentRequestDao,
                userPersonalDao,
                paymentDao
        );

        var adminUIController = new AdminUIController(
                adminUIService,
                adminContext,
                barangay
        );

        return new AdminUI(adminUIController);
    }

}
