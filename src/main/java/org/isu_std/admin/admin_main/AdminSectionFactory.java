package org.isu_std.admin.admin_main;

import org.isu_std.admin.admin_main.admin_account_setting.AdminAccountSetting;
import org.isu_std.admin.admin_main.admin_account_setting.AdminAccountSettingFactory;
import org.isu_std.admin.admin_main.admin_approved_documents.ApprovedDocument;
import org.isu_std.admin.admin_main.admin_approved_documents.ApprovedDocumentFactory;
import org.isu_std.admin.admin_main.admin_doc_manage.ManageDocumentFactory;
import org.isu_std.admin.admin_main.admin_doc_manage.ManageDocumentUI;
import org.isu_std.admin.admin_main.admin_requested_documents.RequestedDocument;
import org.isu_std.admin.admin_main.admin_requested_documents.RequestedDocumentFactory;
import org.isu_std.client_context.AdminContext;
import org.isu_std.dao.*;
import org.isu_std.io.SystemLogger;
import org.isu_std.io.custom_exception.DataAccessException;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.models.Admin;
import org.isu_std.models.Barangay;

import java.util.Optional;

public class AdminSectionFactory {
    private final AdminDao adminDao;
    private final DocManageDao docManageDao;
    private final DocumentDao documentDao;
    private final DocumentRequestDao documentRequestDao;
    private final UserPersonalDao userPersonalDao;
    private final PaymentDao paymentDao;

    protected AdminSectionFactory(
            AdminDao adminDao,
            DocManageDao docManageDao,
            DocumentDao documentDao,
            DocumentRequestDao documentRequestDao,
            UserPersonalDao userPersonalDao,
            PaymentDao paymentDao
    ){
        this.adminDao = adminDao;
        this.docManageDao = docManageDao;
        this.documentDao = documentDao;
        this.documentRequestDao = documentRequestDao;
        this.userPersonalDao = userPersonalDao;
        this.paymentDao = paymentDao;
    }

    protected AdminSection[] createAdminSections(AdminContext adminContext, Barangay barangay){
        return new AdminSection[]{
                createRequestedDocument(barangay),
                createApprovedDocsRequest(barangay),
                createManageDocument(barangay),
                createAdminAccountSetting(adminContext)
        };
    }

    private ManageDocumentUI createManageDocument(Barangay barangay){
        var manageDocumentFactory = new ManageDocumentFactory(
                docManageDao,
                documentDao
        );

        return manageDocumentFactory.createManageDocument(barangay);
    }

    private RequestedDocument createRequestedDocument(Barangay barangay){
        RequestedDocumentFactory reqDocFactory = RequestedDocumentFactory
                .getInstance(documentRequestDao, documentDao, userPersonalDao);

        return reqDocFactory.createRequestedDocument(barangay);
    }

    private ApprovedDocument createApprovedDocsRequest(Barangay barangay){
        var approvedDocumentFactory = new ApprovedDocumentFactory(
                documentRequestDao, documentDao, userPersonalDao, paymentDao
        );

        return approvedDocumentFactory.createApprovedDocument(barangay);
    }

    private AdminAccountSetting createAdminAccountSetting(AdminContext adminContext){
        var adminAccountSettingFactory = new AdminAccountSettingFactory(adminDao);

        return adminAccountSettingFactory.create(adminContext);
    }
}
