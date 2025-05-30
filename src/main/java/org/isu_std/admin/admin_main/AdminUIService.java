package org.isu_std.admin.admin_main;

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

    protected ManageDocumentUI getManageDocument(int barangayId){
        var manageDocumentFactory = new ManageDocumentFactory(
                docManageDao,
                documentDao
        );

        return manageDocumentFactory.createManageDocument(barangayId);
    }

    protected RequestedDocument getRequestedDocument(Barangay barangay){
        RequestedDocumentFactory reqDocFactory = RequestedDocumentFactory
                .getInstance(documentRequestDao, documentDao, userPersonalDao);

        return reqDocFactory.createRequestedDocument(barangay);
    }

    protected ApprovedDocument getApprovedDocsRequest(Barangay barangay){
        var approvedDocumentFactory = new ApprovedDocumentFactory(
                documentRequestDao, documentDao, userPersonalDao, paymentDao
        );

        return approvedDocumentFactory.createApprovedDocument(barangay);
    }
}