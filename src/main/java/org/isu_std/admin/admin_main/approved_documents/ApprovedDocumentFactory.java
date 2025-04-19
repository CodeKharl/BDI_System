package org.isu_std.admin.admin_main.approved_documents;

import org.isu_std.dao.DocumentDao;
import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.dao.UserPersonalDao;
import org.isu_std.models.Barangay;

public class ApprovedDocumentFactory {
    private final DocumentRequestDao documentRequestDao;
    private final DocumentDao documentDao;
    private final UserPersonalDao userPersonalDao;

    public ApprovedDocumentFactory(
            DocumentRequestDao documentRequestDao, DocumentDao documentDao, UserPersonalDao userPersonalDao
    ){
        this.documentRequestDao = documentRequestDao;
        this.documentDao = documentDao;
        this.userPersonalDao = userPersonalDao;
    }

    public ApprovedDocument createApprovedDocument(Barangay barangay){
        ApprovedService approvedService = new ApprovedService(documentRequestDao, documentDao, userPersonalDao);
        ApprovedController approvedController = new ApprovedController(approvedService, barangay);
        return new ApprovedDocument(approvedController);
    }
}
