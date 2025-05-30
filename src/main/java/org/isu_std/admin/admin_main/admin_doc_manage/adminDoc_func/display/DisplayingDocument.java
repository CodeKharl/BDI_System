package org.isu_std.admin.admin_main.admin_doc_manage.adminDoc_func.display;

import org.isu_std.admin.admin_main.admin_doc_manage.ManageDocumentImpl;

public class DisplayingDocument implements ManageDocumentImpl {
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
