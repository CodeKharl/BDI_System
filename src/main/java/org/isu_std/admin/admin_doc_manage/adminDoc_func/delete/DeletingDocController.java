package org.isu_std.admin.admin_doc_manage.adminDoc_func.delete;

import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.OperationFailedException;

public class DeletingDocController {
    private final DeletingDocService deletingDocService;
    private final int barangayId;
    private int selectedDocumentId;

    public DeletingDocController(DeletingDocService deletingDocService, int barangayId){
        this.deletingDocService = deletingDocService;
        this.barangayId = barangayId;
    }

    protected final boolean setDocumentId(){
        int documentId = deletingDocService.getDocIDValidation();

        if(documentId != 0){
            this.selectedDocumentId = documentId;
            return true;
        }

        return false;
    }

    protected void deleteDocument(){
        try{
            deletingDocService.deleteDocFile(barangayId, selectedDocumentId);
            deletingDocService.deletePerformed(barangayId, selectedDocumentId);
            Util.printMessage("Document deleted successfully");
        }catch (OperationFailedException e){
            Util.printMessage(e.getMessage());
        }
    }
}
