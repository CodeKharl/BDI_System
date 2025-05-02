package org.isu_std.user.user_acc_manage;

public class UserManageAccController {
    private final UserManageAccService userManageAccService;
    private final int userId;

    private UserManageProcess[] userManageProcesses;

    protected UserManageAccController(UserManageAccService userManageAccService, int userId){
        this.userManageAccService = userManageAccService;
        this.userId = userId;
    }

    protected void managerPerformed(String[] manageChoices, int choice){
        if(this.userManageProcesses == null){
            this.userManageProcesses = userManageAccService.createUserManageProcesses(userId);
        }

        int index = choice - 1;
        UserManageProcess userManageProcess = userManageProcesses[index];
        userManageProcess.managePerformed(manageChoices[index]);
    }
}
