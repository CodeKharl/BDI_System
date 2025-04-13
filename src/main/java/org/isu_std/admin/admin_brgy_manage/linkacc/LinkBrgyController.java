package org.isu_std.admin.admin_brgy_manage.linkacc;

import org.isu_std.io.exception.NotFoundException;
import org.isu_std.io.exception.OperationFailedException;
import org.isu_std.models.Admin;
import org.isu_std.models.Barangay;
import org.isu_std.io.Util;

public class LinkBrgyController {
    private final LinkBrgyService linkBrgyService;
    private Barangay barangay;
    private Admin admin;

    public LinkBrgyController(LinkBrgyService linkBrgyService, Admin admin){
        this.linkBrgyService = linkBrgyService;
        this.admin = admin;
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
                    admin.adminId(),
                    barangay.barangayId()
            );

            admin = linkBrgyService.buildAdminWithId(admin, barangay.barangayId());
            Util.printMessage("Your account is connected to the Barangay.");

            return true;
        }catch (OperationFailedException e){
            Util.printException(e.getMessage());
        }

        return false;
    }
}
