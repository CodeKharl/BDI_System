package org.isu_std.logsign.adminlogin;

import org.isu_std.ClientManager;
import org.isu_std.io.Util;
import org.isu_std.io.exception.NotFoundException;

public class AdminLoginController {
    private final AdminLoginService adminLoginService;
    private final ClientManager clientManager;

    public AdminLoginController(AdminLoginService adminLoginService, ClientManager clientManager){
        this.adminLoginService = adminLoginService;
        this.clientManager = clientManager;
    }

    protected boolean isInputAccepted(int count, int input) {
        try {
            switch (count) {
                case 0 -> clientManager.setAdmin(
                        adminLoginService.getAdmin(input)
                );

                case 1 -> adminLoginService.checkAdminPin(
                        clientManager.getAdmin().adminPin(),
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
