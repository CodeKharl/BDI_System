package org.isu_std.admin.admin_main;

import org.isu_std.admin.admin_doc_manage.ManageDocumentFactory;
import org.isu_std.admin.admin_doc_manage.ManageDocumentUI;
import org.isu_std.admin.admin_main.approved_documents.ApprovedDocument;
import org.isu_std.admin.admin_main.approved_documents.ApprovedDocumentFactory;
import org.isu_std.admin.admin_main.requested_documents.RequestedDocument;
import org.isu_std.admin.admin_main.requested_documents.RequestedDocumentFactory;
import org.isu_std.dao.*;
import org.isu_std.models.Barangay;

public class AdminUIService {
    private final DocManageDao docManageDao;
    private final DocumentDao documentDao;
    private final DocumentRequestDao documentRequestDao;
    private final UserPersonalDao userPersonalDao;
    private final PaymentDao paymentDao;

    public AdminUIService(
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

    protected ManageDocumentUI getManageDocumentUI(int barangayID){
        ManageDocumentFactory manageDocumentFactory = new ManageDocumentFactory(
                docManageDao,
                documentDao
        );

        return manageDocumentFactory.createManageDocument(barangayID);
    }

    protected RequestedDocument getRequestedDocument(Barangay barangay){
        RequestedDocumentFactory reqDocFactory = RequestedDocumentFactory
                .getInstance(documentRequestDao, documentDao, userPersonalDao);

        return reqDocFactory.createRequestedDocument(barangay);
    }

    protected ApprovedDocument getApprovedDocsRequest(Barangay barangay){
        ApprovedDocumentFactory approvedDocumentFactory = new ApprovedDocumentFactory(
                documentRequestDao, documentDao, userPersonalDao, paymentDao
        );

        return approvedDocumentFactory.createApprovedDocument(barangay);
    }
}