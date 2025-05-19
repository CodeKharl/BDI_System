package org.isu_std.admin.admin_doc_manage.adminDoc_func.delete;

import org.isu_std.admin.admin_doc_manage.adminDoc_func.others.DocumentFileDeletion;
import org.isu_std.admin.admin_doc_manage.adminDoc_func.others.DocFileDeletionFactory;
import org.isu_std.admin.admin_doc_manage.adminDoc_func.others.doc_Id_Validation.ValidDocIDProvider;
import org.isu_std.dao.DocManageDao;
import org.isu_std.dao.DocumentDao;
import org.isu_std.io.SystemLogger;
import org.isu_std.io.custom_exception.DataAccessException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.custom_exception.ServiceException;

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

    public void deletePerformed(int barangayId, int documentId) throws OperationFailedException{
        try {
            if (!docManageDao.deleteDocument(barangayId, documentId)) {
                throw new OperationFailedException("Failed to Delete the Document! Please try again.");
            }
        }catch (DataAccessException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException("Failed to delete document with document ID : " + documentId);
        }
    }

    protected int getDocIDValidation(){
        return this.validDocIDProvider.getValidatedId();
    }

    protected void deleteDocFile(int barangayId, int documentId) throws OperationFailedException{
        DocumentFileDeletion documentFileDeletion = DocFileDeletionFactory.createDocFileDeletion(documentDao);
        documentFileDeletion.deletePerform(barangayId, documentId);
    }
}
