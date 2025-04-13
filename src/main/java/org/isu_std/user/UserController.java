package org.isu_std.user;

import org.isu_std.io.Util;
import org.isu_std.io.exception.NotFoundException;
import org.isu_std.models.User;

public class UserController {
    private final UserService userService;
    private final User user;

    protected UserController(UserService userService, User user){
        this.userService = userService;
        this.user = user;
    }

    protected void userDocumentRequest(){
        try {
            userService.getUserDocReq(user).requestDocument();
        }catch(NotFoundException e){
            Util.printException(e.getMessage());
        }
    }

    protected void userManageAcc(){
        userService.getUserManageAcc(
                user.userId()
        ).manageMenu();
    }

    protected String getUsername(){
        return this.user.username();
    }
}
