package org.isu_std.admin.admin_main;

import org.isu_std.admin.admin_main.admin_account_setting.AdminAccountSetting;
import org.isu_std.admin.admin_main.admin_doc_manage.ManageDocumentFactory;
import org.isu_std.admin.admin_main.admin_doc_manage.ManageDocumentUI;
import org.isu_std.admin.admin_main.admin_approved_documents.ApprovedDocument;
import org.isu_std.admin.admin_main.admin_approved_documents.ApprovedDocumentFactory;
import org.isu_std.admin.admin_main.admin_requested_documents.RequestedDocument;
import org.isu_std.admin.admin_main.admin_requested_documents.RequestedDocumentFactory;
import org.isu_std.dao.*;
import org.isu_std.models.Barangay;

public class AdminUIService {
    private final DocManageDao docManageDao;
    private final DocumentDao documentDao;
    private final DocumentRequestDao documentRequestDao;
    private final UserPersonalDao userPersonalDao;
    private final PaymentDao paymentDao;

    protected AdminUIService(
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

    protected AdminSection[] getAdminSections(Barangay barangay){
        var adminSectionFactory = new AdminSectionFactory(
                docManageDao,
                documentDao,
                documentRequestDao,
                userPersonalDao,
                paymentDao
        );

        return adminSectionFactory.createAdminSections(barangay);
    }
}