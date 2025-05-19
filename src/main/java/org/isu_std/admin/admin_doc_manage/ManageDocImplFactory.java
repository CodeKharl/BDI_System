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
import org.isu_std.admin.admin_doc_manage.adminDoc_func.others.doc_Id_Validation.ValidDocIDProvider;
import org.isu_std.admin.admin_doc_manage.adminDoc_func.others.doc_Id_Validation.ValidDocIDProviderFactory;
import org.isu_std.dao.DocManageDao;
import org.isu_std.dao.DocumentDao;

public class ManageDocImplFactory {
    private final DocManageDao docManageDao;
    private final DocumentDao documentDao;

    protected ManageDocImplFactory(DocManageDao docManageDao, DocumentDao documentDao){
        this.docManageDao = docManageDao;
        this.documentDao = documentDao;
    }

    protected ManageDocumentImpl[] createAdminDocumentMap(int barangayId){
        return new ManageDocumentImpl[]{
                getAddingDocument(barangayId),
                getModifyDocument(barangayId),
                getDeletingDocument(barangayId),
                getDisplayingDocument(barangayId)
        };
    }

    private AddingDocument getAddingDocument(int barangayId){
        var addingDocService = new AddingDocService(docManageDao);
        var addingDocController = new AddingDocController(addingDocService, barangayId);

        return new AddingDocument(addingDocController);
    }

    private ModifyingDocument getModifyDocument(int barangayId){
        var modifyingDocService = new ModifyingDocService(
                documentDao, docManageDao, getValidDocIDProvider(barangayId)
        );

        var modifyingDocController = new ModifyingDocController(
                modifyingDocService, barangayId
        );

        return new ModifyingDocument(modifyingDocController);
    }

    private DeletingDocument getDeletingDocument(int barangayId){
        var deletingDocService = new DeletingDocService(
                documentDao, docManageDao, getValidDocIDProvider(barangayId)
        );

        var deletingDocController = new DeletingDocController(deletingDocService, barangayId);

        return new DeletingDocument(deletingDocController);
    }

    private DisplayingDocument getDisplayingDocument(int barangayId){
        var displayingDocService = new DisplayingDocService(documentDao);
        var displayingDocController = new DisplayingDocController(
                displayingDocService, barangayId
        );

        return new DisplayingDocument(displayingDocController);
    }

    private ValidDocIDProvider getValidDocIDProvider(int barangayId){
        var validDocIdProviderFactory = new ValidDocIDProviderFactory(documentDao);

        return validDocIdProviderFactory.createValidDocIdProvider(barangayId);
    }
}
