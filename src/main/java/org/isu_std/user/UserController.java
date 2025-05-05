package org.isu_std.user;

import org.isu_std.client_context.UserContext;
import org.isu_std.models.User;

public class UserController {
    private final UserService userService;
    private final UserContext userContext;

    private UserProcess[] userProcesses;

    protected UserController(UserService userService, UserContext userContext){
        this.userService = userService;
        this.userContext = userContext;
    }

    protected void userOnProcess(String[] processTitles, int choice){
        if(this.userProcesses == null){
            this.userProcesses = userService.createUserProcesses(userContext);
        }

        int index = choice - 1;
        UserProcess userProcess = userProcesses[index];
        userProcess.processPerformed(processTitles[index]);
    }

    protected String getUsername(){
        return this.userContext.getUsername();
    }
}
