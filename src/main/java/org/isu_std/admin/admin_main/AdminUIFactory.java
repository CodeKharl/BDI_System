package org.isu_std.admin.admin_main;

import org.isu_std.dao.*;
import org.isu_std.models.Admin;
import org.isu_std.models.Barangay;

public class AdminUIFactory {
    private final DocManageDao docManageDao;
    private final DocumentDao documentDao;
    private final DocumentRequestDao documentRequestDao;
    private final UserPersonalDao userPersonalDao;
    private final PaymentDao paymentDao;

    private AdminUIFactory(
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

    private static class Holder{
        private static AdminUIFactory instance;
    }

    public static AdminUIFactory getInstance(
            DocManageDao docManageDao,
            DocumentDao documentDao,
            DocumentRequestDao documentRequestDao,
            UserPersonalDao userPersonalDao,
            PaymentDao paymentDao
    ){
        if(Holder.instance == null){
            Holder.instance = new AdminUIFactory(
                    docManageDao,
                    documentDao,
                    documentRequestDao,
                    userPersonalDao,
                    paymentDao
            );
        }

        return Holder.instance;
    }

    public AdminUI createAdmin(Admin admin, Barangay barangay){
        AdminUIController adminUIController = new AdminUIController(
                new AdminUIService(docManageDao, documentDao, documentRequestDao, userPersonalDao, paymentDao),
                admin,
                barangay
        );

        return new AdminUI(adminUIController);
    }

}
