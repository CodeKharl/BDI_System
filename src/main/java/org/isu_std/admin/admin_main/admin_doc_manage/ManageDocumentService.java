package org.isu_std.admin.admin_main.admin_doc_manage;

import org.isu_std.dao.DocManageDao;
import org.isu_std.dao.DocumentDao;

public class ManageDocumentService {
    private final DocumentDao documentDao;
    private final DocManageDao docManageDao;

    public ManageDocumentService(DocumentDao documentDao, DocManageDao docManageDao){
        this.documentDao = documentDao;
        this.docManageDao = docManageDao;
    }

    protected ManageDocumentImpl[] getAdminDocumentImplArr(int barangayId){
        var manageDocImplFactory = new ManageDocImplFactory(docManageDao, documentDao);
        return manageDocImplFactory.createAdminDocumentMap(barangayId);
    }
}
