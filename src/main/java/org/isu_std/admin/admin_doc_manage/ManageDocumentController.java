package org.isu_std.admin.admin_doc_manage;

public class ManageDocumentController {
    private final ManageDocumentService manageDocumentService;

    protected ManageDocumentController(ManageDocumentService manageDocumentService, int barangayId){
        this.manageDocumentService = manageDocumentService;
    }

    protected void startManageDocFunc(int choice){
        manageDocumentService.getDocumentFunctions(choice).manageProcess();
    }
}
