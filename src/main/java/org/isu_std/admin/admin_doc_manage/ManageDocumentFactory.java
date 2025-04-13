package org.isu_std.admin.admin_doc_manage;

import org.isu_std.admin.admin_doc_manage.adminDoc_func.AdminDocFuncFactory;
import org.isu_std.dao.DocManageDao;
import org.isu_std.dao.DocumentDao;

import java.util.Map;

public class ManageDocumentFactory {
    private Map<Integer, AdminDocumentImpl> adminDocumentMap;
    private final DocManageDao docManageDao;
    private final DocumentDao documentDao;

    private ManageDocumentFactory(DocManageDao docManageDao, DocumentDao documentDao){
        this.docManageDao = docManageDao;
        this.documentDao = documentDao;
    }

    private static final class Holder{
        private static ManageDocumentFactory instance;
    }

    public static ManageDocumentFactory getInstance(DocManageDao docManageRepository, DocumentDao documentRepository){
        if(Holder.instance == null){
            Holder.instance = new ManageDocumentFactory(docManageRepository, documentRepository);
        }

        return Holder.instance;
    }

    public ManageDocumentUI createManageDocument(int barangayId){
        ManageDocumentProvider manageDocumentProvider = new ManageDocumentProvider(
                docManageDao, documentDao
        );

        adminDocumentMap = manageDocumentProvider.createAdminDocumentMap(barangayId);
        return getManageDocument(barangayId);
    }

    private ManageDocumentUI getManageDocument(int barangayId){
        AdminDocFuncFactory adminDocFuncFactory = new AdminDocFuncFactory(adminDocumentMap);
        ManageDocumentController manageDocumentController = new ManageDocumentController(
                new ManageDocumentService(adminDocFuncFactory),
                barangayId
        );

        return new ManageDocumentUI(manageDocumentController);
    }
}
