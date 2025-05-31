package org.isu_std.admin.admin_main.admin_doc_manage;

import org.isu_std.dao.DocManageDao;
import org.isu_std.dao.DocumentDao;
import org.isu_std.models.Barangay;

public class ManageDocumentFactory {
    private final DocManageDao docManageDao;
    private final DocumentDao documentDao;

    public ManageDocumentFactory(DocManageDao docManageDao, DocumentDao documentDao){
        this.docManageDao = docManageDao;
        this.documentDao = documentDao;
    }

    public ManageDocumentUI createManageDocument(Barangay barangay){
        var manageDocumentService = new ManageDocumentService(
                documentDao, docManageDao
        );

        var manageDocumentController = new ManageDocumentController(
                manageDocumentService, barangay
        );

        return new ManageDocumentUI(manageDocumentController);
    }
}
