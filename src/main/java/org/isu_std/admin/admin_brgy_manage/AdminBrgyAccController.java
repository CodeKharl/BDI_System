package org.isu_std.admin.admin_brgy_manage;

import org.isu_std.io.collections.InputMessageCollection;
import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.models.Admin;

public class AdminBrgyAccController {
    private final AdminBrgyService adminBrgyService;
    private final Admin admin;

    public AdminBrgyAccController(Admin admin, AdminBrgyService adminBrgyService){
        this.admin = admin;
        this.adminBrgyService = adminBrgyService;
    }

    protected boolean isUserHasNoBarangayId(){
        return admin.barangayId() == 0;
    }

    protected boolean isProcessSuccess(int choice){
        switch(choice){
            case 1 -> {
                return adminBrgyService
                        .createLinkBrgy(admin)
                        .linkPerformed();
            }

            case 2 -> adminBrgyService
                    .createRegisterBrgy()
                    .registerPerformed();

            default -> Util.printMessage(
                    InputMessageCollection.INPUT_INVALID.getFormattedMessage("choice")
            );
        };

        return false;
    }

    protected void launchAdminUI(){
        try {
            adminBrgyService.getAdminUi(admin).adminMenu();
        }catch(OperationFailedException e){
            Util.printException(e.getMessage());
        }
    }
}
