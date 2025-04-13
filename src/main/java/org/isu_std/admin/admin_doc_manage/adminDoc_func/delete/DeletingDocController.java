package org.isu_std.admin.admin_doc_manage.adminDoc_func.delete;

import org.isu_std.io.Util;

public class DeletingDocController {
    private final DeletingDocService deletingDocService;
    private final int barangayId;

    private int documentId;

    public DeletingDocController(DeletingDocService deletingDocService, int barangayId){
        this.deletingDocService = deletingDocService;
        this.barangayId = barangayId;
    }

    protected final boolean setDocumentId(){
        documentId = deletingDocService.getDocIDValidation(barangayId);
        return documentId != 0; // 0 means cancellation
    }

    protected void deleteDocument(){
        try{
            deletingDocService.deletePerformed(barangayId, documentId);
            Util.printMessage("Document deleted successfully");
        }catch (IllegalStateException e){
            Util.printMessage(e.getMessage());
        }
    }
}
