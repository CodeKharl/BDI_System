package org.isu_std;

import org.isu_std.io.Util;

public class SystemController {
    private final SystemService systemService;
    private final ClientContext clientContext;

    public SystemController(SystemService systemService){
        this.systemService = systemService;
        this.clientContext = systemService.createClientManager();
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
                .getLogin(type, clientContext)
                .setLoginInformation()
        ){
            systemService.getPostLoginNav(
                    type,
                    clientContext.getUser(),
                    clientContext.getAdmin()
            ).navigateToSection();
        }
    }

    private void startSignup(int type){
        systemService
                .getSignup(type)
                .setSignupInformation();
    }
}
