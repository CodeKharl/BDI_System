package org.isu_std.admin.admin_ui;

import org.isu_std.models.Admin;
import org.isu_std.models.Barangay;

public class AdminUIController {
    private final AdminUIService adminUIService;
    private final Admin admin;
    private final Barangay barangay;

    public AdminUIController(AdminUIService adminUIService, Admin admin, Barangay barangay){
        this.adminUIService = adminUIService;
        this.admin = admin;
        this.barangay = barangay;
    }

    protected void startManageDocument(){
        adminUIService.createManageDocumentUI(
                admin.barangayId()
        ).manageMenu();
    }

    protected void startRequestedDocument(){
        adminUIService.getRequestedDocument(barangay).requestedDocSection();
    }

    protected String getAdminName(){
        return this.admin.adminName();
    }
}
