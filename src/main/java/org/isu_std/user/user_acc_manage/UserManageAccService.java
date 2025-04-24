package org.isu_std.user.user_acc_manage;

import org.isu_std.dao.UserPersonalDao;
import org.isu_std.user.user_acc_manage.userpersonal.ManagePersonal;
import org.isu_std.user.user_acc_manage.userpersonal.ManagePersonalController;
import org.isu_std.user.user_acc_manage.userpersonal.ManagePersonalService;

public class UserManageAccService {
    private final UserPersonalDao userPersonalDao;

    protected UserManageAccService(UserPersonalDao userPersonalDao){
        this.userPersonalDao = userPersonalDao;
    }

    protected ManagePersonal createManagePersonal(int usedId){
        return new ManagePersonal(
                new ManagePersonalController(
                        new ManagePersonalService(userPersonalDao),
                        usedId
                )
        );
    }
}
