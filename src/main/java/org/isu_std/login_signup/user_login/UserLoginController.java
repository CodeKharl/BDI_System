package org.isu_std.login_signup.user_login;

import org.isu_std.client_context.AdminContext;
import org.isu_std.client_context.UserContext;
import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.NotFoundException;

public class UserLoginController {
    private final UserLoginService userLoginService;
    private final UserContext userContext;

    public UserLoginController(UserLoginService userLoginService, UserContext userContext){
        this.userLoginService = userLoginService;
        this.userContext = userContext;
    }

    protected String[] userDetails(){
        return this.userLoginService.getUserDetails();
    }

    protected boolean isInputAccepted(int count, String input){
        try {
            switch (count) {
                case 0 -> userContext.setUser(
                        userLoginService.getUser(input)
                );

                case 1 -> userLoginService.checkUserPassword(
                        userContext.getUser().password(),
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
