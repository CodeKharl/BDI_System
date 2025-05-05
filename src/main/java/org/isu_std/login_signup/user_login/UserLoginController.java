package org.isu_std.login_signup.user_login;

import org.isu_std.ClientContext;
import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.NotFoundException;

public class UserLoginController {
    private final UserLoginService userLoginService;
    private final ClientContext clientContext;

    public UserLoginController(UserLoginService userLoginService, ClientContext clientContext){
        this.userLoginService = userLoginService;
        this.clientContext = clientContext;
    }

    protected String[] userDetails(){
        return this.userLoginService.getUserDetails();
    }

    protected boolean isInputAccepted(int count, String input){
        try {
            switch (count) {
                case 0 -> clientContext.setUser(
                        userLoginService.getUser(input)
                );

                case 1 -> userLoginService.checkUserPassword(
                        clientContext.getUser().password(),
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
