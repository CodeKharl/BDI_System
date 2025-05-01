package org.isu_std.logsign.adminlogin;

import org.isu_std.ClientContext;
import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.NotFoundException;

public class AdminLoginController {
    private final AdminLoginService adminLoginService;
    private final ClientContext clientContext;

    public AdminLoginController(AdminLoginService adminLoginService, ClientContext clientContext){
        this.adminLoginService = adminLoginService;
        this.clientContext = clientContext;
    }

    protected boolean isInputAccepted(int count, int input) {
        try {
            switch (count) {
                case 0 -> clientContext.setAdmin(
                        adminLoginService.getAdmin(input)
                );

                case 1 -> adminLoginService.checkAdminPin(
                        clientContext.getAdmin().adminPin(),
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
