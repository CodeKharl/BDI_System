package org.isu_std.admin.admin_doc_manage.adminDoc_func.delete;

import org.isu_std.admin.admin_doc_manage.adminDoc_func.others.docIdValidation.DocIDValidation;
import org.isu_std.dao.DocManageDao;
import org.isu_std.io.exception.OperationFailedException;

public class DeletingDocService {
    private final DocManageDao docManageRepository;
    private final DocIDValidation docIDValidation;

    public DeletingDocService(DocManageDao docManageRepository, DocIDValidation docIDValidation){
        this.docManageRepository = docManageRepository;
        this.docIDValidation = docIDValidation;
    }

    public void deletePerformed(int barangayId, int documentId){
        if(!docManageRepository.isDeleteSuccess(barangayId, documentId)){
            throw new OperationFailedException("Error to Delete the Document! Please try again.");
        }
    }

    protected int getDocIDValidation(int barangayId){
        return this.docIDValidation.getInputDocumentId(barangayId);
    }
}
