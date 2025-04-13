package org.isu_std.logsign.userlogin;

import org.isu_std.ClientManager;
import org.isu_std.io.Util;
import org.isu_std.io.exception.NotFoundException;

public class UserLoginController {
    private final UserLoginService userLoginService;
    private final ClientManager clientManager;
    public UserLoginController(UserLoginService userLoginService, ClientManager clientManager){
        this.userLoginService = userLoginService;
        this.clientManager = clientManager;
    }

    protected boolean isInputAccepted(int count, String input){
        try {
            switch (count) {
                case 0 -> clientManager.setUser(
                        userLoginService.getUser(input)
                );

                case 1 -> userLoginService.checkUserPassword(
                        clientManager.getUser().password(),
                        input
                );

                default -> throw new IllegalStateException("Unexpected Value " + count);
            }

            return true;
        }catch (IllegalArgumentException | NotFoundException e){
            Util.printException(e.getMessage());
        }

        return false;
    }
}
