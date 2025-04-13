package org.isu_std.user.userAccManage;

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
