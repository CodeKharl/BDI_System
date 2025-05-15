package org.isu_std;

import org.isu_std.client_context.AdminContext;
import org.isu_std.client_context.UserContext;
import org.isu_std.io.Util;

public class SystemController {
    private final SystemService systemService;
    private final AdminContext adminContext;
    private final UserContext userContext;

    public SystemController(SystemService systemService){
        this.systemService = systemService;
        this.adminContext = systemService.createAdminContext();
        this.userContext = systemService.createUserContext();
    }

    protected boolean isSystemRunning(int choice){
        // Handles the condition based on the given input by the client.
        switch (choice) {
            case 1 -> startLogin(ClientManager.getClientType());
            case 2 -> startSignup(ClientManager.getClientType());
            case 3 -> {} // About the system
            case 4 -> {
                Util.printMessage("System exits...");
                return false;
            }
        }

        return true;
    }

    private void startLogin(int type){
        if(systemService
                .getLogin(type, adminContext, userContext)
                .setLoginInformation()
        ){
            systemService.getPostLoginNav(
                    type,
                    userContext,
                    adminContext
            ).navigateToSection();
        }
    }

    private void startSignup(int type){
        systemService
                .getSignup(type)
                .setSignupInformation();
    }
}
