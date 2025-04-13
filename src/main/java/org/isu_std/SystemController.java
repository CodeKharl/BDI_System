package org.isu_std;

import org.isu_std.io.WindowsCLIClear;
import org.isu_std.io.Util;

public class SystemController {
    private final SystemService systemService;
    private final ClientManager clientManager;

    public SystemController(SystemService systemService){
        this.systemService = systemService;
        this.clientManager = systemService.createClientManager();
    }

    protected boolean isSystemRunning(int choice){
        // Handles the condition based on the given input by the client.
        switch (choice) {
            case 1 -> startLogin(ClientManage.getClientType());
            case 2 -> startSignup(ClientManage.getClientType());
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
                .getLogin(type, clientManager)
                .setLoginInformation()
        ){
            systemService.getPostLoginNav(
                    type,
                    clientManager.getUser(),
                    clientManager.getAdmin()
            ).navigateToSection();
        }
    }

    private void startSignup(int type){
        systemService
                .getSignup(type)
                .setSignupInformation();
    }
}
