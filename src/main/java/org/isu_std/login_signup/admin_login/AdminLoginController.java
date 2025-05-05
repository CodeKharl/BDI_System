package org.isu_std.login_signup.admin_login;

import org.isu_std.client_context.AdminContext;
import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.NotFoundException;

public class AdminLoginController {
    private final AdminLoginService adminLoginService;
    private final AdminContext adminContext;

    public AdminLoginController(AdminLoginService adminLoginService, AdminContext adminContext){
        this.adminLoginService = adminLoginService;
        this.adminContext = adminContext;
    }

    protected boolean isInputAccepted(int count, int input) {
        try {
            switch (count) {
                case 0 -> adminContext.setAdmin(
                        adminLoginService.getAdmin(input)
                );

                case 1 -> adminLoginService.checkAdminPin(
                        adminContext.getAdmin().adminPin(),
                        input
                );
            }

            return true;
        }catch (IllegalArgumentException | NotFoundException e){
            Util.printException(e.getMessage());
        }

        return false;
    }
}
