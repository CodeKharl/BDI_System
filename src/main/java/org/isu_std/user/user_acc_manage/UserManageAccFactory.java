package org.isu_std.user.user_acc_manage;

import org.isu_std.dao.BarangayDao;
import org.isu_std.dao.UserPersonalDao;

public class UserManageAccFactory{
    private final UserPersonalDao userPersonalDao;
    private final BarangayDao barangayDao;

    public UserManageAccFactory(UserPersonalDao userPersonalDao, BarangayDao barangayDao){
        this.userPersonalDao = userPersonalDao;
        this.barangayDao = barangayDao;
    }

    public UserManageAcc createUserManageAcc(int userId){
        var userManageAccService = new UserManageAccService(userPersonalDao, barangayDao);
        var userManageAccController = new UserManageAccController(userManageAccService, userId);

        return new UserManageAcc(userManageAccController);
    }
}
