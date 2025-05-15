package org.isu_std.admin.admin_main;

import org.isu_std.client_context.AdminContext;
import org.isu_std.models.Admin;
import org.isu_std.models.Barangay;

public class AdminUIController {
    private final AdminUIService adminUIService;
    private final AdminContext adminContext;
    private final Barangay barangay;

    public AdminUIController(AdminUIService adminUIService, AdminContext adminContext, Barangay barangay){
        this.adminUIService = adminUIService;
        this.adminContext = adminContext;
        this.barangay = barangay;
    }

    protected String getAdminName(){
        return this.adminContext.getAdmin().adminName();
    }

    protected String getBarangayFullName(){
        return "%s, %s, %s".formatted(
                barangay.barangayName(),
                barangay.municipality(),
                barangay.province()
        );
    }

    protected void adminOnProcess(int choice){
        switch(choice){
            case 1 -> adminUIService.getRequestedDocument(barangay)
                    .requestedDocSection();

            case 2 -> adminUIService.getApprovedDocsRequest(barangay)
                    .approvedDocView();

            case 3 -> adminUIService.getManageDocumentUI(
                    adminContext.getAdmin().barangayId()
                    ).manageMenu();

            case 4 -> {}
        };
    }
}
