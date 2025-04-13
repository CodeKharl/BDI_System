package org.isu_std.admin.admin_doc_manage;

import org.isu_std.admin.admin_doc_manage.adminDoc_func.add.AddingDocController;
import org.isu_std.admin.admin_doc_manage.adminDoc_func.add.AddingDocument;
import org.isu_std.admin.admin_doc_manage.adminDoc_func.add.AddingDocService;
import org.isu_std.admin.admin_doc_manage.adminDoc_func.delete.DeletingDocController;
import org.isu_std.admin.admin_doc_manage.adminDoc_func.delete.DeletingDocService;
import org.isu_std.admin.admin_doc_manage.adminDoc_func.delete.DeletingDocument;
import org.isu_std.admin.admin_doc_manage.adminDoc_func.display.DisplayingDocController;
import org.isu_std.admin.admin_doc_manage.adminDoc_func.display.DisplayingDocService;
import org.isu_std.admin.admin_doc_manage.adminDoc_func.display.DisplayingDocument;
import org.isu_std.admin.admin_doc_manage.adminDoc_func.modify.ModifyingDocController;
import org.isu_std.admin.admin_doc_manage.adminDoc_func.modify.ModifyingDocService;
import org.isu_std.admin.admin_doc_manage.adminDoc_func.modify.ModifyingDocument;
import org.isu_std.admin.admin_doc_manage.adminDoc_func.others.docIdValidation.DocIDValidation;
import org.isu_std.admin.admin_doc_manage.adminDoc_func.others.docIdValidation.DocIDValidationController;
import org.isu_std.admin.admin_doc_manage.adminDoc_func.others.docIdValidation.DocIDValidationService;
import org.isu_std.dao.DocManageDao;
import org.isu_std.dao.DocumentDao;

import java.util.HashMap;
import java.util.Map;

public class ManageDocumentProvider {
    private final DocManageDao docManageDao;
    private final DocumentDao documentDao;
    private final DocIDValidation docIDValidation;

    protected ManageDocumentProvider(
            DocManageDao docManageDao,
            DocumentDao documentDao
    ){
        this.docManageDao = docManageDao;
        this.documentDao = documentDao;

        this.docIDValidation = new DocIDValidation(
                new DocIDValidationController(
                        new DocIDValidationService(documentDao)
                )
        );
    }

    protected final Map<Integer, AdminDocumentImpl> createAdminDocumentMap(int barangayId){
        Map<Integer, AdminDocumentImpl> adminDocumentMap = new HashMap<>();

        adminDocumentMap.put(1, getAddingDocument(barangayId));
        adminDocumentMap.put(2, getModifyDocument(barangayId));
        adminDocumentMap.put(3, getDeletingDocument(barangayId));
        adminDocumentMap.put(4, getDisplayingDocument(barangayId));

        return adminDocumentMap;
    }

    private AddingDocument getAddingDocument(int barangayId){
        return new AddingDocument(
                new AddingDocController(
                        new AddingDocService(docManageDao),
                        barangayId
                )
        );
    }

    private ModifyingDocument getModifyDocument(int barangayId){
        return new ModifyingDocument(
                new ModifyingDocController(
                        new ModifyingDocService(docManageDao, docIDValidation),
                        barangayId
                )
        );

    }

    private DeletingDocument getDeletingDocument(int barangayId){
        return new DeletingDocument(
                new DeletingDocController(
                        new DeletingDocService(docManageDao, docIDValidation),
                        barangayId
                )
        );
    }

    private DisplayingDocument getDisplayingDocument(int barangayId){
        return new DisplayingDocument(
                new DisplayingDocController(
                        new DisplayingDocService(documentDao),
                        barangayId
                )
        );
    }
}
