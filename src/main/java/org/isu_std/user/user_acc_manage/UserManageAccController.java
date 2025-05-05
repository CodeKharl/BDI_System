package org.isu_std.user.user_acc_manage;

import org.isu_std.client_context.UserContext;
import org.isu_std.models.User;

public class UserManageAccController {
    private final UserManageAccService userManageAccService;
    private final UserContext userContext;

    private UserManageProcess[] userManageProcesses;

    protected UserManageAccController(UserManageAccService userManageAccService, UserContext userContext){
        this.userManageAccService = userManageAccService;
        this.userContext = userContext;
    }

    protected void managerPerformed(String[] manageChoices, int choice){
        if(this.userManageProcesses == null){
            this.userManageProcesses = userManageAccService.createUserManageProcesses(userContext);
        }

        int index = choice - 1;
        UserManageProcess userManageProcess = userManageProcesses[index];
        userManageProcess.managePerformed(manageChoices[index]);
    }
}
