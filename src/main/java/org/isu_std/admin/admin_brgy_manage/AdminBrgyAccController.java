package org.isu_std.admin.admin_brgy_manage;

import org.isu_std.client_context.AdminContext;
import org.isu_std.io.collections_enum.InputMessageCollection;
import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.ServiceException;

public class AdminBrgyAccController {
    private final AdminBrgyService adminBrgyService;
    private final AdminContext adminContext;

    public AdminBrgyAccController(AdminContext adminContext, AdminBrgyService adminBrgyService){
        this.adminContext = adminContext;
        this.adminBrgyService = adminBrgyService;
    }

    protected boolean isUserHasNoBarangayId(){
        return adminContext.getAdmin().barangayId() == 0;
    }

    protected boolean isProcessSuccess(int choice){
        switch(choice){
            case 1 -> {
                return adminBrgyService
                        .createLinkBrgy(adminContext)
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
            adminBrgyService.getAdminUI(adminContext).adminMenu();
        }catch (NotFoundException | ServiceException e){
            Util.printException(e.getMessage());
        }
    }
}
