package org.isu_std.admin.admin_main;

import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.dao.UserPersonalDao;
import org.isu_std.models.Admin;
import org.isu_std.models.Barangay;
import org.isu_std.dao.DocManageDao;
import org.isu_std.dao.DocumentDao;

public class AdminUIFactory {
    private final DocManageDao docManageDao;
    private final DocumentDao documentDao;
    private final DocumentRequestDao documentRequestDao;
    private final UserPersonalDao userPersonalDao;

    private AdminUIFactory(DocManageDao docManageDao, DocumentDao documentDao, DocumentRequestDao documentRequestDao, UserPersonalDao userPersonalDao){
        this.docManageDao = docManageDao;
        this.documentDao = documentDao;
        this.documentRequestDao = documentRequestDao;
        this.userPersonalDao = userPersonalDao;
    }

    private static class Holder{
        private static AdminUIFactory instance;
    }

    public static AdminUIFactory getInstance(DocManageDao docManageDao, DocumentDao documentDao, DocumentRequestDao documentRequestDao, UserPersonalDao userPersonalDao){
        if(Holder.instance == null){
            Holder.instance = new AdminUIFactory(docManageDao, documentDao, documentRequestDao, userPersonalDao);
        }

        return Holder.instance;
    }

    public AdminUI createAdmin(Admin admin, Barangay barangay){
        AdminUIController adminUIController = new AdminUIController(
                new AdminUIService(docManageDao, documentDao, documentRequestDao, userPersonalDao),
                admin,
                barangay
        );

        return new AdminUI(adminUIController);
    }

}
