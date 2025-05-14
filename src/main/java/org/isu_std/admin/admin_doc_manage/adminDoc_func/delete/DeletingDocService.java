package org.isu_std.admin.admin_doc_manage.adminDoc_func.delete;

import org.isu_std.admin.admin_doc_manage.adminDoc_func.others.DocFileDeletion;
import org.isu_std.admin.admin_doc_manage.adminDoc_func.others.DocFileDeletionFactory;
import org.isu_std.admin.admin_doc_manage.adminDoc_func.others.doc_Id_Validation.ValidDocIDProvider;
import org.isu_std.dao.DocManageDao;
import org.isu_std.dao.DocumentDao;
import org.isu_std.io.custom_exception.OperationFailedException;

public class DeletingDocService {
    private final DocumentDao documentDao;
    private final DocManageDao docManageDao;
    private final ValidDocIDProvider validDocIDProvider;

    public DeletingDocService(
            DocumentDao documentDao, DocManageDao docManageDao, ValidDocIDProvider validDocIDProvider
    ){
        this.docManageDao = docManageDao;
        this.validDocIDProvider = validDocIDProvider;
        this.documentDao = documentDao;
    }

    public void deletePerformed(int barangayId, int documentId){
        if(!docManageDao.isDeleteSuccess(barangayId, documentId)){
            throw new OperationFailedException("Error to Delete the Document! Please try again.");
        }
    }

    protected int getDocIDValidation(){
        return this.validDocIDProvider.getValidatedId();
    }

    protected void deleteDocFile(int barangayId, int documentId) throws OperationFailedException{
        DocFileDeletion docFileDeletion = DocFileDeletionFactory.createDocFileDeletion(documentDao);
        docFileDeletion.deletePerform(barangayId, documentId);
    }
}
