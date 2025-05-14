package org.isu_std.admin.admin_doc_manage;

public class ManageDocumentController {
    private final ManageDocumentService manageDocumentService;
    private final int barangayId;

    private ManageDocumentImpl[] adminDocumentImplArr;

    protected ManageDocumentController(ManageDocumentService manageDocumentService, int barangayId){
        this.manageDocumentService = manageDocumentService;
        this.barangayId = barangayId;
    }

    protected void startManageDocFunc(int choice){
        if(adminDocumentImplArr == null){
            adminDocumentImplArr = manageDocumentService
                    .getAdminDocumentImplArr(barangayId);
        }

        int index = choice - 1;
        adminDocumentImplArr[index].manageProcess();
    }
}
