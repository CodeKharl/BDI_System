package org.isu_std.admin.admin_main;

import org.isu_std.client_context.AdminContext;
import org.isu_std.models.Barangay;

public class AdminUIController {
    private final AdminContext adminContext;
    private final Barangay barangay;
    private final AdminSection[] adminSections;

    public AdminUIController(AdminUIService adminUIService, AdminContext adminContext, Barangay barangay){
        this.adminContext = adminContext;
        this.barangay = barangay;
        this.adminSections = adminUIService.getAdminSections(barangay);
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

    protected void adminOnProcess(String[] adminMenu, int choice){
        int index = choice - 1;
        String sectionTitle = adminMenu[index];
        adminSections[index].run(sectionTitle);
    }
}
