package org.isu_std.user.userAccManage;

import org.isu_std.dao.UserPersonalDao;

public class UserManageAccFactory{
    private final UserPersonalDao userPersonalDao;

    private UserManageAccFactory(UserPersonalDao userPersonalDao){
        this.userPersonalDao = userPersonalDao;
    }

    private final static class Holder{
        private static UserManageAccFactory instance;
    }

    public static UserManageAccFactory getInstance(UserPersonalDao userPersonalDao){
        if(Holder.instance == null){
            Holder.instance = new UserManageAccFactory(userPersonalDao);
        }

        return Holder.instance;
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
