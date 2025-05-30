package org.isu_std.admin.admin_main.admin_approved_documents;

import org.isu_std.dao.DocumentDao;
import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.dao.PaymentDao;
import org.isu_std.dao.UserPersonalDao;
import org.isu_std.models.Barangay;

public class ApprovedDocumentFactory {
    private final DocumentRequestDao documentRequestDao;
    private final DocumentDao documentDao;
    private final UserPersonalDao userPersonalDao;
    private final PaymentDao paymentDao;

    public ApprovedDocumentFactory(
            DocumentRequestDao documentRequestDao,
            DocumentDao documentDao,
            UserPersonalDao userPersonalDao,
            PaymentDao paymentDao
    ){
        this.documentRequestDao = documentRequestDao;
        this.documentDao = documentDao;
        this.userPersonalDao = userPersonalDao;
        this.paymentDao = paymentDao;
    }

    public ApprovedDocument createApprovedDocument(Barangay barangay){
        ApprovedDocumentService approvedDocumentService = new ApprovedDocumentService(
                documentRequestDao, documentDao, userPersonalDao, paymentDao
        );

        ApprovedDocumentController approvedDocumentController = new ApprovedDocumentController(approvedDocumentService, barangay);
        return new ApprovedDocument(approvedDocumentController);
    }
}
