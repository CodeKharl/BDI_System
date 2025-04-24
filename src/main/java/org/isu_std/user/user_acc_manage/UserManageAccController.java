package org.isu_std.user.user_acc_manage;

public class UserManageAccController {
    private final UserManageAccService userManageAccService;
    private final int userId;

    protected UserManageAccController(UserManageAccService userManageAccService, int userId){
        this.userManageAccService = userManageAccService;
        this.userId = userId;
    }

    protected void personalProcess(){
        userManageAccService
                .createManagePersonal(userId)
                .manageUserPersonal();
    }

    protected void accInfoProcess(){

    }
}
