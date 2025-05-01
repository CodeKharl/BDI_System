package org.isu_std.user.user_acc_manage;

import org.isu_std.dao.UserPersonalDao;

public class UserManageAccFactory{
    private final UserPersonalDao userPersonalDao;

    public UserManageAccFactory(UserPersonalDao userPersonalDao){
        this.userPersonalDao = userPersonalDao;
    }

    public UserManageAcc createUserManageAcc(int userId){

        return new UserManageAcc(
                new UserManageAccController(
                        new UserManageAccService(userPersonalDao),
                        userId
                )
        );
    }
}
