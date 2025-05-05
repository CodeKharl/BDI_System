package org.isu_std.user.user_acc_manage;

import org.isu_std.client_context.UserContext;
import org.isu_std.dao.BarangayDao;
import org.isu_std.dao.UserDao;
import org.isu_std.dao.UserPersonalDao;
import org.isu_std.models.User;

public class UserManageAccFactory{
    private final UserPersonalDao userPersonalDao;
    private final BarangayDao barangayDao;
    private final UserDao userDao;

    public UserManageAccFactory(UserPersonalDao userPersonalDao, BarangayDao barangayDao, UserDao userDao){
        this.userPersonalDao = userPersonalDao;
        this.barangayDao = barangayDao;
        this.userDao = userDao;
    }

    public UserManageAcc createUserManageAcc(UserContext userContext){
        var userManageAccService = new UserManageAccService(userPersonalDao, barangayDao, userDao);
        var userManageAccController = new UserManageAccController(userManageAccService, userContext);

        return new UserManageAcc(userManageAccController);
    }
}
