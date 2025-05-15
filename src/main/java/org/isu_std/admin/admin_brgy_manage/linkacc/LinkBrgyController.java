package org.isu_std.admin.admin_brgy_manage.linkacc;

import org.isu_std.client_context.AdminContext;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.models.Admin;
import org.isu_std.models.Barangay;
import org.isu_std.io.Util;

public class LinkBrgyController {
    private final LinkBrgyService linkBrgyService;
    private final AdminContext adminContext;

    private Barangay barangay;

    public LinkBrgyController(LinkBrgyService linkBrgyService, AdminContext adminContext){
        this.linkBrgyService = linkBrgyService;
        this.adminContext = adminContext;
    }

    protected boolean isInputAccepted(int count, int input){
        try {
            switch (count) {
                case 0 -> barangay = linkBrgyService.getBarangay(input);
                case 1 -> linkBrgyService.checkBarangayPin(
                        barangay.barangayPin(), input
                );

                default -> throw new IllegalStateException("Unexpected Value : " + count);
            }

            return true;
        }catch (IllegalArgumentException | NotFoundException e){
            Util.printException(e.getMessage());
            return false;
        }
    }

    protected boolean setBarangayConnection(){
        // Setting the barangayID of admin on the database and the admin object.
        try{
            linkBrgyService.setAdminBarangayId(
                    barangay.barangayId(),
                    adminContext.getAdmin().adminId()
            );

            Admin defaultAdmin = adminContext.getAdmin();
            Admin newAdmin = linkBrgyService.buildAdminWithId(
                    defaultAdmin, barangay
            );
            adminContext.setAdmin(newAdmin);

            Util.printMessage("Your account is connected to the Barangay.");

            return true;
        }catch (OperationFailedException e){
            Util.printException(e.getMessage());
        }

        return false;
    }
}
