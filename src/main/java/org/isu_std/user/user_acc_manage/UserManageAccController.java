package org.isu_std.user.user_acc_manage;

import org.isu_std.models.User;

public class UserManageAccController {
    private final UserManageAccService userManageAccService;
    private final User user;

    private UserManageProcess[] userManageProcesses;

    protected UserManageAccController(UserManageAccService userManageAccService, User user){
        this.userManageAccService = userManageAccService;
        this.user = user;
    }

    protected void managerPerformed(String[] manageChoices, int choice){
        if(this.userManageProcesses == null){
            this.userManageProcesses = userManageAccService.createUserManageProcesses(user);
        }

        int index = choice - 1;
        UserManageProcess userManageProcess = userManageProcesses[index];
        userManageProcess.managePerformed(manageChoices[index]);
    }
}
