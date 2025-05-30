package org.isu_std.admin.admin_main.admin_doc_manage.adminDoc_func.delete;

import org.isu_std.admin.admin_main.admin_doc_manage.ManageDocumentImpl;
import org.isu_std.admin.admin_main.admin_doc_manage.adminDoc_func.others.DocumentManageCodes;
import org.isu_std.io.collections_enum.ChoiceCollection;
import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;

public class DeletingDocument implements ManageDocumentImpl {
    private final DeletingDocController deletingDocController;

    public DeletingDocument(DeletingDocController deletingDocController){
        this.deletingDocController = deletingDocController;
    }

    @Override
    public void manageProcess() {
        while(true) {
            Util.printSectionTitle("Delete Document"); // Print Delete Title with exit key.

            if(!deletingDocController.setDocumentId()){
                return;
            }

            if(isDeleteConfirmed()){
                deletingDocController.deleteDocument();
            }
        }
    }

    private boolean isDeleteConfirmed(){
        return SystemInput.isPerformConfirmed(
                "Delete Confirmation",
                DocumentManageCodes.DELETING.getCode(),
                DocumentManageCodes.CANCELING.getCode()
        );
    }
}
