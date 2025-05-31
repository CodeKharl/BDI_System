package org.isu_std.admin.admin_main.admin_doc_manage;

import org.isu_std.models.Barangay;

class ManageDocumentController {
    private final ManageDocumentImpl[] manageDocumentImpls;

    protected ManageDocumentController(ManageDocumentService manageDocumentService, Barangay barangay){
        this.manageDocumentImpls = manageDocumentService
                .getAdminDocumentImplArr(barangay.barangayId());
    }

    protected void startManageDocFunc(String[] manageDocMenu, int choice){
        int index = choice - 1;
        String manageTitle = manageDocMenu[index];

        manageDocumentImpls[index].manageProcess(manageTitle);
    }
}
