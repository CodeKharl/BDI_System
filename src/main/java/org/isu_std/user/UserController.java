package org.isu_std.user;

import org.isu_std.models.User;

public class UserController {
    private final UserService userService;
    private final User user;

    private UserProcess[] userProcesses;

    protected UserController(UserService userService, User user){
        this.userService = userService;
        this.user = user;
    }

    protected void userOnProcess(String[] processTitles, int choice){
        if(this.userProcesses == null){
            this.userProcesses = userService.createUserProcesses(user);
        }

        int index = choice - 1;
        UserProcess userProcess = userProcesses[index];
        userProcess.processPerformed(processTitles[index]);
    }

    protected String getUsername(){
        return this.user.username();
    }
}
