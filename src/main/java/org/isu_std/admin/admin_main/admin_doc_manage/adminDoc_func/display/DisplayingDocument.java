package org.isu_std.admin.admin_main.admin_doc_manage.adminDoc_func.display;

import org.isu_std.admin.admin_main.admin_doc_manage.ManageDocumentImpl;
import org.isu_std.io.Util;

public class DisplayingDocument implements ManageDocumentImpl {
    private final DisplayingDocController displayingDocController;

    public DisplayingDocument(DisplayingDocController displayingDocController){
        this.displayingDocController = displayingDocController;
    }

    @Override
    public void manageProcess(String manageTitle){
        Util.printSectionTitle(manageTitle);

        if(displayingDocController.isThereExistingDocs()){
            displayingDocController.printDocs();
        }
    }
}
