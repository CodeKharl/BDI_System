package org.isu_std.admin.admin_doc_manage.adminDoc_func.display;

import org.isu_std.admin.admin_doc_manage.AdminDocumentImpl;

public class DisplayingDocument implements AdminDocumentImpl {
    private final DisplayingDocController displayingDocController;

    public DisplayingDocument(DisplayingDocController displayingDocController){
        this.displayingDocController = displayingDocController;
    }

    @Override
    public void manageProcess(){
        if(displayingDocController.isThereExistingDocs()){
            displayingDocController.printDocs();
        }
    }
}
